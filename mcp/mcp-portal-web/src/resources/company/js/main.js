var cookiedomain = document.domain.replace(/^www\./, '');
if(cookiedomain) cookiedomain = '.'+cookiedomain;
//if(console) console.log('cookiedomain: '+cookiedomain);

// 쿠키 입력
function set_cookie(name, value, expirehours, domain)
{
    var today = new Date();
    today.setTime(today.getTime() + (60*60*1000*expirehours));
    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + today.toGMTString() + ";";
    if (domain) {
        document.cookie += "domain=" + domain + ";";
    }
}

// 쿠키 얻음
function get_cookie(name)
{
    var find_sw = false;
    var start, end;
    var i = 0;

    for (i=0; i<= document.cookie.length; i++)
    {
        start = i;
        end = start + name.length;

        if(document.cookie.substring(start, end) == name)
        {
            find_sw = true
            break
        }
    }

    if (find_sw == true)
    {
        start = end + 1;
        end = document.cookie.indexOf(";", start);

        if(end < start)
            end = document.cookie.length;

        //return document.cookie.substring(start, end);
        return unescape(document.cookie.substring(start, end));
    }
    return "";
}

// 쿠키 지움
function delete_cookie(name)
{
    var today = new Date();

    today.setTime(today.getTime() - 1);


    if(name == null || "".equals(name)) return;
    var filtered_name = name.replaceAll("\r", "").replaceAll("\n", "");
    var value = get_cookie(filtered_name);
    if(value != "")
        value.replaceAll("\r", "").replaceAll("\n", "");
        document.cookie = filtered_name + "=" + value + "; path=/; expires=" + today.toGMTString();
}

(function ($) {
    $(function () {

            var windowSize = $(window).width();


            function adjustMenu() {
                $('.dropdown').each(function (idx) {
                    var offsetLeft = parseInt($(this).offset().left);
                    var subMenu = $(this).find('.dropdown-menu');
                    //var subMenu = $(this).find('.dropdown-menu li:eq(0)');
                    if (idx < 3) {
                        subMenu.css({'padding-left': offsetLeft});
                    } else {
                        subMenu.css({'padding-left': offsetLeft - 150});
                    }

                })
            }

            adjustMenu();


            $(window).on('resize', function () {
                windowSize = $(window).width();

                $('.dropdown').each(function (idx) {
                    var offsetLeft = parseInt($(this).offset().left);
                    var subMenu = $(this).find('.dropdown-menu');
                    //var subMenu = $(this).find('.dropdown-menu li:eq(0)');
                    // subMenu.css({'padding-left': offsetLeft});

                    if (windowSize >= 768) {
                        if (idx < 3) {
                            subMenu.css({'padding-left': offsetLeft});
                        } else {
                            subMenu.css({'padding-left': offsetLeft - 150});
                        }
                    } else {
                        subMenu.css({'padding-left': 0})
                    }

                })

            })


            $('#footer select').change(function () {
                var url = this.value;
                window.open(url, '_blank');
            });


        $('body').scrollspy({target: '.sidebar'});

        // sidebar tooltip
        $('.sidebar').on('activate.bs.scrollspy', function () {
            $('[data-toggle="tooltip"]').tooltip('show');
        })

//		var _navbar_height = $('#navbar').height(); // dusthand

        // animation
        $.fn.scrollAni = function () {
            return this.each(function () {
                $(this).find('li:not(.top)').on({
                    click: function () {
                        var target = $(this).find('a').attr('href');
                        if(target==null || target=='undefined' || target=='#none' || !$(target).length) return;
                        $('html, body').stop().animate({
                            scrollTop: $(target).offset().top
                            //scrollTop: $(target).offset().top - _navbar_height
                        }, 400);
                        return false;
                    }
                });
                $(this).find('.top').on({
                    click: function (e) {
                        $('html, body').stop().animate({
                            scrollTop: 0
                        }, 200);
                    }
                });
            })
        }

        $('.sidebar').scrollAni();


        // IE Chceck
        var ua = navigator.userAgent.toLowerCase();
        if (ua.indexOf('msie') != -1 || ua.indexOf('trident') != -1) {
            var version = 11;
            ua = /msie ([0-9]{1,}[\.0-9]{0,})/.exec(ua);
            if (ua) {
                version = parseInt(ua[1]);
            }
            var classNames = '';
            classNames += ' is-ie';
            classNames += ' ie' + version;
            for (var i = version + 1; i <= 11; i++) {
                classNames += ' lt-ie' + i;
            }
            document.getElementsByTagName('html')[0].className += classNames;
        }

        // sub banner-list
        var bannerBtn = $('.banner-btn a');
        bannerBtn.on({
            'click' : function() {
                $('#banner-wrap').stop().animate({
                    height: 'toggle'
                },300);
                $(this).toggleClass('open');
                return false;
            }
        })


        // bxSlider-plugIn

        $('.bxslider').bxSlider({
            auto: true,
            autoControls: true,
            responsive : true,
            touchEnabled : true,
            //easing : 'ease-out',
            pause : 3000
        });



		/*// 해시링크 클릭이동시 픽스된 메뉴높이만큼 당겨주기
		$('#navbar .dropdown-menu a[href*=#]').on('click',function(e){
			if(!this.hash || !$(this.hash).attr('id')) return;
			//event.preventDefault();
			var target = 'body';
			if($('html').hasClass('is-ie')) target = 'html';
			$(target).scrollTop($(this.hash).offset().top - _navbar_height);
			return false;
		});*/

		/*$(window).load(function(){
			// 해시링크로 넘어오는경우 픽스된 메뉴높이만큼 당겨주기
			var hash_link = document.location.hash;
			if( $(hash_link).attr('id') ) { // hash에 해당하는 객체가 있으면 실행
				setTimeout( function(){
					$(document).scrollTop( $(hash_link).offset().top - _navbar_height );
				}, 10);
			}
		});*/


		// popup
		if($('html').hasClass('lt-ie9')) {
		$('.popup-notice .popup-chkbox label img').on('click',function(){
			$(this).closest('label').click();
			return false;
		}); }
		$('.popup-notice .popup-btnClose').on('click',function(){
			var $chkbox = $(this).closest('.popup-bottom').find('.popup-chkbox input[type=checkbox]');
			var $popup_wrap = $(this).closest('.popup-notice')
			var popup_name = $popup_wrap.attr('id');
			var flag = $chkbox.prop('checked');
			if(flag && popup_name) set_cookie(popup_name, '1', 24, cookiedomain);
			$popup_wrap.fadeOut('fast');
			return false;
		});
		$('.popup-notice').each(function(){
			var popup_name = $(this).attr('id');
			if(popup_name && get_cookie(popup_name)) $(this).remove();
			else $(this).fadeIn('fast');
		});

    });
})(jQuery);