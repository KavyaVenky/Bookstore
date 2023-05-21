package business.book;

import business.BookstoreDbException;
import business.JdbcUtils;
import business.category.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import business.BookstoreDbException.BookstoreQueryDbException;

public class BookDaoJdbc implements BookDao {

    private static final String FIND_BY_BOOK_ID_SQL =
            "SELECT * " +
                    "FROM book " +
                    "WHERE book_id = ?";

    private static final String FIND_BY_CATEGORY_ID_SQL = "SELECT * " + "FROM book " + "WHERE category_id = ?";
    // TODO Implement this constant to be used in the findByCategoryId method


    private static final String FIND_RANDOM_BY_CATEGORY_ID_SQL =
            "SELECT * " +
                    "FROM book " +
                    "WHERE category_id = ? " +
                    "ORDER BY RAND() " +
                    "LIMIT ?";

    private static final String FIND_BOOKS_BY_CATEGORY_NAME = "SELECT * FROM book WHERE category_id = (SELECT category_id FROM category WHERE name = ?)";

    private static final String FIND_RANDOM_BY_CATEGORY_NAME_SQL = "SELECT * " +
            "FROM book " +
            "WHERE category_id = (SELECT category_id FROM category WHERE name = ?) " +
            "ORDER BY RAND() " +
            "LIMIT ?";

    @Override
    public Book findByBookId(long bookId) {
        Book book = null;
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_BOOK_ID_SQL)) {
            statement.setLong(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    book = readBook(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding book " + bookId, e);
        }
        return book;
    }

    @Override
    public List<Book> findByCategoryId(long categoryId) {
        List<Book> books = new ArrayList<>();

        // TODO: Implement this method.

        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CATEGORY_ID_SQL)) {
            statement.setLong(1, categoryId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(readBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding the books " + categoryId, e);
        }


        return books;
    }

    @Override
    public List<Book> findRandomByCategoryId(long categoryId, int limit) {
        List<Book> books = new ArrayList<>();

        // TODO Implement this method
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_RANDOM_BY_CATEGORY_ID_SQL)) {
            statement.setLong(1, categoryId);
            statement.setInt(2, limit);
            try (ResultSet resultSet = statement.executeQuery()) {
                int count = 0;
                while (resultSet.next() && count < limit) {
                    count+=1;
                    books.add(readBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding the books " + categoryId, e);
        }

        return books;
    }

    @Override
    public List<Book> findByCategoryName(String name){
        List<Book> books = new ArrayList<>();
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BOOKS_BY_CATEGORY_NAME)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    books.add(readBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding the books " + name, e);
        }
        if(books.size() == 0){
            throw new BookstoreQueryDbException("Encountered a problem finding the books " + name);
        }
        return books;
    }

    @Override
    public List<Book> findRandomByCategoryName(String name, int limit){
        List<Book> books = new ArrayList<>();

        // TODO Implement this method
        try (Connection connection = JdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_RANDOM_BY_CATEGORY_NAME_SQL)) {
            statement.setString(1, name);
            statement.setInt(2, limit);
            try (ResultSet resultSet = statement.executeQuery()) {
                int count = 0;
                while (resultSet.next() && count < limit) {
                    count+=1;
                    books.add(readBook(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new BookstoreQueryDbException("Encountered a problem finding the books " + name, e);
        }

        return books;
    }



    private Book readBook(ResultSet resultSet) throws SQLException {
        long bookId = resultSet.getLong("book_id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        String description = resultSet.getString("description");
        int price = resultSet.getInt("price");
        int rating = resultSet.getInt("rating");
        boolean isPublic = resultSet.getBoolean("is_public");
        boolean isFeatured = resultSet.getBoolean("is_featured");
        long categoryId = resultSet.getLong("category_id");
        return new Book(bookId, title, author, description, price, rating, isPublic, isFeatured, categoryId);
    }

}
