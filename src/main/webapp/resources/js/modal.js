$(function () {
  $('.js-open').click(function () {
    $('#overlay, .modal-window').fadeIn();
  });
  $('.js-close').click(function () {
    $('#overlay, .modal-window').fadeOut();
  });
});
$(function () {
  $('.js-open').click(function () {
    $('#overlay, .modal-window').fadeIn();
  });
  // オーバーレイクリックでもモーダルを閉じるように
  $('.js-close , #overlay').click(function () {
    $('#overlay, .modal-window').fadeOut();
  });
});