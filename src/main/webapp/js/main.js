$(document).ready(function () {
  $('.vote-up a, .vote-down a').on('click', function () {
    var el = $(this);
    var up = el.parents('.row').first().find('.vote-up');
    var down = el.parents('.row').first().find('.vote-down');
    $.getJSON(el.attr('href'), function (response) {
      up.removeClass('label-success');
      down.removeClass('label-inverse');
      if (response.type == -1) {
        down.addClass('label-inverse');
      } else if (response.type == 1) {
        up.addClass('label-success');
      }
      el.parents('.row').first().find('.votes').html(response.count);
    });
    return false;
  });

  $('.add-comment').on('click', function (e) {
    var el = $(this);
    e.preventDefault();
    e.stopPropagation();
    el.hide();
    var template = $('#comment-template').html();
    el.parent().append(template);
    return false;
  });

  $('body').on('submit', '.comment-form', function () {
    var el = $(this);
    var a = el.parent().find('.add-comment');
    $.ajax({
      method: 'post',
      url: a.attr('href'),
      data: {
        'comment': el.find('textarea').val()
      },
      dataType: 'json'
    }).done(function (response) {
          if (response) {
            a.show();
            a.closest('.row').find('.comments').append(response.content);
            el.remove();
          }
        });

    return false;
  });
})
;