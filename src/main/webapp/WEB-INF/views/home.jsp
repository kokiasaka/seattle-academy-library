<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>ホーム｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="/resources/css/home.css" />" rel="stylesheet" type="text/css">
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <h1>Home</h1>
        <a href="<%=request.getContextPath()%>/addBook" class="btn_add_book">書籍の追加</a>
         <a href="<%=request.getContextPath()%>/sortFavorite" class="btn_add_book">お気に入り</a>
        <form method="GET" action="sort">
            <p>
                <select name="sortBook">
                    <option value="sortASC">昇順</option>
                    <option value="sortDESC">降順</option>
                    <option value="sortAuthor">著者名順</option>
                    <option value="sortPublishDate">出版日順</option>
                </select>
            </p>
            <button>並び替え</button>
        </form>
        <%--  <a href="<%=request.getContextPath()%>/sortAuthor" class="btn_add_book">著者名順</a> <a href="<%=request.getContextPath()%>/sortPublishDate" class="btn_add_book">出版日最新順</a> --%>
       
        <div class="content_body">
            <c:if test="${!empty resultMessage}">
                <div class="error_msg">${resultMessage}</div>
            </c:if>
            <form action="search" method="get">
                <input type="search" name="searches" placeholder="キーワードを入力"> <input type="submit" value="検索"> <input type="button" onclick="location.href='https://www.amazon.co.jp/gp/bestsellers/books/ref=zg_bs_nav_0'" value="Amazon売れ筋ランキング">
            </form>
             <c:forEach var="bookInfo" items="${bookedBest}">
              <div class="booklist">
            <c:if test="${empty bookInfo.thumbnail}">
            <div class = "best">オススメbookedBest</div>
                <img class="book_noimg" src="resources/img/noImg.png">
            </c:if>
            <c:if test="${!empty bookInfo.thumbnail}">
             <div class = "best">オススメ</div>
                <img class="book_noimg" src="${bookInfo.thumbnail}">
            </c:if>
            <input type="hidden" name="bookId" value="${bookInfo.bookId}">
            <ul>
                <li class="book_title">${bookInfo.title}</li>
                <li class="book_author">${bookInfo.author}(著)</li>
            </ul>
             </c:forEach>
            <div>
                <div class="booklist">
                    <c:forEach var="bookInfo" items="${selectedBookInfo}">
                        <div class="books">
                            <c:if test="${bookInfo.favorite != 1}">
                                <form method="GET" action="favoriteBook" name="favorite">
                                    <p align="justify">
                                        <button class="btn btn-primary addtofavorite">お気に入りに登録</button>
                                        <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                                    </p>
                                </form>
                            </c:if>
                            <c:if test="${bookInfo.favorite == 1}">
                                <form method="GET" action="nonFavoriteBook" name="nonFavorite">
                                    <p align="justify">
                                        <span class="favoritedmark fade">★</span>
                                        <button class="btn btn-primary addtofavorite">お気に入り削除</button>
                                        <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                                    </p>
                                </form>
                            </c:if>
                            <form method="get" class="book_thumnail" action="editBook">
                                <a href="javascript:void(0)" onclick="this.parentNode.submit();"> <c:if test="${empty bookInfo.thumbnail}">
                                        <img class="book_noimg" src="resources/img/noImg.png">
                                    </c:if> <c:if test="${!empty bookInfo.thumbnail}">
                                        <img class="book_noimg" src="${bookInfo.thumbnail}">
                                    </c:if>
                                </a> <input type="hidden" name="bookId" value="${bookInfo.bookId}">
                            </form>
                            <ul>
                                <li class="book_title">${bookInfo.title}</li>
                                <li class="book_author">${bookInfo.author}(著)</li>
                                <li class="book_publisher">出版社:${bookInfo.publisher}</li>
                                <li class="book_publish_date">出版日:${bookInfo.publishDate}</li>
                            </ul>
                            <input type="button" onclick="location.href='https://www.amazon.co.jp/s?k=${bookInfo.title}&ref=nb_sb_noss'" value="購入">
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>
</body>
</html>
