package jp.co.seattle.library.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

@Controller
public class OutputBookController {
	
	@Autowired
	private BooksService booksService;
	
	@Transactional
	@RequestMapping(value = "/outPutBook", method = RequestMethod.POST)
	public String OutPutBook(Locale locale, @RequestParam("bookId") int bookId, RedirectAttributes redirectAttributes,
		Model model) {
	
	
	//書籍情報取得
	BookDetailsInfo bookInfo = booksService.getBookInfo(bookId);
	
	//API呼び出し
	String responseMessage = booksService.callAPI(bookInfo);
	
	if(responseMessage.equals("書籍出力に成功しました")) {
		redirectAttributes.addFlashAttribute("successMessage",responseMessage);
	}else {
		redirectAttributes.addFlashAttribute("errorMessage",responseMessage);	
	}
	
	return "redirect:/editBook?bookId=" +bookId;
	
}
	
}
