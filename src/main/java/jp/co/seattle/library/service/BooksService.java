package jp.co.seattle.library.service;

import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 * booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> getBookList() {

		// TODO 書籍名の昇順で書籍情報を取得するようにSQLを修正（タスク３）

		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT id, title, author, publisher, publish_date, isbn, description, thumbnail_url, thumbnail_name, reg_date, upd_date,favorite,genre,review FROM books ORDER BY title ASC",
				new BookInfoRowMapper());

		return getedBookList;
	}

	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public BookDetailsInfo getBookInfo(int bookId) {
		String sql = "SELECT id, title, author, publisher, publish_date, isbn, description, thumbnail_url, thumbnail_name,favorite,genre,review FROM books WHERE books.id = ? ORDER BY title ASC;";

		BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper(), bookId);

		return bookDetailsInfo;
	}

	/**
	 * 書籍を登録する
	 *
	 * @param bookInfo 書籍情報
	 * @return bookId 書籍ID
	 */
	public int registBook(BookDetailsInfo bookInfo) {
		// TODO 取得した書籍情報を登録し、その書籍IDを返却するようにSQLを修正（タスク４）
		String sql = "INSERT INTO books(title, author, publisher, publish_date, thumbnail_name, thumbnail_url,isbn, description, reg_date, upd_date,favorite,genre,review)VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), ?, ?, ?) RETURNING id;";

		int bookId = jdbcTemplate.queryForObject(sql, int.class, bookInfo.getTitle(), bookInfo.getAuthor(),
				bookInfo.getPublisher(), bookInfo.getPublishDate(), bookInfo.getThumbnailName(),
				bookInfo.getThumbnailUrl(), bookInfo.getIsbn(), bookInfo.getDescription(), bookInfo.getFavorite(),bookInfo.getGenre(),bookInfo.getReview());
		return bookId;
	}

	/**
	 * 書籍を削除する
	 * 
	 * @param bookId 書籍ID
	 */
	public void deleteBook(int bookId) {
		// TODO 対象の書籍を削除するようにSQLを修正（タスク6）
		String sql = "DELETE FROM books WHERE books.id = ?;";
		jdbcTemplate.update(sql, bookId);
	}

	/**
	 * 書籍情報を更新する
	 * 
	 * @param bookInfo
	 */
	public void updateBook(BookDetailsInfo bookInfo) {
		String sql;
		if (bookInfo.getThumbnailUrl() == null) {
			// TODO 取得した書籍情報を更新するようにSQLを修正（タスク５）
			sql = "UPDATE books SET title = ?,author = ?,publisher = ?,publish_date = ?,isbn = ?,description = ?,upd_date = now(),genre = ?,review = ? WHERE id = ?;";
			jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
					bookInfo.getPublishDate(), bookInfo.getIsbn(), bookInfo.getDescription(),bookInfo.getGenre(),bookInfo.getReview(), bookInfo.getBookId());
		} else {
			// TODO 取得した書籍情報を更新するようにSQLを修正（タスク５）
			sql = "UPDATE books SET title = ?,author = ?,publisher = ?,publish_date = ?,thumbnail_name = ?,thumbnail_url = ?,isbn = ?,description = ?,upd_date = now(),genre = ?,review = ? WHERE id=?;";
			jdbcTemplate.update(sql, bookInfo.getTitle(), bookInfo.getAuthor(), bookInfo.getPublisher(),
					bookInfo.getPublishDate(), bookInfo.getThumbnailName(), bookInfo.getThumbnailUrl(),
					bookInfo.getIsbn(), bookInfo.getDescription(),bookInfo.getGenre(), bookInfo.getReview(),bookInfo.getBookId());
		}

	}

	public List<BookInfo> searched(String searches) {
		// TODO 書籍名の昇順で書籍情報を取得するようにSQLを修正
		List<BookInfo> mina = jdbcTemplate.query(
				"SELECT * FROM books WHERE title LIKE concat('%',?,'%') ORDER BY title ASC",
				new BookInfoRowMapper(), searches);
		return mina;
	}

	public List<BookInfo> sortBookListAsc() {
		// タスク7昇順
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY title ASC ",
				new BookInfoRowMapper());
		return getedBookList;
	}

	public List<BookInfo> sortBookListDesc() {
		// タスク7降順
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY title DESC ",
				new BookInfoRowMapper());
		return getedBookList;
	}

	public List<BookInfo> sortBookListAuthor() {
		// タスク7著者名順
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY author ASC ",
				new BookInfoRowMapper());
		return getedBookList;
	}

	public List<BookInfo> sortBookListDate() {
		// タスク7著者名順
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY publish_date ASC ",
				new BookInfoRowMapper());

		return getedBookList;
	}

	public List<BookInfo> sortBookListFavorite() {
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books WHERE favorite = 1 ORDER BY title  ASC",
				new BookInfoRowMapper());

		return getedBookList;
	}

	public void favoriteUp(int bookId) {
		String sql;
		sql = "UPDATE books SET favorite = 1 WHERE id = ?;";
		jdbcTemplate.update(sql, bookId);
	}

	public void favoriteDown(int bookId) {
		String sql;
		sql = "UPDATE books SET favorite = 0 WHERE id = ?;";
		jdbcTemplate.update(sql, bookId);

	}

	public List<BookInfo> bookingBest() {
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT * FROM books ORDER BY RANDOM() LIMIT 1;",
				new BookInfoRowMapper());
		return getedBookList;
	}


	@Bean
	public RestTemplate restTemplate() {
	return new RestTemplate();
	
}

	public String callAPI(BookDetailsInfo bookInfo) {
		
		ResourceBundle rb = ResourceBundle.getBundle("output");
		String url = rb.getString ("url");
	}
	}

