package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.dto.UserInfo;
import jp.co.seattle.library.service.UsersService;

/**
 * ログインコントローラー
 */
@Controller /** APIの入り口 */
public class PasswordResetController {
	final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UsersService usersService;

	@RequestMapping(value = "/newPassword", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	public String PasswordReset(Model model) {
		return "passwordReset";
	}

/**
	 * ログイン処理
	 *
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @param model
	 * @return ホーム画面に遷移
	 */
	@RequestMapping(value = "/newCrestePassword", method = RequestMethod.POST)
	public String createAccount(Locale locale, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("passwordForCheck") String passwordForCheck,
			Model model) {

		if (password.matches("^[0-9a-zA-Z]+$") && password.length() >= 8) {
			if (password.equals(passwordForCheck)) {
				// パラメータで受け取ったアカウント情報をDtoに格納する。
				UserInfo userInfo = new UserInfo();
				userInfo.setEmail(email);
				userInfo.setPassword(password);
				usersService.registUser(userInfo);
				return "redirect:/login";
			} else {
				model.addAttribute("errorMessage", "パスワードが一致しません。");
				return "newCreateAccount";
			}
		} else {
			model.addAttribute("errorMessage", "パスワードは8文字以上かつ半角英数字にしてください");
			return "newCreateAccoun";
		}
	}
}
