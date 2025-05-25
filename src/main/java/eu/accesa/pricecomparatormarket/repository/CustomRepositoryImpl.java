package eu.accesa.pricecomparatormarket.repository;

import eu.accesa.pricecomparatormarket.dtos.DiscountResponseDto;
import eu.accesa.pricecomparatormarket.dtos.ProductDetailsDto;
import eu.accesa.pricecomparatormarket.dtos.ProductSubstituteDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import eu.accesa.pricecomparatormarket.dtos.ProductSubstituteDto;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CustomRepositoryImpl implements CustomRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String currentPricesSqlQuery = """
            select
                pri.*
            from
                prices pri
            inner join stores s on
                s.id = pri.store_id
            inner join (
                select
                    p.product_id,
                    p.store_id,
                    max(p.effective_date) as most_recent_start_date
                from
                    prices p
                group by
                    p.product_id, p.store_id
            ) as current_pri on (
                current_pri.product_id = pri.product_id
                and current_pri.store_id = pri.store_id
                and current_pri.most_recent_start_date = pri.effective_date
            )
            """;

    @Override
    public List<ProductDetailsDto> findProductPricesWithDiscounts(List<String> productNames) {
        String productNamesListSql = String.join(",", productNames.stream().map(p -> "?").toList());
        String sqlQuery = """
                with available_discounts as (
                    select * from discounts d
                    where current_date >= d.from_date and current_date <= d.to_date
                ),
                current_prices as (%s),
                basket_products as (
                    select * from products p where p.product_name in (%s)
                )
                select
                    s.name as store_name,
                    pro.product_id,
                    pro.product_name,
                    pro.product_category,
                    pro.brand,
                    pro.package_quantity,
                    pro.package_unit,
                    pri.price as original_price,
                    d.percentage_of_discount,
                    d.from_date,
                    d.to_date,
                    (case
                        when d.percentage_of_discount is null then pri.price
                        else pri.price - (d.percentage_of_discount / 100.0) * pri.price
                    end) as final_price
                from basket_products pro
                inner join current_prices pri on pri.product_id = pro.id
                inner join stores s on s.id = pri.store_id
                left join available_discounts d on d.product_id = pri.product_id and d.store_id = pri.store_id
                """.formatted(currentPricesSqlQuery, productNamesListSql);

        return jdbcTemplate.query(sqlQuery, ps -> {
            for (int idx = 0; idx < productNames.size(); idx++) {
                ps.setString(idx + 1, productNames.get(idx));
            }
        }, (rs, rowNum) -> {
            ProductDetailsDto dto = new ProductDetailsDto();
            dto.setStoreName(rs.getString("store_name"));
            dto.setProductId(rs.getString("product_id"));
            dto.setProductName(rs.getString("product_name"));
            dto.setProductCategory(rs.getString("product_category"));
            dto.setBrand(rs.getString("brand"));
            dto.setPackageQuantity(rs.getBigDecimal("package_quantity"));
            dto.setPackageUnit(rs.getString("package_unit"));
            dto.setOriginalPrice(rs.getBigDecimal("original_price"));

            DiscountResponseDto discount = new DiscountResponseDto();
            String from = rs.getString("from_date");
            String to = rs.getString("to_date");
            int percentage = rs.getInt("percentage_of_discount");

            if (percentage > 0 && from != null && to != null) {
                discount.setFromDate(LocalDate.parse(from));
                discount.setToDate(LocalDate.parse(to));
                discount.setPercentageOfDiscount(percentage);
                dto.setDiscountResponseDto(discount);
            }

            dto.setFinalPrice(rs.getBigDecimal("final_price"));
            return dto;
        });
    }

    @Override
    public List<ProductDetailsDto> findTopAvailableDiscounts(Integer limit) {
        String sqlQuery = """
                with top_available_discounts as (
                    select * from discounts d
                    where current_date >= d.from_date and current_date <= d.to_date
                    order by d.percentage_of_discount desc
                    limit ?
                ),
                current_prices as (%s)
                select
                    s.name as store_name,
                    pro.product_id,
                    pro.product_name,
                    pro.product_category,
                    pro.brand,
                    pro.package_quantity,
                    pro.package_unit,
                    pri.price as original_price,
                    d.percentage_of_discount,
                    d.from_date,
                    d.to_date,
                    (pri.price - (d.percentage_of_discount / 100.0) * pri.price) as final_price
                from products pro
                inner join current_prices pri on pri.product_id = pro.id
                inner join stores s on s.id = pri.store_id
                inner join top_available_discounts d on d.product_id = pri.product_id and d.store_id = pri.store_id
                order by d.percentage_of_discount desc
                """.formatted(currentPricesSqlQuery);

        return jdbcTemplate.query(sqlQuery, ps -> {
            ps.setInt(1, limit);
        }, (rs, rowNum) -> {
            ProductDetailsDto dto = new ProductDetailsDto();
            dto.setStoreName(rs.getString("store_name"));
            dto.setProductId(rs.getString("product_id"));
            dto.setProductName(rs.getString("product_name"));
            dto.setProductCategory(rs.getString("product_category"));
            dto.setBrand(rs.getString("brand"));
            dto.setPackageQuantity(rs.getBigDecimal("package_quantity"));
            dto.setPackageUnit(rs.getString("package_unit"));
            dto.setOriginalPrice(rs.getBigDecimal("original_price"));

            DiscountResponseDto discount = new DiscountResponseDto();
            discount.setFromDate(LocalDate.parse(rs.getString("from_date")));
            discount.setToDate(LocalDate.parse(rs.getString("to_date")));
            discount.setPercentageOfDiscount(rs.getInt("percentage_of_discount"));
            dto.setDiscountResponseDto(discount);

            dto.setFinalPrice(rs.getBigDecimal("final_price"));
            return dto;
        });
    }

    @Override
    public List<ProductDetailsDto> findDiscountsStartingBy(LocalDate startDate) {
        String sqlQuery = """
                with new_available_discounts as (
                    select * from discounts d where d.from_date = ?
                ),
                current_prices as (%s)
                select
                    s.name as store_name,
                    pro.product_id,
                    pro.product_name,
                    pro.product_category,
                    pro.brand,
                    pro.package_quantity,
                    pro.package_unit,
                    pri.price as original_price,
                    d.percentage_of_discount,
                    d.from_date,
                    d.to_date,
                    (pri.price - (d.percentage_of_discount / 100.0) * pri.price) as final_price
                from products pro
                inner join current_prices pri on pri.product_id = pro.id
                inner join stores s on s.id = pri.store_id
                inner join new_available_discounts d on d.product_id = pri.product_id and d.store_id = pri.store_id
                """.formatted(currentPricesSqlQuery);

        return jdbcTemplate.query(sqlQuery, ps -> {
            ps.setDate(1, Date.valueOf(startDate));
        }, (rs, rowNum) -> {
            ProductDetailsDto dto = new ProductDetailsDto();
            dto.setStoreName(rs.getString("store_name"));
            dto.setProductId(rs.getString("product_id"));
            dto.setProductName(rs.getString("product_name"));
            dto.setProductCategory(rs.getString("product_category"));
            dto.setBrand(rs.getString("brand"));
            dto.setPackageQuantity(rs.getBigDecimal("package_quantity"));
            dto.setPackageUnit(rs.getString("package_unit"));
            dto.setOriginalPrice(rs.getBigDecimal("original_price"));

            DiscountResponseDto discount = new DiscountResponseDto();
            discount.setFromDate(LocalDate.parse(rs.getString("from_date")));
            discount.setToDate(LocalDate.parse(rs.getString("to_date")));
            discount.setPercentageOfDiscount(rs.getInt("percentage_of_discount"));
            dto.setDiscountResponseDto(discount);

            dto.setFinalPrice(rs.getBigDecimal("final_price"));
            return dto;
        });
    }

    @Override
    public List<ProductSubstituteDto> findProductSubstitutes(String productName, String productCategory) {
        String sql = """
                SELECT 
                    p.id as product_id,
                    p.product_name,
                    p.brand,
                    p.product_category,
                    p.package_quantity,
                    p.package_unit,
                    latest_prices.price,
                    (latest_prices.price / p.package_quantity) as value_per_unit
                FROM products p
                JOIN (%s) latest_prices
                ON (latest_prices.product_id = p.id) 
                WHERE (? is null OR p.product_category = ?)
                AND (? is null OR p.product_name = ?)
                ORDER BY value_per_unit ASC
                LIMIT 5
                """.formatted(currentPricesSqlQuery);

        return jdbcTemplate.query(sql, ps -> {
            ps.setString(1, productCategory);
            ps.setString(2, productCategory);
            ps.setString(3, productName);
            ps.setString(4, productName);
        }, (rs, rowNum) -> {
            ProductSubstituteDto dto = new ProductSubstituteDto();
            dto.setProductId(rs.getString("product_id"));
            dto.setProductName(rs.getString("product_name"));
            dto.setBrand(rs.getString("brand"));
            dto.setProductCategory(rs.getString("product_category"));
            dto.setPackageQuantity(rs.getBigDecimal("package_quantity"));
            dto.setPackageUnit(rs.getString("package_unit"));
            dto.setPrice(rs.getBigDecimal("price"));
            dto.setValuePerUnit(rs.getBigDecimal("value_per_unit"));
            return dto;

        });
    }
}
