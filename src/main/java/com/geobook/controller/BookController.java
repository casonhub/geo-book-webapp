package com.geobook.controller;

import com.geobook.util.DBConnection;
import com.geobook.model.Book;
import com.geobook.model.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookController {

    // Fetch all books
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setIsbn(resultSet.getString("isbn"));
                books.add(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    // Fetch locations for a book
    public List<Location> getLocationsByBookId(int bookId) {
        List<Location> locations = new ArrayList<>();
        String query = "SELECT * FROM locations WHERE book_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Location location = new Location();
                    location.setId(resultSet.getInt("id"));
                    location.setName(resultSet.getString("name"));
                    location.setLatitude(resultSet.getDouble("latitude"));
                    location.setLongitude(resultSet.getDouble("longitude"));
                    location.setChapterId(resultSet.getInt("chapter_id"));
                    locations.add(location);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return locations;
    }
}
