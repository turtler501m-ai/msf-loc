"use strict";

var _typeof =
    typeof Symbol === "function" && typeof Symbol.iterator === "symbol"
        ? function (obj) {
          return typeof obj;
        }
        : function (obj) {
          return obj && typeof Symbol === "function" && obj.constructor === Symbol
              ? "symbol"
              : typeof obj;
        };

var _createClass = (function () {
  function defineProperties(target, props) {
    for (var i = 0; i < props.length; i++) {
      var descriptor = props[i];
      descriptor.enumerable = descriptor.enumerable || false;
      descriptor.configurable = true;
      if ("value" in descriptor) descriptor.writable = true;
      Object.defineProperty(target, descriptor.key, descriptor);
    }
  }
  return function (Constructor, protoProps, staticProps) {
    if (protoProps) defineProperties(Constructor.prototype, protoProps);
    if (staticProps) defineProperties(Constructor, staticProps);
    return Constructor;
  };
})();

function _classCallCheck(instance, Constructor) {
  if (!(instance instanceof Constructor)) {
    throw new TypeError("Cannot call a class as a function");
  }
}

var NmcpCommonComponent = (function () {
  function NmcpCommonComponent() {
    _classCallCheck(this, NmcpCommonComponent);
  }

  _createClass(NmcpCommonComponent, [
    {
      key: "init",

      /**init */
      value: function init() {
        KTM.initialize();
      },

      /** Common method */
    },
    {
      key: "toHTML",
      value: function toHTML(sourceString) {
        var div = document.createElement("div");
        div.innerHTML = sourceString.trim();
        return div.firstChild;
      },

      /** AJAX */
    },
    {
      key: "ajaxGet",
      value: function ajaxGet(url, data, type, callback) {
        $.ajax({
          url: url,
          type: "GET",
          cache : false,
          dataType: type,
          data: data,
          async: false,
          success: function success(resp) {
            if (typeof callback == "function") {
              callback(resp);
              return;
            } else if (typeof callback == "string") {
              $(callback).html(resp);
              return;
            } else {
              this._respValue = resp;
            }
            return resp;
          },
        });
      },
    },
    {
      key: "ajaxPost",
      value: function ajaxPost(url, data, type, callback) {
        $.ajax({
          url: url,
          type: "POST",
          dataType: type,
          data: data,
          async: false,
          success: function success(resp) {
            if (typeof callback == "function") {
              callback(resp);
              return;
            } else if (typeof callback == "string") {
              $(callback).html(resp);
              return;
            } else {
              this._respValue = resp;
            }
            return resp;
          },
        });
      },

      /** TAB method*/
    },
    {
      key: "addTab",
      value: function addTab(
          targetTab,
          htmlTab,
          targetBody,
          htmlBody,
          callback
      ) {
        $(targetBody).append(htmlBody);
        $(targetTab).append(htmlTab);
        if (
            (typeof targetBody === "undefined"
                ? "undefined"
                : _typeof(targetBody)) == "object"
        ) {
          var el = targetBody;
        } else {
          var el = $(targetBody)[0];
        }
        var instance = KTM.Tab.getInstance(el);
        instance.update();
        var tabId = $(htmlBody).attr("id");
        if (typeof callback == "function") {
          callback("#" + tabId);
        }
      },
    },
    {
      key: "addSingleTab",
      value: function addSingleTab(tabId, buttonClass, html, callback) {
        $(tabId).append(html);
        $(buttonClass)
            .off()
            .on("click", function () {});
        $(buttonClass).on("click", function () {
          $(buttonClass).removeClass("is-active");
          $(this).addClass("is-active");
          $(buttonClass).attr("aria-hidden", "false");
          $(this).attr("aria-hidden", "true");
        });
        if (typeof callback == "function") {
          callback("#" + tabId);
        }
      },

      /** Accordion method */
    },
    {
      key: "addAccordion",
      value: function addAccordion(target, html, callback) {
        if (
            (typeof target === "undefined" ? "undefined" : _typeof(target)) ==
            "object"
        ) {
          var accContainer = target;
        } else {
          var accContainer = document.querySelector(target);
        }

        var accEl = accContainer.closest(".c-accordion");
        var instance = KTM.Accordion.getInstance(accEl);
        accContainer.appendChild(this.toHTML(html));
        instance.update();
        if (typeof callback == "function") {
          callback(target);
        }
      },

      /** Accordion2 method */
    },
    {
      key: "addAccordion2",
      value: function addAccordion2(target) {
        var el = document.querySelector(target);
        var instance = KTM.Accordion.getInstance(el)
            ? KTM.Accordion.getInstance(document.querySelector(el))
            : new KTM.Accordion(el);
        instance.update();
      },

      /**alert */
    },
    {
      key: "alert",
      value: function alert(msg, callback, eventType = 'close') {
        if (typeof callback == "function") {
          new KTM.Alert(msg, {
            [eventType]: function() {
              setTimeout(function () {
                callback();
              }, eventType === 'close' ? 420 : 0);
            },
          });
        } else {
          new KTM.Alert(msg);
        }
      },


      /**alert */
    },
    {
      key: "alert2",
      value: function alert2(msg, callback, btnTxt) {
        if (typeof callback == "function") {
          new KTM.Alert(msg, {
            open: function open() {
              $(".c-button--primary,.c-button--w460").text(btnTxt);
            },
            close: function close() {
              //callback();
              setTimeout(function () {
                callback();
              }, 420);
            },
          });
        } else {
          new KTM.Alert(msg);
        }
      },

      /** dialog */
    },
    {
      key: "closePopup",
      value: function closePopup(target) {
        //var el = document.querySelector(target);
        //var instance = KTM.Dialog.getInstance(el);
        //instance.close();
        //new KTM.Dialog(document.querySelector("#modalArs")).close();

        var el = document.querySelector(target);
        var modal = KTM.Dialog.getInstance(el)
            ? KTM.Dialog.getInstance(el)
            : new KTM.Dialog(el);
        modal.close();
      },
    },
    {
      key: "openPopup",
      value: function openPopup(target) {
        var el = document.querySelector("#modalArs");
        var modal = KTM.Dialog.getInstance(el)
            ? KTM.Dialog.getInstance(el)
            : new KTM.Dialog(el);
        modal.open();
      },

      /** */
      //data-ignore="true"

      //1)  data-dialog-trigger 踰꾪듉???대┃?섎㈃ ?앹뾽???ㅽ뵂?⑸땲??
      //2)  ?앹뾽?? data-dialog-close

      // ?앹뾽 湲곕뒫 異붽? ?덉젙 : open, opened, close, closed
    },
    {
      key: "respValue",
      set: function set(value) {
        if (!value) {
          return;
        }
        this._respValue = value;
      },
      get: function get() {
        return this._respValue;
      },
    },
  ]);

  return NmcpCommonComponent;
})();
