package jp.co.seattle.library.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

@Controller
public class OutputBookController {

	@Autowired
	private BooksService bookService;
	
	@Transactional
	@RequestMapping(value = "/outPutBook", method = RequestMapping.POST)
	public String OutPutBook(Locale locale, @RequestParam("bookId") int bookId, RedirectAttributes redirectAttributes,
		Model model) {
	
	
	//書籍情報取得
	BookDetailsInfo bookInfo = booksService.getBookInfo(bookId);
	
	//API呼び出し
	String responseMessage = boooksService.callAPI(bookInfo);
	
}
	
}
