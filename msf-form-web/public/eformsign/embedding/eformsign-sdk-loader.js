(function(window, document) {
  'use strict'

  var loadPromises = {}

  function normalizeDomain(domain) {
    return String(domain || '').replace(/\/+$/, '')
  }

  function loadScript(src) {
    return new Promise(function(resolve, reject) {
      var script = document.createElement('script')
      script.src = src
      script.onload = resolve
      script.onerror = function() {
        reject(new Error('eformsign SDK 로드에 실패했습니다.'))
      }
      document.head.appendChild(script)
    })
  }

  function loadScriptsSequentially(baseUrl, paths) {
    return paths.reduce(function(promise, path) {
      return promise.then(function() {
        return loadScript(baseUrl + path)
      })
    }, Promise.resolve())
  }

  function patchMultiFileViewer(options) {
    if (!window.MultiFileViewer) {
      throw new Error('eformsign MultiFileViewer SDK를 찾을 수 없습니다.')
    }

    window.MultiFileViewer.prototype.open = function() {
      var iframe = document.getElementById('eformsign_iframe')
      var self = this

      if (!iframe) return

      var allowAttr = iframe.getAttribute('allow')
      if (!allowAttr) {
        iframe.setAttribute('allow', 'local-network-access')
      } else if (allowAttr.indexOf('local-network-access') === -1) {
        iframe.setAttribute('allow', allowAttr + '; local-network-access')
      }

      self._viewerInfo.fileData = window.fileData || []

      self.sendMessageEvent({
        type: 'multifile',
        fn: 'initViewerData',
        data: self._viewerInfo
      })
    }

    if (options && options.patchSendEvent) {
      window.MultiFileViewer.prototype.sendEvent = function(event) {
        this.sendMessageEvent({
          type: 'multifile',
          fn: 'sendEvent',
          action: {
            code: event.code,
            values: event.values
          }
        })
      }
    }
  }

  function loadSdk(type, domain, paths, onLoaded) {
    var baseUrl = normalizeDomain(domain)

    if (!baseUrl) {
      return Promise.reject(new Error('eformsign 도메인 정보가 없습니다.'))
    }

    var key = type + ':' + baseUrl

    if (loadPromises[key]) {
      return loadPromises[key]
    }

    loadPromises[key] = loadScriptsSequentially(baseUrl, paths)
      .then(onLoaded)
      .catch(function(e) {
        delete loadPromises[key]
        throw e
      })

    return loadPromises[key]
  }

  window.MsfEformsignSdk = {
    loadMultiFileSdk: function(domain, options) {
      return loadSdk(
        'multi-file',
        domain,
        [
          '/lib/js/efs_multi_file_viewer.js',
          '/lib/js/efs_multi_file_api.js'
        ],
        function() {
          patchMultiFileViewer(options || {})
        }
      )
    },
    loadEmbeddedSdk: function(domain) {
      return loadSdk(
        'embedded',
        domain,
        [
          '/plugins/jquery/jquery.min.js',
          '/lib/js/efs_embedded_v2.js'
        ],
        function() {
          if (!window.EformSignDocument) {
            throw new Error('eformsign embedded SDK를 찾을 수 없습니다.')
          }
        }
      )
    }
  }
})(window, document)
