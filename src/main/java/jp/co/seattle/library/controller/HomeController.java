package jp.co.seattle.library.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class HomeController {
	final static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String transitionHome(Model model) {
		//書籍の一覧情報を取得（タスク３）

		List<BookInfo> bookedBest = booksService.bookingBest();
		model.addAttribute("bookedBest", bookedBest);

		List<BookInfo> selectedBookInfo = booksService.getBookList();
		model.addAttribute("selectedBookInfo", selectedBookInfo);
		return "home";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searching(Locale locale, @RequestParam("searches") String searches, Model model) {
		List<BookInfo> searchsearch = booksService.searched(searches);
		model.addAttribute("selectedBookInfo", searchsearch);
		return "home";
	}
	
	@RequestMapping(value = "/sortFavorite", method = RequestMethod.GET)
	public String sortBookFavorite(Model model) {
		List<BookInfo> selectedBookInfo = booksService.sortBookListFavorite();
		model.addAttribute("selectedBookInfo", selectedBookInfo);
		return "home";
	}
	
	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 * @favorite
	 */
	@RequestMapping(value = "/favoriteBook", method = RequestMethod.GET)
	public String favorite(Locale locale, @RequestParam("bookId") int bookId, Model model) {
		booksService.favoriteUp(bookId);
		return "redirect://home";
	}

	/**
	 * Homeボタンからホーム画面に戻るページ
	 * 
	 * @param model
	 * @return
	 * @favorite
	 */
	@RequestMapping(value = "/nonFavoriteBook", method = RequestMethod.GET)
	public String nonFavorite(Locale locale, @RequestParam("bookId") int bookId, Model model) {
		booksService.favoriteDown(bookId);
		return "redirect://home";
	}

	@RequestMapping(value = "/sort", method = RequestMethod.GET)
	public String sort(Model model, @RequestParam("sortBook") String sort) {
		if (sort.equals("sortASC")) {
			List<BookInfo> selectedBookInfo = booksService.sortBookListAsc();
			model.addAttribute("selectedBookInfo", selectedBookInfo);
		}
		if (sort.equals("sortDESC")) {
			List<BookInfo> selectedBookInfo = booksService.sortBookListDesc();
			model.addAttribute("selectedBookInfo", selectedBookInfo);
		}
		if (sort.equals("sortAuthor")) {
			List<BookInfo> selectedBookInfo = booksService.sortBookListAuthor();
			model.addAttribute("selectedBookInfo", selectedBookInfo);
		}
		if (sort.equals("sortPublishDate")) {
			List<BookInfo> selectedBookInfo = booksService.sortBookListDate();
			model.addAttribute("selectedBookInfo", selectedBookInfo);
		}
		return "home";
	}

}