package jaykye.finalproject.dao;

import jaykye.finalproject.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryDaoDB implements CategoryDao {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Category getCategoryById(String id) {
        try {
            final String GET_CATEGORY_BY_ID = "SELECT * FROM category WHERE categoryId = ?";
            Category category = jdbc.queryForObject(GET_CATEGORY_BY_ID, new CategoryMapper(), id);
            return category;
        } catch(DataAccessException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    // ############################### Foreign key object 가져오는 함수들 ###############################

    // #################################################################################################


    @Override
    public List<Category> getAllCategory() {
        final String GET_ALL_CATEGORYES = "SELECT * FROM category";
        List<Category> categories = jdbc.query(GET_ALL_CATEGORYES, new CategoryMapper());
        return categories;  
    }

    @Override
    public Category addCategory(Category category) {
        final String INSERT_CATEGORY = "INSERT IGNORE INTO category(categoryId, categoryName) " +
                "VALUES(?, ?)";
        jdbc.update(INSERT_CATEGORY,
                category.getId(),
                category.getName()
        );

//        String newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", String.class);
//        category.setId(newId);
        return category;
    }

// Don't need it for now.
    @Override
    public void updateCategory(Category category) {
//        final String UPDATE_CATEGORY = "UPDATE category SET categoryName = ? WHERE categoryId = ?";
//        jdbc.update(UPDATE_CATEGORY,
//                category.getName(),
//                category.getId());
//        final String DELETE_VENUE = "DELETE FROM venue where categoryId = ?";
//        jdbc.update(DELETE_VENUE, category.getId());
//        insertCategoryOrganization(category);
    }

    @Override
    public void deleteCategoryById(String id) {

        final String DELETE_VENUE_CATEGORY = "DELETE FROM venue_category "
                + "WHERE categoryId = ?";
        jdbc.update(DELETE_VENUE_CATEGORY, id);

        final String DELETE_CATEGORY = "DELETE FROM category WHERE categoryId = ?";
        jdbc.update(DELETE_CATEGORY, id);
    }

    public static final class CategoryMapper implements RowMapper<Category> {
        /**
         * Do not map foreign keys here.
         * @param rs
         * @param index
         * @return
         * @throws SQLException
         */
        @Override
        public Category mapRow(ResultSet rs, int index) throws SQLException {
            Category category = new Category();
            category.setId(rs.getString("categoryId"));
            category.setName(rs.getString("categoryName"));
            return category;
        }
    }


}
