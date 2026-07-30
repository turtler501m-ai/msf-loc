var segCropModule = (() => {
  var _scriptDir = typeof document !== "undefined" && document.currentScript ? document.currentScript.src : undefined;

  return function (segCropModule) {
    segCropModule = segCropModule || {};

    var f;
    f || (f = typeof segCropModule !== "undefined" ? segCropModule : {});
    var aa, ba;
    f.ready = new Promise(function (a, b) {
      aa = a;
      ba = b;
    });
    f.la || (f.la = 0);
    f.la++;
    f.ENVIRONMENT_IS_PTHREAD ||
      (function (a) {
        function b(m, p, r) {
          var q = new XMLHttpRequest();
          q.open("GET", m, !0);
          q.responseType = "arraybuffer";
          q.onprogress = function (t) {
            var g = p;
            t.total && (g = t.total);
            if (t.loaded) {
              q.L ? (f.Y[m].loaded = t.loaded) : ((q.L = !0), f.Y || (f.Y = {}), (f.Y[m] = { loaded: t.loaded, total: g }));
              var v = (g = t = 0),
                x;
              for (x in f.Y) {
                var A = f.Y[x];
                t += A.total;
                g += A.loaded;
                v++;
              }
              t = Math.ceil((t * f.la) / v);
              f.setStatus && f.setStatus("Downloading data... (" + g + "/" + t + ")");
            } else !f.Y && f.setStatus && f.setStatus("Downloading data...");
          };
          q.onerror = function () {
            throw Error("NetworkError for: " + m);
          };
          q.onload = function () {
            if (200 == q.status || 304 == q.status || 206 == q.status || (0 == q.status && q.response)) r(q.response);
            else throw Error(q.statusText + " : " + q.responseURL);
          };
          q.send(null);
        }
        function c(m) {
          console.error("package error:", m);
        }
        function d() {
          function m(t, g, v) {
            this.start = t;
            this.end = g;
            this.audio = v;
          }
          function p(t) {
            if (!t) throw "Loading data file failed." + Error().stack;
            if (!(t instanceof ArrayBuffer)) throw "bad input to processPackageData" + Error().stack;
            t = new Uint8Array(t);
            m.prototype.ka = t;
            t = a.files;
            for (var g = 0; g < t.length; ++g) m.prototype.L[t[g].filename].onload();
            f.removeRunDependency("datafile_SegCrop.data");
          }
          f.FS_createPath("/", "assets", !0, !0);
          m.prototype = {
            L: {},
            open: function (t, g) {
              this.name = g;
              this.L[g] = this;
              f.addRunDependency("fp " + this.name);
            },
            onload: function () {
              this.ja(this.ka.subarray(this.start, this.end));
            },
            ja: function (t) {
              f.FS_createDataFile(this.name, null, t, !0, !0, !0);
              f.removeRunDependency("fp " + this.name);
              this.L[this.name] = null;
            },
          };
          for (var r = a.files, q = 0; q < r.length; ++q) new m(r[q].start, r[q].end, r[q].audio || 0).open("GET", r[q].filename);
          f.addRunDependency("datafile_SegCrop.data");
          f.Ba || (f.Ba = {});
          f.Ba["SegCrop.data"] = { $a: !1 };
          n ? (p(n), (n = null)) : (k = p);
        }
        "object" === typeof window
          ? window.encodeURIComponent(window.location.pathname.toString().substring(0, window.location.pathname.toString().lastIndexOf("/")) + "/")
          : "undefined" === typeof process && "undefined" !== typeof location && encodeURIComponent(location.pathname.toString().substring(0, location.pathname.toString().lastIndexOf("/")) + "/");
        "function" !== typeof f.locateFilePackage ||
          f.locateFile ||
          ((f.locateFile = f.locateFilePackage), l("warning: you defined Module.locateFilePackage, that has been renamed to Module.locateFile (using your locateFilePackage for now)"));
        var e = f.locateFile ? f.locateFile("SegCrop.data", "") : "../../detect/crop/SegCrop.data",
          h = a.remote_package_size,
          k = null,
          n = f.getPreloadedPackage ? f.getPreloadedPackage(e, h) : null;
        n ||
          b(
            e,
            h,
            function (m) {
              k ? (k(m), (k = null)) : (n = m);
            },
            c,
          );
        f.calledRun ? d() : (f.preRun || (f.preRun = []), f.preRun.push(d));
      })({ files: [{ filename: "/assets/20250205_seg.onnx", start: 0, end: 2294484 }], remote_package_size: 2294484 });
    var ca = Object.assign({}, f),
      da = "./this.program",
      u = "",
      ea,
      fa;
    "undefined" != typeof document && document.currentScript && (u = document.currentScript.src);
    _scriptDir && (u = _scriptDir);
    0 !== u.indexOf("blob:") ? (u = u.substr(0, u.replace(/[?#].*/, "").lastIndexOf("/") + 1)) : (u = "");
    ea = (a) => {
      var b = new XMLHttpRequest();
      b.open("GET", a, !1);
      b.send(null);
      return b.responseText;
    };
    fa = (a, b, c) => {
      var d = new XMLHttpRequest();
      d.open("GET", a, !0);
      d.responseType = "arraybuffer";
      d.onload = () => {
        200 == d.status || (0 == d.status && d.response) ? b(d.response) : c();
      };
      d.onerror = c;
      d.send(null);
    };
    var ha = f.print || console.log.bind(console),
      l = f.printErr || console.warn.bind(console);
    Object.assign(f, ca);
    ca = null;
    f.thisProgram && (da = f.thisProgram);
    var w;
    f.wasmBinary && (w = f.wasmBinary);
    var noExitRuntime = f.noExitRuntime || !0;
    "object" != typeof WebAssembly && y("no native wasm support detected");
    var ia,
      ja = !1,
      ka = "undefined" != typeof TextDecoder ? new TextDecoder("utf8") : void 0;
    function z(a, b, c) {
      var d = b + c;
      for (c = b; a[c] && !(c >= d); ) ++c;
      if (16 < c - b && a.buffer && ka) return ka.decode(a.subarray(b, c));
      for (d = ""; b < c; ) {
        var e = a[b++];
        if (e & 128) {
          var h = a[b++] & 63;
          if (192 == (e & 224)) d += String.fromCharCode(((e & 31) << 6) | h);
          else {
            var k = a[b++] & 63;
            e = 224 == (e & 240) ? ((e & 15) << 12) | (h << 6) | k : ((e & 7) << 18) | (h << 12) | (k << 6) | (a[b++] & 63);
            65536 > e ? (d += String.fromCharCode(e)) : ((e -= 65536), (d += String.fromCharCode(55296 | (e >> 10), 56320 | (e & 1023))));
          }
        } else d += String.fromCharCode(e);
      }
      return d;
    }
    function B(a, b) {
      return a ? z(C, a, b) : "";
    }
    function la(a, b, c, d) {
      if (!(0 < d)) return 0;
      var e = c;
      d = c + d - 1;
      for (var h = 0; h < a.length; ++h) {
        var k = a.charCodeAt(h);
        if (55296 <= k && 57343 >= k) {
          var n = a.charCodeAt(++h);
          k = (65536 + ((k & 1023) << 10)) | (n & 1023);
        }
        if (127 >= k) {
          if (c >= d) break;
          b[c++] = k;
        } else {
          if (2047 >= k) {
            if (c + 1 >= d) break;
            b[c++] = 192 | (k >> 6);
          } else {
            if (65535 >= k) {
              if (c + 2 >= d) break;
              b[c++] = 224 | (k >> 12);
            } else {
              if (c + 3 >= d) break;
              b[c++] = 240 | (k >> 18);
              b[c++] = 128 | ((k >> 12) & 63);
            }
            b[c++] = 128 | ((k >> 6) & 63);
          }
          b[c++] = 128 | (k & 63);
        }
      }
      b[c] = 0;
      return c - e;
    }
    function ma(a) {
      for (var b = 0, c = 0; c < a.length; ++c) {
        var d = a.charCodeAt(c);
        127 >= d ? b++ : 2047 >= d ? (b += 2) : 55296 <= d && 57343 >= d ? ((b += 4), ++c) : (b += 3);
      }
      return b;
    }
    var na, D, C, oa, E, F;
    function pa() {
      var a = ia.buffer;
      na = a;
      f.HEAP8 = D = new Int8Array(a);
      f.HEAP16 = oa = new Int16Array(a);
      f.HEAP32 = E = new Int32Array(a);
      f.HEAPU8 = C = new Uint8Array(a);
      f.HEAPU16 = new Uint16Array(a);
      f.HEAPU32 = F = new Uint32Array(a);
      f.HEAPF32 = new Float32Array(a);
      f.HEAPF64 = new Float64Array(a);
    }
    var G,
      qa = [],
      ra = [],
      sa = [];
    function ta() {
      var a = f.preRun.shift();
      qa.unshift(a);
    }
    var H = 0,
      ua = null,
      I = null;
    function va() {
      H++;
      f.monitorRunDependencies && f.monitorRunDependencies(H);
    }
    function wa() {
      H--;
      f.monitorRunDependencies && f.monitorRunDependencies(H);
      if (0 == H && (null !== ua && (clearInterval(ua), (ua = null)), I)) {
        var a = I;
        I = null;
        a();
      }
    }
    function y(a) {
      if (f.onAbort) f.onAbort(a);
      a = "Aborted(" + a + ")";
      l(a);
      ja = !0;
      a = new WebAssembly.RuntimeError(a + ". Build with -sASSERTIONS for more info.");
      ba(a);
      throw a;
    }
    function xa() {
      return J.startsWith("data:application/octet-stream;base64,");
    }
    var J;
    J = "../../detect/crop/SegCrop.wasm";
    if (!xa()) {
      var ya = J;
      J = f.locateFile ? f.locateFile(ya, u) : u + ya;
    }
    function za() {
      var a = J;
      try {
        if (a == J && w) return new Uint8Array(w);
        throw "both async and sync fetching of the wasm failed";
      } catch (b) {
        y(b);
      }
    }
    function Aa() {
      return w || "function" != typeof fetch
        ? Promise.resolve().then(function () {
            return za();
          })
        : fetch(J, { credentials: "same-origin" })
            .then(function (a) {
              if (!a.ok) throw "failed to load wasm binary file at '" + J + "'";
              return a.arrayBuffer();
            })
            .catch(function () {
              return za();
            });
    }
    var K, Ba;
    function Ca(a) {
      for (; 0 < a.length; ) a.shift()(f);
    }
    function Da(a) {
      this.V = a - 24;
      this.Ia = function (b) {
        F[(this.V + 4) >> 2] = b;
      };
      this.Fa = function (b) {
        F[(this.V + 8) >> 2] = b;
      };
      this.Ga = function () {
        E[this.V >> 2] = 0;
      };
      this.ka = function () {
        D[(this.V + 12) >> 0] = 0;
      };
      this.Ha = function () {
        D[(this.V + 13) >> 0] = 0;
      };
      this.L = function (b, c) {
        this.ja();
        this.Ia(b);
        this.Fa(c);
        this.Ga();
        this.ka();
        this.Ha();
      };
      this.ja = function () {
        F[(this.V + 16) >> 2] = 0;
      };
    }
    var Ea = 0,
      Fa = (a, b) => {
        for (var c = 0, d = a.length - 1; 0 <= d; d--) {
          var e = a[d];
          "." === e ? a.splice(d, 1) : ".." === e ? (a.splice(d, 1), c++) : c && (a.splice(d, 1), c--);
        }
        if (b) for (; c; c--) a.unshift("..");
        return a;
      },
      L = (a) => {
        var b = "/" === a.charAt(0),
          c = "/" === a.substr(-1);
        (a = Fa(
          a.split("/").filter((d) => !!d),
          !b,
        ).join("/")) ||
          b ||
          (a = ".");
        a && c && (a += "/");
        return (b ? "/" : "") + a;
      },
      Ga = (a) => {
        var b = /^(\/?|)([\s\S]*?)((?:\.{1,2}|[^\/]+?|)(\.[^.\/]*|))(?:[\/]*)$/.exec(a).slice(1);
        a = b[0];
        b = b[1];
        if (!a && !b) return ".";
        b && (b = b.substr(0, b.length - 1));
        return a + b;
      },
      Ha = (a) => {
        if ("/" === a) return "/";
        a = L(a);
        a = a.replace(/\/$/, "");
        var b = a.lastIndexOf("/");
        return -1 === b ? a : a.substr(b + 1);
      };
    function Ia() {
      if ("object" == typeof crypto && "function" == typeof crypto.getRandomValues) {
        var a = new Uint8Array(1);
        return () => {
          crypto.getRandomValues(a);
          return a[0];
        };
      }
      return () => y("randomDevice");
    }
    function Ja() {
      for (var a = "", b = !1, c = arguments.length - 1; -1 <= c && !b; c--) {
        b = 0 <= c ? arguments[c] : "/";
        if ("string" != typeof b) throw new TypeError("Arguments to path.resolve must be strings");
        if (!b) return "";
        a = b + "/" + a;
        b = "/" === b.charAt(0);
      }
      a = Fa(
        a.split("/").filter((d) => !!d),
        !b,
      ).join("/");
      return (b ? "/" : "") + a || ".";
    }
    function Ka(a, b) {
      var c = Array(ma(a) + 1);
      a = la(a, c, 0, c.length);
      b && (c.length = a);
      return c;
    }
    var La = [];
    function Ma(a, b) {
      La[a] = { input: [], K: [], $: b };
      Na(a, Oa);
    }
    var Oa = {
        open: function (a) {
          var b = La[a.node.ea];
          if (!b) throw new M(43);
          a.J = b;
          a.seekable = !1;
        },
        close: function (a) {
          a.J.$.flush(a.J);
        },
        flush: function (a) {
          a.J.$.flush(a.J);
        },
        read: function (a, b, c, d) {
          if (!a.J || !a.J.$.wa) throw new M(60);
          for (var e = 0, h = 0; h < d; h++) {
            try {
              var k = a.J.$.wa(a.J);
            } catch (n) {
              throw new M(29);
            }
            if (void 0 === k && 0 === e) throw new M(6);
            if (null === k || void 0 === k) break;
            e++;
            b[c + h] = k;
          }
          e && (a.node.timestamp = Date.now());
          return e;
        },
        write: function (a, b, c, d) {
          if (!a.J || !a.J.$.pa) throw new M(60);
          try {
            for (var e = 0; e < d; e++) a.J.$.pa(a.J, b[c + e]);
          } catch (h) {
            throw new M(29);
          }
          d && (a.node.timestamp = Date.now());
          return e;
        },
      },
      Pa = {
        wa: function (a) {
          if (!a.input.length) {
            var b = null;
            "undefined" != typeof window && "function" == typeof window.prompt
              ? ((b = window.prompt("Input: ")), null !== b && (b += "\n"))
              : "function" == typeof readline && ((b = readline()), null !== b && (b += "\n"));
            if (!b) return null;
            a.input = Ka(b, !0);
          }
          return a.input.shift();
        },
        pa: function (a, b) {
          null === b || 10 === b ? (ha(z(a.K, 0)), (a.K = [])) : 0 != b && a.K.push(b);
        },
        flush: function (a) {
          a.K && 0 < a.K.length && (ha(z(a.K, 0)), (a.K = []));
        },
      },
      Qa = {
        pa: function (a, b) {
          null === b || 10 === b ? (l(z(a.K, 0)), (a.K = [])) : 0 != b && a.K.push(b);
        },
        flush: function (a) {
          a.K && 0 < a.K.length && (l(z(a.K, 0)), (a.K = []));
        },
      },
      N = {
        N: null,
        P: function () {
          return N.createNode(null, "/", 16895, 0);
        },
        createNode: function (a, b, c, d) {
          if (24576 === (c & 61440) || 4096 === (c & 61440)) throw new M(63);
          N.N ||
            (N.N = {
              dir: { node: { T: N.G.T, M: N.G.M, aa: N.G.aa, da: N.G.da, Da: N.G.Da, ia: N.G.ia, Ea: N.G.Ea, Ca: N.G.Ca, fa: N.G.fa }, stream: { U: N.H.U } },
              file: { node: { T: N.G.T, M: N.G.M }, stream: { U: N.H.U, read: N.H.read, write: N.H.write, sa: N.H.sa, oa: N.H.oa, Aa: N.H.Aa } },
              link: { node: { T: N.G.T, M: N.G.M, ca: N.G.ca }, stream: {} },
              ta: { node: { T: N.G.T, M: N.G.M }, stream: Ra },
            });
          c = Sa(a, b, c, d);
          16384 === (c.mode & 61440)
            ? ((c.G = N.N.dir.node), (c.H = N.N.dir.stream), (c.F = {}))
            : 32768 === (c.mode & 61440)
              ? ((c.G = N.N.file.node), (c.H = N.N.file.stream), (c.I = 0), (c.F = null))
              : 40960 === (c.mode & 61440)
                ? ((c.G = N.N.link.node), (c.H = N.N.link.stream))
                : 8192 === (c.mode & 61440) && ((c.G = N.N.ta.node), (c.H = N.N.ta.stream));
          c.timestamp = Date.now();
          a && ((a.F[b] = c), (a.timestamp = c.timestamp));
          return c;
        },
        ab: function (a) {
          return a.F ? (a.F.subarray ? a.F.subarray(0, a.I) : new Uint8Array(a.F)) : new Uint8Array(0);
        },
        ua: function (a, b) {
          var c = a.F ? a.F.length : 0;
          c >= b || ((b = Math.max(b, (c * (1048576 > c ? 2 : 1.125)) >>> 0)), 0 != c && (b = Math.max(b, 256)), (c = a.F), (a.F = new Uint8Array(b)), 0 < a.I && a.F.set(c.subarray(0, a.I), 0));
        },
        Oa: function (a, b) {
          if (a.I != b)
            if (0 == b) ((a.F = null), (a.I = 0));
            else {
              var c = a.F;
              a.F = new Uint8Array(b);
              c && a.F.set(c.subarray(0, Math.min(b, a.I)));
              a.I = b;
            }
        },
        G: {
          T: function (a) {
            var b = {};
            b.Za = 8192 === (a.mode & 61440) ? a.id : 1;
            b.eb = a.id;
            b.mode = a.mode;
            b.gb = 1;
            b.uid = 0;
            b.bb = 0;
            b.ea = a.ea;
            16384 === (a.mode & 61440) ? (b.size = 4096) : 32768 === (a.mode & 61440) ? (b.size = a.I) : 40960 === (a.mode & 61440) ? (b.size = a.link.length) : (b.size = 0);
            b.Wa = new Date(a.timestamp);
            b.fb = new Date(a.timestamp);
            b.Ya = new Date(a.timestamp);
            b.Ja = 4096;
            b.Xa = Math.ceil(b.size / b.Ja);
            return b;
          },
          M: function (a, b) {
            void 0 !== b.mode && (a.mode = b.mode);
            void 0 !== b.timestamp && (a.timestamp = b.timestamp);
            void 0 !== b.size && N.Oa(a, b.size);
          },
          aa: function () {
            throw Ta[44];
          },
          da: function (a, b, c, d) {
            return N.createNode(a, b, c, d);
          },
          Da: function (a, b, c) {
            if (16384 === (a.mode & 61440)) {
              try {
                var d = O(b, c);
              } catch (h) {}
              if (d) for (var e in d.F) throw new M(55);
            }
            delete a.parent.F[a.name];
            a.parent.timestamp = Date.now();
            a.name = c;
            b.F[c] = a;
            b.timestamp = a.parent.timestamp;
            a.parent = b;
          },
          ia: function (a, b) {
            delete a.F[b];
            a.timestamp = Date.now();
          },
          Ea: function (a, b) {
            var c = O(a, b),
              d;
            for (d in c.F) throw new M(55);
            delete a.F[b];
            a.timestamp = Date.now();
          },
          Ca: function (a) {
            var b = [".", ".."],
              c;
            for (c in a.F) a.F.hasOwnProperty(c) && b.push(c);
            return b;
          },
          fa: function (a, b, c) {
            a = N.createNode(a, b, 41471, 0);
            a.link = c;
            return a;
          },
          ca: function (a) {
            if (40960 !== (a.mode & 61440)) throw new M(28);
            return a.link;
          },
        },
        H: {
          read: function (a, b, c, d, e) {
            var h = a.node.F;
            if (e >= a.node.I) return 0;
            a = Math.min(a.node.I - e, d);
            if (8 < a && h.subarray) b.set(h.subarray(e, e + a), c);
            else for (d = 0; d < a; d++) b[c + d] = h[e + d];
            return a;
          },
          write: function (a, b, c, d, e, h) {
            b.buffer === D.buffer && (h = !1);
            if (!d) return 0;
            a = a.node;
            a.timestamp = Date.now();
            if (b.subarray && (!a.F || a.F.subarray)) {
              if (h) return ((a.F = b.subarray(c, c + d)), (a.I = d));
              if (0 === a.I && 0 === e) return ((a.F = b.slice(c, c + d)), (a.I = d));
              if (e + d <= a.I) return (a.F.set(b.subarray(c, c + d), e), d);
            }
            N.ua(a, e + d);
            if (a.F.subarray && b.subarray) a.F.set(b.subarray(c, c + d), e);
            else for (h = 0; h < d; h++) a.F[e + h] = b[c + h];
            a.I = Math.max(a.I, e + d);
            return d;
          },
          U: function (a, b, c) {
            1 === c ? (b += a.position) : 2 === c && 32768 === (a.node.mode & 61440) && (b += a.node.I);
            if (0 > b) throw new M(28);
            return b;
          },
          sa: function (a, b, c) {
            N.ua(a.node, b + c);
            a.node.I = Math.max(a.node.I, b + c);
          },
          oa: function (a, b, c, d, e) {
            if (32768 !== (a.node.mode & 61440)) throw new M(43);
            a = a.node.F;
            if (e & 2 || a.buffer !== na) {
              if (0 < c || c + b < a.length) a.subarray ? (a = a.subarray(c, c + b)) : (a = Array.prototype.slice.call(a, c, c + b));
              c = !0;
              y();
              b = void 0;
              if (!b) throw new M(48);
              D.set(a, b);
            } else ((c = !1), (b = a.byteOffset));
            return { V: b, Va: c };
          },
          Aa: function (a, b, c, d, e) {
            if (32768 !== (a.node.mode & 61440)) throw new M(43);
            if (e & 2) return 0;
            N.H.write(a, b, 0, d, c, !1);
            return 0;
          },
        },
      };
    function Ua(a, b, c) {
      var d = "al " + a;
      fa(
        a,
        (e) => {
          e || y('Loading data file "' + a + '" failed (no arrayBuffer).');
          b(new Uint8Array(e));
          d && wa(d);
        },
        () => {
          if (c) c();
          else throw 'Loading data file "' + a + '" failed.';
        },
      );
      d && va(d);
    }
    var Va = null,
      Wa = {},
      P = [],
      Xa = 1,
      Q = null,
      Ya = !0,
      M = null,
      Ta = {},
      R = (a, b = {}) => {
        a = Ja("/", a);
        if (!a) return { path: "", node: null };
        b = Object.assign({ va: !0, qa: 0 }, b);
        if (8 < b.qa) throw new M(32);
        a = Fa(
          a.split("/").filter((k) => !!k),
          !1,
        );
        for (var c = Va, d = "/", e = 0; e < a.length; e++) {
          var h = e === a.length - 1;
          if (h && b.parent) break;
          c = O(c, a[e]);
          d = L(d + "/" + a[e]);
          c.ba && (!h || (h && b.va)) && (c = c.ba.root);
          if (!h || b.ma) for (h = 0; 40960 === (c.mode & 61440); ) if (((c = Za(d)), (d = Ja(Ga(d), c)), (c = R(d, { qa: b.qa + 1 }).node), 40 < h++)) throw new M(32);
        }
        return { path: d, node: c };
      },
      S = (a) => {
        for (var b; ; ) {
          if (a === a.parent) return ((a = a.P.za), b ? ("/" !== a[a.length - 1] ? a + "/" + b : a + b) : a);
          b = b ? a.name + "/" + b : a.name;
          a = a.parent;
        }
      },
      $a = (a, b) => {
        for (var c = 0, d = 0; d < b.length; d++) c = ((c << 5) - c + b.charCodeAt(d)) | 0;
        return ((a + c) >>> 0) % Q.length;
      },
      O = (a, b) => {
        var c;
        if ((c = (c = ab(a, "x")) ? c : a.G.aa ? 0 : 2)) throw new M(c, a);
        for (c = Q[$a(a.id, b)]; c; c = c.Z) {
          var d = c.name;
          if (c.parent.id === a.id && d === b) return c;
        }
        return a.G.aa(a, b);
      },
      Sa = (a, b, c, d) => {
        a = new bb(a, b, c, d);
        b = $a(a.parent.id, a.name);
        a.Z = Q[b];
        return (Q[b] = a);
      },
      cb = { r: 0, "r+": 2, w: 577, "w+": 578, a: 1089, "a+": 1090 },
      db = (a) => {
        var b = ["r", "w", "rw"][a & 3];
        a & 512 && (b += "w");
        return b;
      },
      ab = (a, b) => {
        if (Ya) return 0;
        if (!b.includes("r") || a.mode & 292) {
          if ((b.includes("w") && !(a.mode & 146)) || (b.includes("x") && !(a.mode & 73))) return 2;
        } else return 2;
        return 0;
      },
      eb = (a, b) => {
        try {
          return (O(a, b), 20);
        } catch (c) {}
        return ab(a, "wx");
      },
      fb = (a = 0) => {
        for (; 4096 >= a; a++) if (!P[a]) return a;
        throw new M(33);
      },
      hb = (a, b) => {
        gb ||
          ((gb = function () {
            this.L = {};
          }),
          (gb.prototype = {}),
          Object.defineProperties(gb.prototype, {
            object: {
              get: function () {
                return this.node;
              },
              set: function (c) {
                this.node = c;
              },
            },
            flags: {
              get: function () {
                return this.L.flags;
              },
              set: function (c) {
                this.L.flags = c;
              },
            },
            position: {
              get: function () {
                return this.L.position;
              },
              set: function (c) {
                this.L.position = c;
              },
            },
          }));
        a = Object.assign(new gb(), a);
        b = fb(b);
        a.S = b;
        return (P[b] = a);
      },
      Ra = {
        open: (a) => {
          a.H = Wa[a.node.ea].H;
          a.H.open && a.H.open(a);
        },
        U: () => {
          throw new M(70);
        },
      },
      Na = (a, b) => {
        Wa[a] = { H: b };
      },
      ib = (a, b) => {
        var c = "/" === b,
          d = !b;
        if (c && Va) throw new M(10);
        if (!c && !d) {
          var e = R(b, { va: !1 });
          b = e.path;
          e = e.node;
          if (e.ba) throw new M(10);
          if (16384 !== (e.mode & 61440)) throw new M(54);
        }
        b = { type: a, hb: {}, za: b, Na: [] };
        a = a.P(b);
        a.P = b;
        b.root = a;
        c ? (Va = a) : e && ((e.ba = b), e.P && e.P.Na.push(b));
      },
      T = (a, b, c) => {
        var d = R(a, { parent: !0 }).node;
        a = Ha(a);
        if (!a || "." === a || ".." === a) throw new M(28);
        var e = eb(d, a);
        if (e) throw new M(e);
        if (!d.G.da) throw new M(63);
        return d.G.da(d, a, b, c);
      },
      jb = (a, b, c) => {
        "undefined" == typeof c && ((c = b), (b = 438));
        return T(a, b | 8192, c);
      },
      kb = (a, b) => {
        if (!Ja(a)) throw new M(44);
        var c = R(b, { parent: !0 }).node;
        if (!c) throw new M(44);
        b = Ha(b);
        var d = eb(c, b);
        if (d) throw new M(d);
        if (!c.G.fa) throw new M(63);
        c.G.fa(c, b, a);
      },
      lb = (a) => {
        var b = R(a, { parent: !0 }).node;
        if (!b) throw new M(44);
        var c = Ha(a);
        a = O(b, c);
        a: {
          try {
            var d = O(b, c);
          } catch (h) {
            d = h.R;
            break a;
          }
          var e = ab(b, "wx");
          d = e ? e : 16384 === (d.mode & 61440) ? 31 : 0;
        }
        if (d) throw new M(d);
        if (!b.G.ia) throw new M(63);
        if (a.ba) throw new M(10);
        b.G.ia(b, c);
        b = $a(a.parent.id, a.name);
        if (Q[b] === a) Q[b] = a.Z;
        else
          for (b = Q[b]; b; ) {
            if (b.Z === a) {
              b.Z = a.Z;
              break;
            }
            b = b.Z;
          }
      },
      Za = (a) => {
        a = R(a).node;
        if (!a) throw new M(44);
        if (!a.G.ca) throw new M(28);
        return Ja(S(a.parent), a.G.ca(a));
      },
      mb = (a, b) => {
        a = "string" == typeof a ? R(a, { ma: !0 }).node : a;
        if (!a.G.M) throw new M(63);
        a.G.M(a, { mode: (b & 4095) | (a.mode & -4096), timestamp: Date.now() });
      },
      ob = (a, b, c) => {
        if ("" === a) throw new M(44);
        if ("string" == typeof b) {
          var d = cb[b];
          if ("undefined" == typeof d) throw Error("Unknown file open mode: " + b);
          b = d;
        }
        c = b & 64 ? (("undefined" == typeof c ? 438 : c) & 4095) | 32768 : 0;
        if ("object" == typeof a) var e = a;
        else {
          a = L(a);
          try {
            e = R(a, { ma: !(b & 131072) }).node;
          } catch (h) {}
        }
        d = !1;
        if (b & 64)
          if (e) {
            if (b & 128) throw new M(20);
          } else ((e = T(a, c, 0)), (d = !0));
        if (!e) throw new M(44);
        8192 === (e.mode & 61440) && (b &= -513);
        if (b & 65536 && 16384 !== (e.mode & 61440)) throw new M(54);
        if (!d && (c = e ? (40960 === (e.mode & 61440) ? 32 : 16384 === (e.mode & 61440) && ("r" !== db(b) || b & 512) ? 31 : ab(e, db(b))) : 44)) throw new M(c);
        if (b & 512 && !d) {
          c = e;
          c = "string" == typeof c ? R(c, { ma: !0 }).node : c;
          if (!c.G.M) throw new M(63);
          if (16384 === (c.mode & 61440)) throw new M(31);
          if (32768 !== (c.mode & 61440)) throw new M(28);
          if ((d = ab(c, "w"))) throw new M(d);
          c.G.M(c, { size: 0, timestamp: Date.now() });
        }
        b &= -131713;
        e = hb({ node: e, path: S(e), flags: b, seekable: !0, position: 0, H: e.H, Ua: [], error: !1 });
        e.H.open && e.H.open(e);
        !f.logReadFiles || b & 1 || (nb || (nb = {}), a in nb || (nb[a] = 1));
        return e;
      },
      pb = (a) => {
        if (null === a.S) throw new M(8);
        a.na && (a.na = null);
        try {
          a.H.close && a.H.close(a);
        } catch (b) {
          throw b;
        } finally {
          P[a.S] = null;
        }
        a.S = null;
      },
      qb = (a, b, c) => {
        if (null === a.S) throw new M(8);
        if (!a.seekable || !a.H.U) throw new M(70);
        if (0 != c && 1 != c && 2 != c) throw new M(28);
        a.position = a.H.U(a, b, c);
        a.Ua = [];
      },
      rb = (a, b, c, d, e, h) => {
        if (0 > d || 0 > e) throw new M(28);
        if (null === a.S) throw new M(8);
        if (0 === (a.flags & 2097155)) throw new M(8);
        if (16384 === (a.node.mode & 61440)) throw new M(31);
        if (!a.H.write) throw new M(28);
        a.seekable && a.flags & 1024 && qb(a, 0, 2);
        var k = "undefined" != typeof e;
        if (!k) e = a.position;
        else if (!a.seekable) throw new M(70);
        b = a.H.write(a, b, c, d, e, h);
        k || (a.position += b);
        return b;
      },
      sb = () => {
        M ||
          ((M = function (a, b) {
            this.node = b;
            this.Pa = function (c) {
              this.R = c;
            };
            this.Pa(a);
            this.message = "FS error";
          }),
          (M.prototype = Error()),
          (M.prototype.constructor = M),
          [44].forEach((a) => {
            Ta[a] = new M(a);
            Ta[a].stack = "<generic error, no stack>";
          }));
      },
      tb,
      ub = (a, b) => {
        var c = 0;
        a && (c |= 365);
        b && (c |= 146);
        return c;
      },
      vb = (a, b) => {
        a = "string" == typeof a ? a : S(a);
        for (b = b.split("/").reverse(); b.length; ) {
          var c = b.pop();
          if (c) {
            var d = L(a + "/" + c);
            try {
              T(d, 16895, 0);
            } catch (e) {}
            a = d;
          }
        }
        return d;
      },
      wb = (a, b, c, d) => {
        a = L(("string" == typeof a ? a : S(a)) + "/" + b);
        c = ub(c, d);
        return T(a, ((void 0 !== c ? c : 438) & 4095) | 32768, 0);
      },
      xb = (a, b, c, d, e, h) => {
        var k = b;
        a && ((a = "string" == typeof a ? a : S(a)), (k = b ? L(a + "/" + b) : a));
        a = ub(d, e);
        k = T(k, ((void 0 !== a ? a : 438) & 4095) | 32768, 0);
        if (c) {
          if ("string" == typeof c) {
            b = Array(c.length);
            d = 0;
            for (e = c.length; d < e; ++d) b[d] = c.charCodeAt(d);
            c = b;
          }
          mb(k, a | 146);
          b = ob(k, 577);
          rb(b, c, 0, c.length, 0, h);
          pb(b);
          mb(k, a);
        }
        return k;
      },
      V = (a, b, c, d) => {
        a = L(("string" == typeof a ? a : S(a)) + "/" + b);
        b = ub(!!c, !!d);
        V.ya || (V.ya = 64);
        var e = (V.ya++ << 8) | 0;
        Na(e, {
          open: (h) => {
            h.seekable = !1;
          },
          close: () => {
            d && d.buffer && d.buffer.length && d(10);
          },
          read: (h, k, n, m) => {
            for (var p = 0, r = 0; r < m; r++) {
              try {
                var q = c();
              } catch (t) {
                throw new M(29);
              }
              if (void 0 === q && 0 === p) throw new M(6);
              if (null === q || void 0 === q) break;
              p++;
              k[n + r] = q;
            }
            p && (h.node.timestamp = Date.now());
            return p;
          },
          write: (h, k, n, m) => {
            for (var p = 0; p < m; p++)
              try {
                d(k[n + p]);
              } catch (r) {
                throw new M(29);
              }
            m && (h.node.timestamp = Date.now());
            return p;
          },
        });
        return jb(a, b, e);
      },
      yb = (a) => {
        if (!(a.xa || a.Ma || a.link || a.F)) {
          if ("undefined" != typeof XMLHttpRequest)
            throw Error(
              "Lazy loading should have been performed (contents set) in createLazyFile, but it was not. Lazy loading only works in web workers. Use --embed-file or --preload-file in emcc on the main thread.",
            );
          if (ea)
            try {
              ((a.F = Ka(ea(a.url), !0)), (a.I = a.F.length));
            } catch (b) {
              throw new M(29);
            }
          else throw Error("Cannot load without read() or XMLHttpRequest.");
        }
      },
      zb = (a, b, c, d, e) => {
        if ("undefined" != typeof XMLHttpRequest) throw "Cannot do synchronous binary XHRs outside webworkers in modern browsers. Use --embed-file or --preload-file in emcc";
        c = { xa: !1, url: c };
        var h = wb(a, b, d, e);
        c.F ? (h.F = c.F) : c.url && ((h.F = null), (h.url = c.url));
        Object.defineProperties(h, {
          I: {
            get: function () {
              return this.F.length;
            },
          },
        });
        var k = {};
        Object.keys(h.H).forEach((n) => {
          var m = h.H[n];
          k[n] = function () {
            yb(h);
            return m.apply(null, arguments);
          };
        });
        k.read = (n, m, p, r, q) => {
          yb(h);
          n = n.node.F;
          if (q >= n.length) m = 0;
          else {
            r = Math.min(n.length - q, r);
            if (n.slice) for (var t = 0; t < r; t++) m[p + t] = n[q + t];
            else for (t = 0; t < r; t++) m[p + t] = n.get(q + t);
            m = r;
          }
          return m;
        };
        k.oa = () => {
          yb(h);
          y();
          throw new M(48);
        };
        h.H = k;
        return h;
      },
      Bb = (a, b, c, d, e, h, k, n, m, p) => {
        function r(g) {
          function v(x) {
            p && p();
            n || xb(a, b, x, d, e, m);
            h && h();
            wa(t);
          }
          Ab.cb(g, q, v, () => {
            k && k();
            wa(t);
          }) || v(g);
        }
        var q = b ? Ja(L(a + "/" + b)) : a,
          t = "cp " + q;
        va(t);
        "string" == typeof c ? Ua(c, (g) => r(g), k) : r(c);
      },
      W = {},
      gb,
      nb,
      Cb = void 0;
    function X() {
      Cb += 4;
      return E[(Cb - 4) >> 2];
    }
    function Y(a) {
      a = P[a];
      if (!a) throw new M(8);
      return a;
    }
    function Db(a) {
      var b = ma(a) + 1,
        c = Eb(b);
      c && la(a, D, c, b);
      return c;
    }
    function Fb(a, b, c) {
      function d(m) {
        return (m = m.toTimeString().match(/\(([A-Za-z ]+)\)$/)) ? m[1] : "GMT";
      }
      var e = new Date().getFullYear(),
        h = new Date(e, 0, 1),
        k = new Date(e, 6, 1);
      e = h.getTimezoneOffset();
      var n = k.getTimezoneOffset();
      E[a >> 2] = 60 * Math.max(e, n);
      E[b >> 2] = Number(e != n);
      a = d(h);
      b = d(k);
      a = Db(a);
      b = Db(b);
      n < e ? ((F[c >> 2] = a), (F[(c + 4) >> 2] = b)) : ((F[c >> 2] = b), (F[(c + 4) >> 2] = a));
    }
    function Gb(a, b, c) {
      Gb.Ka || ((Gb.Ka = !0), Fb(a, b, c));
    }
    var Hb = {};
    function Ib() {
      if (!Jb) {
        var a = {
            USER: "web_user",
            LOGNAME: "web_user",
            PATH: "/",
            PWD: "/",
            HOME: "/home/web_user",
            LANG: (("object" == typeof navigator && navigator.languages && navigator.languages[0]) || "C").replace("-", "_") + ".UTF-8",
            _: da || "./this.program",
          },
          b;
        for (b in Hb) void 0 === Hb[b] ? delete a[b] : (a[b] = Hb[b]);
        var c = [];
        for (b in a) c.push(b + "=" + a[b]);
        Jb = c;
      }
      return Jb;
    }
    var Jb;
    function Kb(a) {
      return 0 === a % 4 && (0 !== a % 100 || 0 === a % 400);
    }
    var Lb = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
      Mb = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    function Nb(a, b, c, d) {
      function e(g, v, x) {
        for (g = "number" == typeof g ? g.toString() : g || ""; g.length < v; ) g = x[0] + g;
        return g;
      }
      function h(g, v) {
        return e(g, v, "0");
      }
      function k(g, v) {
        function x(U) {
          return 0 > U ? -1 : 0 < U ? 1 : 0;
        }
        var A;
        0 === (A = x(g.getFullYear() - v.getFullYear())) && 0 === (A = x(g.getMonth() - v.getMonth())) && (A = x(g.getDate() - v.getDate()));
        return A;
      }
      function n(g) {
        switch (g.getDay()) {
          case 0:
            return new Date(g.getFullYear() - 1, 11, 29);
          case 1:
            return g;
          case 2:
            return new Date(g.getFullYear(), 0, 3);
          case 3:
            return new Date(g.getFullYear(), 0, 2);
          case 4:
            return new Date(g.getFullYear(), 0, 1);
          case 5:
            return new Date(g.getFullYear() - 1, 11, 31);
          case 6:
            return new Date(g.getFullYear() - 1, 11, 30);
        }
      }
      function m(g) {
        var v = g.W;
        for (g = new Date(new Date(g.X + 1900, 0, 1).getTime()); 0 < v; ) {
          var x = g.getMonth(),
            A = (Kb(g.getFullYear()) ? Lb : Mb)[x];
          if (v > A - g.getDate()) ((v -= A - g.getDate() + 1), g.setDate(1), 11 > x ? g.setMonth(x + 1) : (g.setMonth(0), g.setFullYear(g.getFullYear() + 1)));
          else {
            g.setDate(g.getDate() + v);
            break;
          }
        }
        x = new Date(g.getFullYear() + 1, 0, 4);
        v = n(new Date(g.getFullYear(), 0, 4));
        x = n(x);
        return 0 >= k(v, g) ? (0 >= k(x, g) ? g.getFullYear() + 1 : g.getFullYear()) : g.getFullYear() - 1;
      }
      var p = E[(d + 40) >> 2];
      d = {
        Sa: E[d >> 2],
        Ra: E[(d + 4) >> 2],
        ga: E[(d + 8) >> 2],
        ra: E[(d + 12) >> 2],
        ha: E[(d + 16) >> 2],
        X: E[(d + 20) >> 2],
        O: E[(d + 24) >> 2],
        W: E[(d + 28) >> 2],
        ib: E[(d + 32) >> 2],
        Qa: E[(d + 36) >> 2],
        Ta: p ? B(p) : "",
      };
      c = B(c);
      p = {
        "%c": "%a %b %d %H:%M:%S %Y",
        "%D": "%m/%d/%y",
        "%F": "%Y-%m-%d",
        "%h": "%b",
        "%r": "%I:%M:%S %p",
        "%R": "%H:%M",
        "%T": "%H:%M:%S",
        "%x": "%m/%d/%y",
        "%X": "%H:%M:%S",
        "%Ec": "%c",
        "%EC": "%C",
        "%Ex": "%m/%d/%y",
        "%EX": "%H:%M:%S",
        "%Ey": "%y",
        "%EY": "%Y",
        "%Od": "%d",
        "%Oe": "%e",
        "%OH": "%H",
        "%OI": "%I",
        "%Om": "%m",
        "%OM": "%M",
        "%OS": "%S",
        "%Ou": "%u",
        "%OU": "%U",
        "%OV": "%V",
        "%Ow": "%w",
        "%OW": "%W",
        "%Oy": "%y",
      };
      for (var r in p) c = c.replace(new RegExp(r, "g"), p[r]);
      var q = "Sunday Monday Tuesday Wednesday Thursday Friday Saturday".split(" "),
        t = "January February March April May June July August September October November December".split(" ");
      p = {
        "%a": function (g) {
          return q[g.O].substring(0, 3);
        },
        "%A": function (g) {
          return q[g.O];
        },
        "%b": function (g) {
          return t[g.ha].substring(0, 3);
        },
        "%B": function (g) {
          return t[g.ha];
        },
        "%C": function (g) {
          return h(((g.X + 1900) / 100) | 0, 2);
        },
        "%d": function (g) {
          return h(g.ra, 2);
        },
        "%e": function (g) {
          return e(g.ra, 2, " ");
        },
        "%g": function (g) {
          return m(g).toString().substring(2);
        },
        "%G": function (g) {
          return m(g);
        },
        "%H": function (g) {
          return h(g.ga, 2);
        },
        "%I": function (g) {
          g = g.ga;
          0 == g ? (g = 12) : 12 < g && (g -= 12);
          return h(g, 2);
        },
        "%j": function (g) {
          for (var v = 0, x = 0; x <= g.ha - 1; v += (Kb(g.X + 1900) ? Lb : Mb)[x++]);
          return h(g.ra + v, 3);
        },
        "%m": function (g) {
          return h(g.ha + 1, 2);
        },
        "%M": function (g) {
          return h(g.Ra, 2);
        },
        "%n": function () {
          return "\n";
        },
        "%p": function (g) {
          return 0 <= g.ga && 12 > g.ga ? "AM" : "PM";
        },
        "%S": function (g) {
          return h(g.Sa, 2);
        },
        "%t": function () {
          return "\t";
        },
        "%u": function (g) {
          return g.O || 7;
        },
        "%U": function (g) {
          return h(Math.floor((g.W + 7 - g.O) / 7), 2);
        },
        "%V": function (g) {
          var v = Math.floor((g.W + 7 - ((g.O + 6) % 7)) / 7);
          2 >= (g.O + 371 - g.W - 2) % 7 && v++;
          if (v) 53 == v && ((x = (g.O + 371 - g.W) % 7), 4 == x || (3 == x && Kb(g.X)) || (v = 1));
          else {
            v = 52;
            var x = (g.O + 7 - g.W - 1) % 7;
            (4 == x || (5 == x && Kb((g.X % 400) - 1))) && v++;
          }
          return h(v, 2);
        },
        "%w": function (g) {
          return g.O;
        },
        "%W": function (g) {
          return h(Math.floor((g.W + 7 - ((g.O + 6) % 7)) / 7), 2);
        },
        "%y": function (g) {
          return (g.X + 1900).toString().substring(2);
        },
        "%Y": function (g) {
          return g.X + 1900;
        },
        "%z": function (g) {
          g = g.Qa;
          var v = 0 <= g;
          g = Math.abs(g) / 60;
          return (v ? "+" : "-") + String("0000" + ((g / 60) * 100 + (g % 60))).slice(-4);
        },
        "%Z": function (g) {
          return g.Ta;
        },
        "%%": function () {
          return "%";
        },
      };
      c = c.replace(/%%/g, "\x00\x00");
      for (r in p) c.includes(r) && (c = c.replace(new RegExp(r, "g"), p[r](d)));
      c = c.replace(/\0\0/g, "%");
      r = Ka(c, !1);
      if (r.length > b) return 0;
      D.set(r, a);
      return r.length - 1;
    }
    var Z = void 0,
      Ob = [];
    function Pb(a, b, c, d) {
      var e = {
        string: (p) => {
          var r = 0;
          if (null !== p && void 0 !== p && 0 !== p) {
            var q = (p.length << 2) + 1;
            r = Qb(q);
            la(p, C, r, q);
          }
          return r;
        },
        array: (p) => {
          var r = Qb(p.length);
          D.set(p, r);
          return r;
        },
      };
      a = f["_" + a];
      var h = [],
        k = 0;
      if (d)
        for (var n = 0; n < d.length; n++) {
          var m = e[c[n]];
          m ? (0 === k && (k = Rb()), (h[n] = m(d[n]))) : (h[n] = d[n]);
        }
      c = a.apply(null, h);
      return (c = (function (p) {
        0 !== k && Sb(k);
        return "string" === b ? B(p) : "boolean" === b ? !!p : p;
      })(c));
    }
    function bb(a, b, c, d) {
      a || (a = this);
      this.parent = a;
      this.P = a.P;
      this.ba = null;
      this.id = Xa++;
      this.name = b;
      this.mode = c;
      this.G = {};
      this.H = {};
      this.ea = d;
    }
    Object.defineProperties(bb.prototype, {
      read: {
        get: function () {
          return 365 === (this.mode & 365);
        },
        set: function (a) {
          a ? (this.mode |= 365) : (this.mode &= -366);
        },
      },
      write: {
        get: function () {
          return 146 === (this.mode & 146);
        },
        set: function (a) {
          a ? (this.mode |= 146) : (this.mode &= -147);
        },
      },
      Ma: {
        get: function () {
          return 16384 === (this.mode & 61440);
        },
      },
      xa: {
        get: function () {
          return 8192 === (this.mode & 61440);
        },
      },
    });
    sb();
    Q = Array(4096);
    ib(N, "/");
    T("/tmp", 16895, 0);
    T("/home", 16895, 0);
    T("/home/web_user", 16895, 0);
    (() => {
      T("/dev", 16895, 0);
      Na(259, { read: () => 0, write: (b, c, d, e) => e });
      jb("/dev/null", 259);
      Ma(1280, Pa);
      Ma(1536, Qa);
      jb("/dev/tty", 1280);
      jb("/dev/tty1", 1536);
      var a = Ia();
      V("/dev", "random", a);
      V("/dev", "urandom", a);
      T("/dev/shm", 16895, 0);
      T("/dev/shm/tmp", 16895, 0);
    })();
    (() => {
      T("/proc", 16895, 0);
      var a = T("/proc/self", 16895, 0);
      T("/proc/self/fd", 16895, 0);
      ib(
        {
          P: () => {
            var b = Sa(a, "fd", 16895, 73);
            b.G = {
              aa: (c, d) => {
                var e = P[+d];
                if (!e) throw new M(8);
                c = { parent: null, P: { za: "fake" }, G: { ca: () => e.path } };
                return (c.parent = c);
              },
            };
            return b;
          },
        },
        "/proc/self/fd",
      );
    })();
    var Ab;
    f.FS_createPath = vb;
    f.FS_createDataFile = xb;
    f.FS_createPreloadedFile = Bb;
    f.FS_unlink = lb;
    f.FS_createLazyFile = zb;
    f.FS_createDevice = V;
    var Ub = {
      a: function (a) {
        return Eb(a + 24) + 24;
      },
      b: function (a, b, c) {
        new Da(a).L(b, c);
        Ea++;
        throw a;
      },
      f: function (a, b, c) {
        Cb = c;
        try {
          var d = Y(a);
          switch (b) {
            case 0:
              var e = X();
              return 0 > e ? -28 : hb(d, e).S;
            case 1:
            case 2:
              return 0;
            case 3:
              return d.flags;
            case 4:
              return ((e = X()), (d.flags |= e), 0);
            case 5:
              return ((e = X()), (oa[(e + 0) >> 1] = 2), 0);
            case 6:
            case 7:
              return 0;
            case 16:
            case 8:
              return -28;
            case 9:
              return ((E[Tb() >> 2] = 28), -1);
            default:
              return -28;
          }
        } catch (h) {
          if ("undefined" == typeof W || !(h instanceof M)) throw h;
          return -h.R;
        }
      },
      s: function (a, b, c) {
        Cb = c;
        try {
          var d = Y(a);
          switch (b) {
            case 21509:
            case 21505:
              return d.J ? 0 : -59;
            case 21510:
            case 21511:
            case 21512:
            case 21506:
            case 21507:
            case 21508:
              return d.J ? 0 : -59;
            case 21519:
              if (!d.J) return -59;
              var e = X();
              return (E[e >> 2] = 0);
            case 21520:
              return d.J ? -28 : -59;
            case 21531:
              a = e = X();
              if (!d.H.La) throw new M(59);
              return d.H.La(d, b, a);
            case 21523:
              return d.J ? 0 : -59;
            case 21524:
              return d.J ? 0 : -59;
            default:
              return -28;
          }
        } catch (h) {
          if ("undefined" == typeof W || !(h instanceof M)) throw h;
          return -h.R;
        }
      },
      i: function (a, b, c, d) {
        Cb = d;
        try {
          b = B(b);
          var e = b;
          if ("/" === e.charAt(0)) b = e;
          else {
            if (-100 === a) var h = "/";
            else {
              var k = P[a];
              if (!k) throw new M(8);
              h = k.path;
            }
            if (0 == e.length) throw new M(44);
            b = L(h + "/" + e);
          }
          var n = d ? X() : 0;
          return ob(b, c, n).S;
        } catch (m) {
          if ("undefined" == typeof W || !(m instanceof M)) throw m;
          return -m.R;
        }
      },
      l: function () {
        return Date.now();
      },
      k: function () {
        return !0;
      },
      m: function (a) {
        var b = new Date(E[(a + 20) >> 2] + 1900, E[(a + 16) >> 2], E[(a + 12) >> 2], E[(a + 8) >> 2], E[(a + 4) >> 2], E[a >> 2], 0),
          c = E[(a + 32) >> 2],
          d = b.getTimezoneOffset(),
          e = new Date(b.getFullYear(), 0, 1),
          h = new Date(b.getFullYear(), 6, 1).getTimezoneOffset(),
          k = e.getTimezoneOffset(),
          n = Math.min(k, h);
        0 > c ? (E[(a + 32) >> 2] = Number(h != k && n == d)) : 0 < c != (n == d) && ((h = Math.max(k, h)), b.setTime(b.getTime() + 6e4 * ((0 < c ? n : h) - d)));
        E[(a + 24) >> 2] = b.getDay();
        E[(a + 28) >> 2] = ((b.getTime() - e.getTime()) / 864e5) | 0;
        E[a >> 2] = b.getSeconds();
        E[(a + 4) >> 2] = b.getMinutes();
        E[(a + 8) >> 2] = b.getHours();
        E[(a + 12) >> 2] = b.getDate();
        E[(a + 16) >> 2] = b.getMonth();
        return (b.getTime() / 1e3) | 0;
      },
      n: Gb,
      d: function () {
        y("");
      },
      j: () => performance.now(),
      e: function (a) {
        var b = C.length;
        a >>>= 0;
        if (536870912 < a) return !1;
        for (var c = 1; 4 >= c; c *= 2) {
          var d = b * (1 + 0.2 / c);
          d = Math.min(d, a + 100663296);
          var e = Math;
          d = Math.max(a, d);
          e = e.min.call(e, 536870912, d + ((65536 - (d % 65536)) % 65536));
          a: {
            try {
              ia.grow((e - na.byteLength + 65535) >>> 16);
              pa();
              var h = 1;
              break a;
            } catch (k) {}
            h = void 0;
          }
          if (h) return !0;
        }
        return !1;
      },
      q: function (a, b) {
        var c = 0;
        Ib().forEach(function (d, e) {
          var h = b + c;
          e = F[(a + 4 * e) >> 2] = h;
          for (h = 0; h < d.length; ++h) D[e++ >> 0] = d.charCodeAt(h);
          D[e >> 0] = 0;
          c += d.length + 1;
        });
        return 0;
      },
      r: function (a, b) {
        var c = Ib();
        F[a >> 2] = c.length;
        var d = 0;
        c.forEach(function (e) {
          d += e.length + 1;
        });
        F[b >> 2] = d;
        return 0;
      },
      c: function (a) {
        try {
          var b = Y(a);
          pb(b);
          return 0;
        } catch (c) {
          if ("undefined" == typeof W || !(c instanceof M)) throw c;
          return c.R;
        }
      },
      h: function (a, b, c, d) {
        try {
          a: {
            var e = Y(a);
            a = b;
            for (var h = (b = 0); h < c; h++) {
              var k = F[a >> 2],
                n = F[(a + 4) >> 2];
              a += 8;
              var m = e,
                p = k,
                r = n,
                q = void 0,
                t = D;
              if (0 > r || 0 > q) throw new M(28);
              if (null === m.S) throw new M(8);
              if (1 === (m.flags & 2097155)) throw new M(8);
              if (16384 === (m.node.mode & 61440)) throw new M(31);
              if (!m.H.read) throw new M(28);
              var g = "undefined" != typeof q;
              if (!g) q = m.position;
              else if (!m.seekable) throw new M(70);
              var v = m.H.read(m, t, p, r, q);
              g || (m.position += v);
              var x = v;
              if (0 > x) {
                var A = -1;
                break a;
              }
              b += x;
              if (x < n) break;
            }
            A = b;
          }
          E[d >> 2] = A;
          return 0;
        } catch (U) {
          if ("undefined" == typeof W || !(U instanceof M)) throw U;
          return U.R;
        }
      },
      o: function (a, b, c, d, e) {
        try {
          b = (c + 2097152) >>> 0 < 4194305 - !!b ? (b >>> 0) + 4294967296 * c : NaN;
          if (isNaN(b)) return 61;
          var h = Y(a);
          qb(h, b, d);
          Ba = [
            h.position >>> 0,
            ((K = h.position), 1 <= +Math.abs(K) ? (0 < K ? (Math.min(+Math.floor(K / 4294967296), 4294967295) | 0) >>> 0 : ~~+Math.ceil((K - +(~~K >>> 0)) / 4294967296) >>> 0) : 0),
          ];
          E[e >> 2] = Ba[0];
          E[(e + 4) >> 2] = Ba[1];
          h.na && 0 === b && 0 === d && (h.na = null);
          return 0;
        } catch (k) {
          if ("undefined" == typeof W || !(k instanceof M)) throw k;
          return k.R;
        }
      },
      g: function (a, b, c, d) {
        try {
          a: {
            var e = Y(a);
            a = b;
            for (var h = (b = 0); h < c; h++) {
              var k = F[a >> 2],
                n = F[(a + 4) >> 2];
              a += 8;
              var m = rb(e, D, k, n);
              if (0 > m) {
                var p = -1;
                break a;
              }
              b += m;
            }
            p = b;
          }
          F[d >> 2] = p;
          return 0;
        } catch (r) {
          if ("undefined" == typeof W || !(r instanceof M)) throw r;
          return r.R;
        }
      },
      p: function (a, b, c, d) {
        return Nb(a, b, c, d);
      },
    };
    (function () {
      function a(e) {
        f.asm = e.exports;
        ia = f.asm.t;
        pa();
        G = f.asm.x;
        ra.unshift(f.asm.u);
        wa("wasm-instantiate");
      }
      function b(e) {
        a(e.instance);
      }
      function c(e) {
        return Aa()
          .then(function (h) {
            return WebAssembly.instantiate(h, d);
          })
          .then(function (h) {
            return h;
          })
          .then(e, function (h) {
            l("failed to asynchronously prepare wasm: " + h);
            y(h);
          });
      }
      var d = { a: Ub };
      va("wasm-instantiate");
      if (f.instantiateWasm)
        try {
          return f.instantiateWasm(d, a);
        } catch (e) {
          return (l("Module.instantiateWasm callback failed with error: " + e), !1);
        }
      (function () {
        return w || "function" != typeof WebAssembly.instantiateStreaming || xa() || "function" != typeof fetch
          ? c(b)
          : fetch(J, { credentials: "same-origin" }).then(function (e) {
              return WebAssembly.instantiateStreaming(e, d).then(b, function (h) {
                l("wasm streaming compile failed: " + h);
                l("falling back to ArrayBuffer instantiation");
                return c(b);
              });
            });
      })().catch(ba);
      return {};
    })();
    f.___wasm_call_ctors = function () {
      return (f.___wasm_call_ctors = f.asm.u).apply(null, arguments);
    };
    f._initializeModel = function () {
      return (f._initializeModel = f.asm.v).apply(null, arguments);
    };
    f._processImage = function () {
      return (f._processImage = f.asm.w).apply(null, arguments);
    };
    var Eb = (f._malloc = function () {
      return (Eb = f._malloc = f.asm.y).apply(null, arguments);
    });
    f._free = function () {
      return (f._free = f.asm.z).apply(null, arguments);
    };
    var Tb = (f.___errno_location = function () {
        return (Tb = f.___errno_location = f.asm.A).apply(null, arguments);
      }),
      Rb = (f.stackSave = function () {
        return (Rb = f.stackSave = f.asm.B).apply(null, arguments);
      }),
      Sb = (f.stackRestore = function () {
        return (Sb = f.stackRestore = f.asm.C).apply(null, arguments);
      }),
      Qb = (f.stackAlloc = function () {
        return (Qb = f.stackAlloc = f.asm.D).apply(null, arguments);
      });
    f.___cxa_is_pointer_type = function () {
      return (f.___cxa_is_pointer_type = f.asm.E).apply(null, arguments);
    };
    f.UTF8ToString = B;
    f.addRunDependency = va;
    f.removeRunDependency = wa;
    f.FS_createPath = vb;
    f.FS_createDataFile = xb;
    f.FS_createPreloadedFile = Bb;
    f.FS_createLazyFile = zb;
    f.FS_createDevice = V;
    f.FS_unlink = lb;
    f.ccall = Pb;
    f.cwrap = function (a, b, c, d) {
      c = c || [];
      var e = c.every((h) => "number" === h || "boolean" === h);
      return "string" !== b && e && !d
        ? f["_" + a]
        : function () {
            return Pb(a, b, c, arguments, d);
          };
    };
    f.addFunction = function (a, b) {
      if (!Z) {
        Z = new WeakMap();
        var c = G.length;
        if (Z)
          for (var d = 0; d < 0 + c; d++) {
            var e = G.get(d);
            e && Z.set(e, d);
          }
      }
      if (Z.has(a)) return Z.get(a);
      if (Ob.length) c = Ob.pop();
      else {
        try {
          G.grow(1);
        } catch (n) {
          if (!(n instanceof RangeError)) throw n;
          throw "Unable to grow wasm table. Set ALLOW_TABLE_GROWTH.";
        }
        c = G.length - 1;
      }
      try {
        G.set(c, a);
      } catch (n) {
        if (!(n instanceof TypeError)) throw n;
        if ("function" == typeof WebAssembly.Function) {
          d = WebAssembly.Function;
          e = { i: "i32", j: "i64", f: "f32", d: "f64", p: "i32" };
          for (var h = { parameters: [], results: "v" == b[0] ? [] : [e[b[0]]] }, k = 1; k < b.length; ++k) h.parameters.push(e[b[k]]);
          b = new d(h, a);
        } else {
          d = [1, 96];
          e = b.slice(0, 1);
          b = b.slice(1);
          h = { i: 127, p: 127, j: 126, f: 125, d: 124 };
          k = b.length;
          128 > k ? d.push(k) : d.push((k % 128) | 128, k >> 7);
          for (k = 0; k < b.length; ++k) d.push(h[b[k]]);
          "v" == e ? d.push(0) : d.push(1, h[e]);
          b = [0, 97, 115, 109, 1, 0, 0, 0, 1];
          e = d.length;
          128 > e ? b.push(e) : b.push((e % 128) | 128, e >> 7);
          b.push.apply(b, d);
          b.push(2, 7, 1, 1, 101, 1, 102, 0, 0, 7, 5, 1, 1, 102, 0, 0);
          b = new WebAssembly.Module(new Uint8Array(b));
          b = new WebAssembly.Instance(b, { e: { f: a } }).exports.f;
        }
        G.set(c, b);
      }
      Z.set(a, c);
      return c;
    };
    f.AsciiToString = function (a) {
      for (var b = ""; ; ) {
        var c = C[a++ >> 0];
        if (!c) return b;
        b += String.fromCharCode(c);
      }
    };
    var Vb;
    I = function Wb() {
      Vb || Xb();
      Vb || (I = Wb);
    };
    function Xb() {
      function a() {
        if (!Vb && ((Vb = !0), (f.calledRun = !0), !ja)) {
          f.noFSInit ||
            tb ||
            ((tb = !0),
            sb(),
            (f.stdin = f.stdin),
            (f.stdout = f.stdout),
            (f.stderr = f.stderr),
            f.stdin ? V("/dev", "stdin", f.stdin) : kb("/dev/tty", "/dev/stdin"),
            f.stdout ? V("/dev", "stdout", null, f.stdout) : kb("/dev/tty", "/dev/stdout"),
            f.stderr ? V("/dev", "stderr", null, f.stderr) : kb("/dev/tty1", "/dev/stderr"),
            ob("/dev/stdin", 0),
            ob("/dev/stdout", 1),
            ob("/dev/stderr", 1));
          Ya = !1;
          Ca(ra);
          aa(f);
          if (f.onRuntimeInitialized) f.onRuntimeInitialized();
          if (f.postRun)
            for ("function" == typeof f.postRun && (f.postRun = [f.postRun]); f.postRun.length; ) {
              var b = f.postRun.shift();
              sa.unshift(b);
            }
          Ca(sa);
        }
      }
      if (!(0 < H)) {
        if (f.preRun) for ("function" == typeof f.preRun && (f.preRun = [f.preRun]); f.preRun.length; ) ta();
        Ca(qa);
        0 < H ||
          (f.setStatus
            ? (f.setStatus("Running..."),
              setTimeout(function () {
                setTimeout(function () {
                  f.setStatus("");
                }, 1);
                a();
              }, 1))
            : a());
      }
    }
    if (f.preInit) for ("function" == typeof f.preInit && (f.preInit = [f.preInit]); 0 < f.preInit.length; ) f.preInit.pop()();
    Xb();

    return segCropModule.ready;
  };
})();
if (typeof exports === "object" && typeof module === "object") module.exports = segCropModule;
else if (typeof define === "function" && define["amd"])
  define([], function () {
    return segCropModule;
  });
else if (typeof exports === "object") exports["segCropModule"] = segCropModule;
