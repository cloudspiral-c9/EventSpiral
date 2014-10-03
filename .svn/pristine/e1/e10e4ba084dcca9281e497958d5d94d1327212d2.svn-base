document.write('<script type="text/javascript" src="/EventSpiral/dwr/interface/LoginController.js"></script>');
document.write('<script type="text/javascript" src="/EventSpiral/dwr/interface/LogoutController.js"></script>');
document.write('<script type="text/javascript" src="js/jquery.cookie.js"></script>');
document.write('<script type="text/javascript" src="js/jquery.serialize.js"></script>');

var header
	= '<div id="logo"></div>'
	+ '<div id="login_wrapper" />'
	+ '<hr>'
	+ '<div id="menu_wrapper" />'
	+ '<hr>';

var defaultForm
	= '<form id="login_form">'
	+ '  ユーザID <input type="text" name="userId"/>'
	+ '  パスワード <input type="password" name="pass" />'
	+ '  <input type="button" onclick="login()" value="ログイン" />'
	+ '  <span id="header_error_msg" class="error"></span>'
	+ '</form>';

var loggedInForm
	= '<form id="login_form">'
	+ '  <span>ようこそ <span class="uid"></span></span>'
	+ '  <input type="button" onclick="logout()" value="ログアウト" />'
	+ '</form>';


var MENU_SEP = ' | ';

// メニューの各リンク
var a_eventList           = '<a href="./">イベント一覧</a>';
var a_boughtTicketList    = '<a href="boughtTicketList.html">購入済みチケット一覧</a>';
var a_registerEvent       = '<a href="registerEvent.html">新規イベント登録</a>';
var a_registeredEventList = '<a href="registeredEventList.html">登録済みイベント一覧</a>';
var a_signup              = '<a href="signup.html">新規アカウント登録</a>';
var a_signupPromoter      = '<a href="signupPromoter.html">新規興行主登録</a>';

// ロールごとのメニューバー
var defaultMenu  = a_eventList + MENU_SEP + a_signup;
var userMenu     = a_eventList + MENU_SEP + a_boughtTicketList;
var promoterMenu = a_eventList + MENU_SEP + a_boughtTicketList + MENU_SEP + a_registeredEventList + MENU_SEP + a_registerEvent;
var adminMenu    = a_eventList + MENU_SEP + a_boughtTicketList + MENU_SEP + a_signupPromoter;

// ヘッダ初期化処理

/**
 * @処理概要
 * ヘッダ初期化処理
 *
 * @パラメータ
 * なし
 *
 * @処理フロー
 * - var headerの内容を#headerの要素にセットする
 * - ログインしていなければ
 * 	- #login_wrapperにdefaultFormをセットし、#menu_wrapperにdefaultMenuをセットする
 * - ログインしていれば
 * 	- #login_wrapperにloggedInFormをセットする
 * 	- ヘッダのclass名に'uid'が指定されている要素にユーザIDをセットする
 * 	- ログインユーザのroleがuserであれば#menu_wrapperにuserMenuを追加
 * 	- ログインユーザのroleがpromoterであれば#menu_wrapperにpromoterMenuを追加
 * 	- ログインユーザのroleがadministratorであれば#menu_wrapperにadminMenuを追加
 *
 * @必要な知識
 * - jquery.cookieの使い方
 * - jquery html()関数の使い方
 *
 */
function initHeader() {
	$('#header').html(header);

	// 非ログイン時
	if ($.cookie('userId') == undefined) {
		$('#login_wrapper').html(defaultForm);
		$('#menu_wrapper').html(defaultMenu);
	// ログイン時
	} else {
		$('#login_wrapper').html(loggedInForm);
		$('[class="uid"]').html($.cookie('userId'));

		if ($.cookie('role') == 'user') {
			$('#menu_wrapper').append(userMenu);
		} else if ($.cookie('role') == 'promoter') {
			$('#menu_wrapper').append(promoterMenu);
		} else if ($.cookie('role') == 'administrator') {
			$('#menu_wrapper').append(adminMenu);
		}
	}
}

/**
 * @処理概要
 * ログイン処理
 * dwrLogin():フォームからログインのケースとdwrLogin(uid, pass):引数ログインのケースのオーバーロード
 *
 * @パラメータ
 * uid - userId
 * pass - password
 *
 * @処理フロー
 * - uidとpassがnullであれば
 * 	- #login_formの入力情報をjsonオブジェクトに変換する(tem-utils.jsのserialiseJson()を利用してよい）
 * - uidとpassがnullでなければ
 * 	- uidとpassをそれぞれuserIdとpassをkeyに持つjsonオブジェクトとして保存する
 * - LoginController.executeを呼び出す。引数は上で作成されたjsonオブジェクト。
 * - callbackされる匿名関数は引数としてexecuteメソッドの返り値を取り、下記を実装していること
 * 	- userIdというkeyを持つcookieにexecuteメソッドの引数に含まれるuserIdをセットする
 *  - roleというkeyを持つcookieにexecuteメソッドの返り値に含まれるroleをセットする
 *  - 画面を更新する
 * - exceptionHandlerでは、文字列と例外オブジェクトを引数に取る匿名関数を指定し、文字列の内容で#header_error_msgを更新すること
 *
 * @必要な知識
 * - jquery.cookieの使い方
 * - jquery html()関数の使い方
 * - tem-utils.jsのserialiseJson()の使い方
 *
 */
function login(uid, pass) {
	var form;
	if (uid == null && pass == null) {
		form = $('#login_form').serializeJson();
	} else {
		form = {userId:uid, pass:pass};
	}

	LoginController.execute(form, {
		callback: function(data) {
			$.cookie('userId', form.userId);
			$.cookie('role', data.role);

			location.reload();
		},
		exceptionHandler: function(msg, err) {
			$('#header_error_msg').text(msg);
		}
	});
}

// ログアウト処理．クッキーを削除しヘッダを更新
/**
 * @処理概要
 * ログアウト処理
 *
 * @パラメータ
 * なし
 *
 * @処理フロー
 * - LogoutController.executeを呼び出す。
 * - callbackされる匿名関数は下記を実装していること
 * 	- userIdというkeyを持つcookieにnullをセットする
 *  - roleというkeyを持つcookieにnullをセットする
 *  - 画面を更新する
 *
 * @必要な知識
 * - jquery.cookieの使い方
 *
 */
function logout(){
	LogoutController.execute({
		callback: function() {
			$.cookie('userId', null);
			$.cookie('role', null);

			location.reload();
		}
	});
}

