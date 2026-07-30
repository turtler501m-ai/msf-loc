var passportModule = (() => {
  var _scriptDir = typeof document !== "undefined" && document.currentScript ? document.currentScript.src : undefined;

  return function (passportModule) {
    passportModule = passportModule || {};

    var f;
    f || (f = typeof passportModule !== "undefined" ? passportModule : {});
    var aa, ba;
    f.ready = new Promise(function (a, b) {
      aa = a;
      ba = b;
    });
    f.ja || (f.ja = 0);
    f.ja++;
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
              q.J ? (f.W[m].loaded = t.loaded) : ((q.J = !0), f.W || (f.W = {}), (f.W[m] = { loaded: t.loaded, total: g }));
              var v = (g = t = 0),
                w;
              for (w in f.W) {
                var z = f.W[w];
                t += z.total;
                g += z.loaded;
                v++;
              }
              t = Math.ceil((t * f.ja) / v);
              f.setStatus && f.setStatus("Downloading data... (" + g + "/" + t + ")");
            } else !f.W && f.setStatus && f.setStatus("Downloading data...");
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
            m.prototype.ia = t;
            t = a.files;
            for (var g = 0; g < t.length; ++g) m.prototype.J[t[g].filename].onload();
            f.removeRunDependency("datafile_Koi_passport.data");
          }
          f.FS_createPath("/", "assets", !0, !0);
          m.prototype = {
            J: {},
            open: function (t, g) {
              this.name = g;
              this.J[g] = this;
              f.addRunDependency("fp " + this.name);
            },
            onload: function () {
              this.ha(this.ia.subarray(this.start, this.end));
            },
            ha: function (t) {
              f.FS_createDataFile(this.name, null, t, !0, !0, !0);
              f.removeRunDependency("fp " + this.name);
              this.J[this.name] = null;
            },
          };
          for (var r = a.files, q = 0; q < r.length; ++q) new m(r[q].start, r[q].end, r[q].audio || 0).open("GET", r[q].filename);
          f.addRunDependency("datafile_Koi_passport.data");
          f.za || (f.za = {});
          f.za["Koi_passport.data"] = { Xa: !1 };
          n ? (p(n), (n = null)) : (k = p);
        }
        "object" === typeof window
          ? window.encodeURIComponent(window.location.pathname.toString().substring(0, window.location.pathname.toString().lastIndexOf("/")) + "/")
          : "undefined" === typeof process && "undefined" !== typeof location && encodeURIComponent(location.pathname.toString().substring(0, location.pathname.toString().lastIndexOf("/")) + "/");
        "function" !== typeof f.locateFilePackage ||
          f.locateFile ||
          ((f.locateFile = f.locateFilePackage), l("warning: you defined Module.locateFilePackage, that has been renamed to Module.locateFile (using your locateFilePackage for now)"));
        var e = f.locateFile ? f.locateFile("Koi_passport.data", "") : "../../detect/passport/Koi_passport.data",
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
      })({
        files: [
          { filename: "/assets/250827_mrz_vgg_480_128_64_2.onnx", start: 0, end: 2145433 },
          { filename: "/assets/yolo_mrz_320.onnx", start: 2145433, end: 3971613 },
        ],
        remote_package_size: 3971613,
      });
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
    var x;
    f.wasmBinary && (x = f.wasmBinary);
    var noExitRuntime = f.noExitRuntime || !0;
    "object" != typeof WebAssembly && y("no native wasm support detected");
    var ia,
      ja = !1,
      ka = "undefined" != typeof TextDecoder ? new TextDecoder("utf8") : void 0;
    function A(a, b, c) {
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
      return a ? A(C, a, b) : "";
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
    var ma, D, C, na, E, F;
    function oa() {
      var a = ia.buffer;
      ma = a;
      f.HEAP8 = D = new Int8Array(a);
      f.HEAP16 = na = new Int16Array(a);
      f.HEAP32 = E = new Int32Array(a);
      f.HEAPU8 = C = new Uint8Array(a);
      f.HEAPU16 = new Uint16Array(a);
      f.HEAPU32 = F = new Uint32Array(a);
      f.HEAPF32 = new Float32Array(a);
      f.HEAPF64 = new Float64Array(a);
    }
    var G,
      pa = [],
      qa = [],
      ra = [];
    function sa() {
      var a = f.preRun.shift();
      pa.unshift(a);
    }
    var H = 0,
      ta = null,
      I = null;
    function ua() {
      H++;
      f.monitorRunDependencies && f.monitorRunDependencies(H);
    }
    function va() {
      H--;
      f.monitorRunDependencies && f.monitorRunDependencies(H);
      if (0 == H && (null !== ta && (clearInterval(ta), (ta = null)), I)) {
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
    function wa() {
      return J.startsWith("data:application/octet-stream;base64,");
    }
    var J;
    J = "../../detect/passport/Koi_passport.wasm";
    if (!wa()) {
      var xa = J;
      J = f.locateFile ? f.locateFile(xa, u) : u + xa;
    }
    function ya() {
      var a = J;
      try {
        if (a == J && x) return new Uint8Array(x);
        throw "both async and sync fetching of the wasm failed";
      } catch (b) {
        y(b);
      }
    }
    function za() {
      return x || "function" != typeof fetch
        ? Promise.resolve().then(function () {
            return ya();
          })
        : fetch(J, { credentials: "same-origin" })
            .then(function (a) {
              if (!a.ok) throw "failed to load wasm binary file at '" + J + "'";
              return a.arrayBuffer();
            })
            .catch(function () {
              return ya();
            });
    }
    var K, Aa;
    function Ba(a) {
      for (; 0 < a.length; ) a.shift()(f);
    }
    function Ca(a) {
      this.T = a - 24;
      this.Ga = function (b) {
        F[(this.T + 4) >> 2] = b;
      };
      this.Da = function (b) {
        F[(this.T + 8) >> 2] = b;
      };
      this.Ea = function () {
        E[this.T >> 2] = 0;
      };
      this.ia = function () {
        D[(this.T + 12) >> 0] = 0;
      };
      this.Fa = function () {
        D[(this.T + 13) >> 0] = 0;
      };
      this.J = function (b, c) {
        this.ha();
        this.Ga(b);
        this.Da(c);
        this.Ea();
        this.ia();
        this.Fa();
      };
      this.ha = function () {
        F[(this.T + 16) >> 2] = 0;
      };
    }
    var Da = 0,
      Ea = (a, b) => {
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
        (a = Ea(
          a.split("/").filter((d) => !!d),
          !b,
        ).join("/")) ||
          b ||
          (a = ".");
        a && c && (a += "/");
        return (b ? "/" : "") + a;
      },
      Fa = (a) => {
        var b = /^(\/?|)([\s\S]*?)((?:\.{1,2}|[^\/]+?|)(\.[^.\/]*|))(?:[\/]*)$/.exec(a).slice(1);
        a = b[0];
        b = b[1];
        if (!a && !b) return ".";
        b && (b = b.substr(0, b.length - 1));
        return a + b;
      },
      Ga = (a) => {
        if ("/" === a) return "/";
        a = L(a);
        a = a.replace(/\/$/, "");
        var b = a.lastIndexOf("/");
        return -1 === b ? a : a.substr(b + 1);
      };
    function Ha() {
      if ("object" == typeof crypto && "function" == typeof crypto.getRandomValues) {
        var a = new Uint8Array(1);
        return () => {
          crypto.getRandomValues(a);
          return a[0];
        };
      }
      return () => y("randomDevice");
    }
    function Ia() {
      for (var a = "", b = !1, c = arguments.length - 1; -1 <= c && !b; c--) {
        b = 0 <= c ? arguments[c] : "/";
        if ("string" != typeof b) throw new TypeError("Arguments to path.resolve must be strings");
        if (!b) return "";
        a = b + "/" + a;
        b = "/" === b.charAt(0);
      }
      a = Ea(
        a.split("/").filter((d) => !!d),
        !b,
      ).join("/");
      return (b ? "/" : "") + a || ".";
    }
    function Ja(a, b) {
      for (var c = 0, d = 0; d < a.length; ++d) {
        var e = a.charCodeAt(d);
        127 >= e ? c++ : 2047 >= e ? (c += 2) : 55296 <= e && 57343 >= e ? ((c += 4), ++d) : (c += 3);
      }
      c = Array(c + 1);
      a = la(a, c, 0, c.length);
      b && (c.length = a);
      return c;
    }
    var Ka = [];
    function La(a, b) {
      Ka[a] = { input: [], I: [], Y: b };
      Ma(a, Na);
    }
    var Na = {
        open: function (a) {
          var b = Ka[a.node.ca];
          if (!b) throw new M(43);
          a.H = b;
          a.seekable = !1;
        },
        close: function (a) {
          a.H.Y.flush(a.H);
        },
        flush: function (a) {
          a.H.Y.flush(a.H);
        },
        read: function (a, b, c, d) {
          if (!a.H || !a.H.Y.ua) throw new M(60);
          for (var e = 0, h = 0; h < d; h++) {
            try {
              var k = a.H.Y.ua(a.H);
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
          if (!a.H || !a.H.Y.na) throw new M(60);
          try {
            for (var e = 0; e < d; e++) a.H.Y.na(a.H, b[c + e]);
          } catch (h) {
            throw new M(29);
          }
          d && (a.node.timestamp = Date.now());
          return e;
        },
      },
      Oa = {
        ua: function (a) {
          if (!a.input.length) {
            var b = null;
            "undefined" != typeof window && "function" == typeof window.prompt
              ? ((b = window.prompt("Input: ")), null !== b && (b += "\n"))
              : "function" == typeof readline && ((b = readline()), null !== b && (b += "\n"));
            if (!b) return null;
            a.input = Ja(b, !0);
          }
          return a.input.shift();
        },
        na: function (a, b) {
          null === b || 10 === b ? (ha(A(a.I, 0)), (a.I = [])) : 0 != b && a.I.push(b);
        },
        flush: function (a) {
          a.I && 0 < a.I.length && (ha(A(a.I, 0)), (a.I = []));
        },
      },
      Pa = {
        na: function (a, b) {
          null === b || 10 === b ? (l(A(a.I, 0)), (a.I = [])) : 0 != b && a.I.push(b);
        },
        flush: function (a) {
          a.I && 0 < a.I.length && (l(A(a.I, 0)), (a.I = []));
        },
      },
      N = {
        L: null,
        N: function () {
          return N.createNode(null, "/", 16895, 0);
        },
        createNode: function (a, b, c, d) {
          if (24576 === (c & 61440) || 4096 === (c & 61440)) throw new M(63);
          N.L ||
            (N.L = {
              dir: { node: { R: N.D.R, K: N.D.K, Z: N.D.Z, ba: N.D.ba, Ba: N.D.Ba, ga: N.D.ga, Ca: N.D.Ca, Aa: N.D.Aa, da: N.D.da }, stream: { S: N.F.S } },
              file: { node: { R: N.D.R, K: N.D.K }, stream: { S: N.F.S, read: N.F.read, write: N.F.write, qa: N.F.qa, ma: N.F.ma, ya: N.F.ya } },
              link: { node: { R: N.D.R, K: N.D.K, aa: N.D.aa }, stream: {} },
              ra: { node: { R: N.D.R, K: N.D.K }, stream: Qa },
            });
          c = Ra(a, b, c, d);
          16384 === (c.mode & 61440)
            ? ((c.D = N.L.dir.node), (c.F = N.L.dir.stream), (c.C = {}))
            : 32768 === (c.mode & 61440)
              ? ((c.D = N.L.file.node), (c.F = N.L.file.stream), (c.G = 0), (c.C = null))
              : 40960 === (c.mode & 61440)
                ? ((c.D = N.L.link.node), (c.F = N.L.link.stream))
                : 8192 === (c.mode & 61440) && ((c.D = N.L.ra.node), (c.F = N.L.ra.stream));
          c.timestamp = Date.now();
          a && ((a.C[b] = c), (a.timestamp = c.timestamp));
          return c;
        },
        Ya: function (a) {
          return a.C ? (a.C.subarray ? a.C.subarray(0, a.G) : new Uint8Array(a.C)) : new Uint8Array(0);
        },
        sa: function (a, b) {
          var c = a.C ? a.C.length : 0;
          c >= b || ((b = Math.max(b, (c * (1048576 > c ? 2 : 1.125)) >>> 0)), 0 != c && (b = Math.max(b, 256)), (c = a.C), (a.C = new Uint8Array(b)), 0 < a.G && a.C.set(c.subarray(0, a.G), 0));
        },
        La: function (a, b) {
          if (a.G != b)
            if (0 == b) ((a.C = null), (a.G = 0));
            else {
              var c = a.C;
              a.C = new Uint8Array(b);
              c && a.C.set(c.subarray(0, Math.min(b, a.G)));
              a.G = b;
            }
        },
        D: {
          R: function (a) {
            var b = {};
            b.Wa = 8192 === (a.mode & 61440) ? a.id : 1;
            b.ab = a.id;
            b.mode = a.mode;
            b.cb = 1;
            b.uid = 0;
            b.Za = 0;
            b.ca = a.ca;
            16384 === (a.mode & 61440) ? (b.size = 4096) : 32768 === (a.mode & 61440) ? (b.size = a.G) : 40960 === (a.mode & 61440) ? (b.size = a.link.length) : (b.size = 0);
            b.Ta = new Date(a.timestamp);
            b.bb = new Date(a.timestamp);
            b.Va = new Date(a.timestamp);
            b.Ha = 4096;
            b.Ua = Math.ceil(b.size / b.Ha);
            return b;
          },
          K: function (a, b) {
            void 0 !== b.mode && (a.mode = b.mode);
            void 0 !== b.timestamp && (a.timestamp = b.timestamp);
            void 0 !== b.size && N.La(a, b.size);
          },
          Z: function () {
            throw Sa[44];
          },
          ba: function (a, b, c, d) {
            return N.createNode(a, b, c, d);
          },
          Ba: function (a, b, c) {
            if (16384 === (a.mode & 61440)) {
              try {
                var d = O(b, c);
              } catch (h) {}
              if (d) for (var e in d.C) throw new M(55);
            }
            delete a.parent.C[a.name];
            a.parent.timestamp = Date.now();
            a.name = c;
            b.C[c] = a;
            b.timestamp = a.parent.timestamp;
            a.parent = b;
          },
          ga: function (a, b) {
            delete a.C[b];
            a.timestamp = Date.now();
          },
          Ca: function (a, b) {
            var c = O(a, b),
              d;
            for (d in c.C) throw new M(55);
            delete a.C[b];
            a.timestamp = Date.now();
          },
          Aa: function (a) {
            var b = [".", ".."],
              c;
            for (c in a.C) a.C.hasOwnProperty(c) && b.push(c);
            return b;
          },
          da: function (a, b, c) {
            a = N.createNode(a, b, 41471, 0);
            a.link = c;
            return a;
          },
          aa: function (a) {
            if (40960 !== (a.mode & 61440)) throw new M(28);
            return a.link;
          },
        },
        F: {
          read: function (a, b, c, d, e) {
            var h = a.node.C;
            if (e >= a.node.G) return 0;
            a = Math.min(a.node.G - e, d);
            if (8 < a && h.subarray) b.set(h.subarray(e, e + a), c);
            else for (d = 0; d < a; d++) b[c + d] = h[e + d];
            return a;
          },
          write: function (a, b, c, d, e, h) {
            b.buffer === D.buffer && (h = !1);
            if (!d) return 0;
            a = a.node;
            a.timestamp = Date.now();
            if (b.subarray && (!a.C || a.C.subarray)) {
              if (h) return ((a.C = b.subarray(c, c + d)), (a.G = d));
              if (0 === a.G && 0 === e) return ((a.C = b.slice(c, c + d)), (a.G = d));
              if (e + d <= a.G) return (a.C.set(b.subarray(c, c + d), e), d);
            }
            N.sa(a, e + d);
            if (a.C.subarray && b.subarray) a.C.set(b.subarray(c, c + d), e);
            else for (h = 0; h < d; h++) a.C[e + h] = b[c + h];
            a.G = Math.max(a.G, e + d);
            return d;
          },
          S: function (a, b, c) {
            1 === c ? (b += a.position) : 2 === c && 32768 === (a.node.mode & 61440) && (b += a.node.G);
            if (0 > b) throw new M(28);
            return b;
          },
          qa: function (a, b, c) {
            N.sa(a.node, b + c);
            a.node.G = Math.max(a.node.G, b + c);
          },
          ma: function (a, b, c, d, e) {
            if (32768 !== (a.node.mode & 61440)) throw new M(43);
            a = a.node.C;
            if (e & 2 || a.buffer !== ma) {
              if (0 < c || c + b < a.length) a.subarray ? (a = a.subarray(c, c + b)) : (a = Array.prototype.slice.call(a, c, c + b));
              c = !0;
              y();
              b = void 0;
              if (!b) throw new M(48);
              D.set(a, b);
            } else ((c = !1), (b = a.byteOffset));
            return { T: b, Sa: c };
          },
          ya: function (a, b, c, d, e) {
            if (32768 !== (a.node.mode & 61440)) throw new M(43);
            if (e & 2) return 0;
            N.F.write(a, b, 0, d, c, !1);
            return 0;
          },
        },
      };
    function Ta(a, b, c) {
      var d = "al " + a;
      fa(
        a,
        (e) => {
          e || y('Loading data file "' + a + '" failed (no arrayBuffer).');
          b(new Uint8Array(e));
          d && va(d);
        },
        () => {
          if (c) c();
          else throw 'Loading data file "' + a + '" failed.';
        },
      );
      d && ua(d);
    }
    var Ua = null,
      Va = {},
      P = [],
      Wa = 1,
      Q = null,
      Xa = !0,
      M = null,
      Sa = {},
      R = (a, b = {}) => {
        a = Ia("/", a);
        if (!a) return { path: "", node: null };
        b = Object.assign({ ta: !0, oa: 0 }, b);
        if (8 < b.oa) throw new M(32);
        a = Ea(
          a.split("/").filter((k) => !!k),
          !1,
        );
        for (var c = Ua, d = "/", e = 0; e < a.length; e++) {
          var h = e === a.length - 1;
          if (h && b.parent) break;
          c = O(c, a[e]);
          d = L(d + "/" + a[e]);
          c.$ && (!h || (h && b.ta)) && (c = c.$.root);
          if (!h || b.ka) for (h = 0; 40960 === (c.mode & 61440); ) if (((c = Ya(d)), (d = Ia(Fa(d), c)), (c = R(d, { oa: b.oa + 1 }).node), 40 < h++)) throw new M(32);
        }
        return { path: d, node: c };
      },
      S = (a) => {
        for (var b; ; ) {
          if (a === a.parent) return ((a = a.N.xa), b ? ("/" !== a[a.length - 1] ? a + "/" + b : a + b) : a);
          b = b ? a.name + "/" + b : a.name;
          a = a.parent;
        }
      },
      Za = (a, b) => {
        for (var c = 0, d = 0; d < b.length; d++) c = ((c << 5) - c + b.charCodeAt(d)) | 0;
        return ((a + c) >>> 0) % Q.length;
      },
      O = (a, b) => {
        var c;
        if ((c = (c = $a(a, "x")) ? c : a.D.Z ? 0 : 2)) throw new M(c, a);
        for (c = Q[Za(a.id, b)]; c; c = c.X) {
          var d = c.name;
          if (c.parent.id === a.id && d === b) return c;
        }
        return a.D.Z(a, b);
      },
      Ra = (a, b, c, d) => {
        a = new ab(a, b, c, d);
        b = Za(a.parent.id, a.name);
        a.X = Q[b];
        return (Q[b] = a);
      },
      bb = { r: 0, "r+": 2, w: 577, "w+": 578, a: 1089, "a+": 1090 },
      cb = (a) => {
        var b = ["r", "w", "rw"][a & 3];
        a & 512 && (b += "w");
        return b;
      },
      $a = (a, b) => {
        if (Xa) return 0;
        if (!b.includes("r") || a.mode & 292) {
          if ((b.includes("w") && !(a.mode & 146)) || (b.includes("x") && !(a.mode & 73))) return 2;
        } else return 2;
        return 0;
      },
      db = (a, b) => {
        try {
          return (O(a, b), 20);
        } catch (c) {}
        return $a(a, "wx");
      },
      eb = (a = 0) => {
        for (; 4096 >= a; a++) if (!P[a]) return a;
        throw new M(33);
      },
      gb = (a, b) => {
        fb ||
          ((fb = function () {
            this.J = {};
          }),
          (fb.prototype = {}),
          Object.defineProperties(fb.prototype, {
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
                return this.J.flags;
              },
              set: function (c) {
                this.J.flags = c;
              },
            },
            position: {
              get: function () {
                return this.J.position;
              },
              set: function (c) {
                this.J.position = c;
              },
            },
          }));
        a = Object.assign(new fb(), a);
        b = eb(b);
        a.P = b;
        return (P[b] = a);
      },
      Qa = {
        open: (a) => {
          a.F = Va[a.node.ca].F;
          a.F.open && a.F.open(a);
        },
        S: () => {
          throw new M(70);
        },
      },
      Ma = (a, b) => {
        Va[a] = { F: b };
      },
      hb = (a, b) => {
        var c = "/" === b,
          d = !b;
        if (c && Ua) throw new M(10);
        if (!c && !d) {
          var e = R(b, { ta: !1 });
          b = e.path;
          e = e.node;
          if (e.$) throw new M(10);
          if (16384 !== (e.mode & 61440)) throw new M(54);
        }
        b = { type: a, eb: {}, xa: b, Ka: [] };
        a = a.N(b);
        a.N = b;
        b.root = a;
        c ? (Ua = a) : e && ((e.$ = b), e.N && e.N.Ka.push(b));
      },
      T = (a, b, c) => {
        var d = R(a, { parent: !0 }).node;
        a = Ga(a);
        if (!a || "." === a || ".." === a) throw new M(28);
        var e = db(d, a);
        if (e) throw new M(e);
        if (!d.D.ba) throw new M(63);
        return d.D.ba(d, a, b, c);
      },
      ib = (a, b, c) => {
        "undefined" == typeof c && ((c = b), (b = 438));
        return T(a, b | 8192, c);
      },
      jb = (a, b) => {
        if (!Ia(a)) throw new M(44);
        var c = R(b, { parent: !0 }).node;
        if (!c) throw new M(44);
        b = Ga(b);
        var d = db(c, b);
        if (d) throw new M(d);
        if (!c.D.da) throw new M(63);
        c.D.da(c, b, a);
      },
      kb = (a) => {
        var b = R(a, { parent: !0 }).node;
        if (!b) throw new M(44);
        var c = Ga(a);
        a = O(b, c);
        a: {
          try {
            var d = O(b, c);
          } catch (h) {
            d = h.O;
            break a;
          }
          var e = $a(b, "wx");
          d = e ? e : 16384 === (d.mode & 61440) ? 31 : 0;
        }
        if (d) throw new M(d);
        if (!b.D.ga) throw new M(63);
        if (a.$) throw new M(10);
        b.D.ga(b, c);
        b = Za(a.parent.id, a.name);
        if (Q[b] === a) Q[b] = a.X;
        else
          for (b = Q[b]; b; ) {
            if (b.X === a) {
              b.X = a.X;
              break;
            }
            b = b.X;
          }
      },
      Ya = (a) => {
        a = R(a).node;
        if (!a) throw new M(44);
        if (!a.D.aa) throw new M(28);
        return Ia(S(a.parent), a.D.aa(a));
      },
      lb = (a, b) => {
        a = "string" == typeof a ? R(a, { ka: !0 }).node : a;
        if (!a.D.K) throw new M(63);
        a.D.K(a, { mode: (b & 4095) | (a.mode & -4096), timestamp: Date.now() });
      },
      nb = (a, b, c) => {
        if ("" === a) throw new M(44);
        if ("string" == typeof b) {
          var d = bb[b];
          if ("undefined" == typeof d) throw Error("Unknown file open mode: " + b);
          b = d;
        }
        c = b & 64 ? (("undefined" == typeof c ? 438 : c) & 4095) | 32768 : 0;
        if ("object" == typeof a) var e = a;
        else {
          a = L(a);
          try {
            e = R(a, { ka: !(b & 131072) }).node;
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
        if (!d && (c = e ? (40960 === (e.mode & 61440) ? 32 : 16384 === (e.mode & 61440) && ("r" !== cb(b) || b & 512) ? 31 : $a(e, cb(b))) : 44)) throw new M(c);
        if (b & 512 && !d) {
          c = e;
          c = "string" == typeof c ? R(c, { ka: !0 }).node : c;
          if (!c.D.K) throw new M(63);
          if (16384 === (c.mode & 61440)) throw new M(31);
          if (32768 !== (c.mode & 61440)) throw new M(28);
          if ((d = $a(c, "w"))) throw new M(d);
          c.D.K(c, { size: 0, timestamp: Date.now() });
        }
        b &= -131713;
        e = gb({ node: e, path: S(e), flags: b, seekable: !0, position: 0, F: e.F, Ra: [], error: !1 });
        e.F.open && e.F.open(e);
        !f.logReadFiles || b & 1 || (mb || (mb = {}), a in mb || (mb[a] = 1));
        return e;
      },
      ob = (a) => {
        if (null === a.P) throw new M(8);
        a.la && (a.la = null);
        try {
          a.F.close && a.F.close(a);
        } catch (b) {
          throw b;
        } finally {
          P[a.P] = null;
        }
        a.P = null;
      },
      pb = (a, b, c) => {
        if (null === a.P) throw new M(8);
        if (!a.seekable || !a.F.S) throw new M(70);
        if (0 != c && 1 != c && 2 != c) throw new M(28);
        a.position = a.F.S(a, b, c);
        a.Ra = [];
      },
      qb = (a, b, c, d, e, h) => {
        if (0 > d || 0 > e) throw new M(28);
        if (null === a.P) throw new M(8);
        if (0 === (a.flags & 2097155)) throw new M(8);
        if (16384 === (a.node.mode & 61440)) throw new M(31);
        if (!a.F.write) throw new M(28);
        a.seekable && a.flags & 1024 && pb(a, 0, 2);
        var k = "undefined" != typeof e;
        if (!k) e = a.position;
        else if (!a.seekable) throw new M(70);
        b = a.F.write(a, b, c, d, e, h);
        k || (a.position += b);
        return b;
      },
      rb = () => {
        M ||
          ((M = function (a, b) {
            this.node = b;
            this.Ma = function (c) {
              this.O = c;
            };
            this.Ma(a);
            this.message = "FS error";
          }),
          (M.prototype = Error()),
          (M.prototype.constructor = M),
          [44].forEach((a) => {
            Sa[a] = new M(a);
            Sa[a].stack = "<generic error, no stack>";
          }));
      },
      sb,
      tb = (a, b) => {
        var c = 0;
        a && (c |= 365);
        b && (c |= 146);
        return c;
      },
      ub = (a, b) => {
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
      vb = (a, b, c, d) => {
        a = L(("string" == typeof a ? a : S(a)) + "/" + b);
        c = tb(c, d);
        return T(a, ((void 0 !== c ? c : 438) & 4095) | 32768, 0);
      },
      wb = (a, b, c, d, e, h) => {
        var k = b;
        a && ((a = "string" == typeof a ? a : S(a)), (k = b ? L(a + "/" + b) : a));
        a = tb(d, e);
        k = T(k, ((void 0 !== a ? a : 438) & 4095) | 32768, 0);
        if (c) {
          if ("string" == typeof c) {
            b = Array(c.length);
            d = 0;
            for (e = c.length; d < e; ++d) b[d] = c.charCodeAt(d);
            c = b;
          }
          lb(k, a | 146);
          b = nb(k, 577);
          qb(b, c, 0, c.length, 0, h);
          ob(b);
          lb(k, a);
        }
        return k;
      },
      V = (a, b, c, d) => {
        a = L(("string" == typeof a ? a : S(a)) + "/" + b);
        b = tb(!!c, !!d);
        V.wa || (V.wa = 64);
        var e = (V.wa++ << 8) | 0;
        Ma(e, {
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
        return ib(a, b, e);
      },
      xb = (a) => {
        if (!(a.va || a.Ja || a.link || a.C)) {
          if ("undefined" != typeof XMLHttpRequest)
            throw Error(
              "Lazy loading should have been performed (contents set) in createLazyFile, but it was not. Lazy loading only works in web workers. Use --embed-file or --preload-file in emcc on the main thread.",
            );
          if (ea)
            try {
              ((a.C = Ja(ea(a.url), !0)), (a.G = a.C.length));
            } catch (b) {
              throw new M(29);
            }
          else throw Error("Cannot load without read() or XMLHttpRequest.");
        }
      },
      yb = (a, b, c, d, e) => {
        if ("undefined" != typeof XMLHttpRequest) throw "Cannot do synchronous binary XHRs outside webworkers in modern browsers. Use --embed-file or --preload-file in emcc";
        c = { va: !1, url: c };
        var h = vb(a, b, d, e);
        c.C ? (h.C = c.C) : c.url && ((h.C = null), (h.url = c.url));
        Object.defineProperties(h, {
          G: {
            get: function () {
              return this.C.length;
            },
          },
        });
        var k = {};
        Object.keys(h.F).forEach((n) => {
          var m = h.F[n];
          k[n] = function () {
            xb(h);
            return m.apply(null, arguments);
          };
        });
        k.read = (n, m, p, r, q) => {
          xb(h);
          n = n.node.C;
          if (q >= n.length) m = 0;
          else {
            r = Math.min(n.length - q, r);
            if (n.slice) for (var t = 0; t < r; t++) m[p + t] = n[q + t];
            else for (t = 0; t < r; t++) m[p + t] = n.get(q + t);
            m = r;
          }
          return m;
        };
        k.ma = () => {
          xb(h);
          y();
          throw new M(48);
        };
        h.F = k;
        return h;
      },
      Ab = (a, b, c, d, e, h, k, n, m, p) => {
        function r(g) {
          function v(w) {
            p && p();
            n || wb(a, b, w, d, e, m);
            h && h();
            va(t);
          }
          zb.$a(g, q, v, () => {
            k && k();
            va(t);
          }) || v(g);
        }
        var q = b ? Ia(L(a + "/" + b)) : a,
          t = "cp " + q;
        ua(t);
        "string" == typeof c ? Ta(c, (g) => r(g), k) : r(c);
      },
      W = {},
      fb,
      mb,
      Bb = void 0;
    function X() {
      Bb += 4;
      return E[(Bb - 4) >> 2];
    }
    function Y(a) {
      a = P[a];
      if (!a) throw new M(8);
      return a;
    }
    var Cb = {};
    function Db() {
      if (!Eb) {
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
        for (b in Cb) void 0 === Cb[b] ? delete a[b] : (a[b] = Cb[b]);
        var c = [];
        for (b in a) c.push(b + "=" + a[b]);
        Eb = c;
      }
      return Eb;
    }
    var Eb;
    function Fb(a) {
      return 0 === a % 4 && (0 !== a % 100 || 0 === a % 400);
    }
    var Gb = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
      Hb = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    function Ib(a, b, c, d) {
      function e(g, v, w) {
        for (g = "number" == typeof g ? g.toString() : g || ""; g.length < v; ) g = w[0] + g;
        return g;
      }
      function h(g, v) {
        return e(g, v, "0");
      }
      function k(g, v) {
        function w(U) {
          return 0 > U ? -1 : 0 < U ? 1 : 0;
        }
        var z;
        0 === (z = w(g.getFullYear() - v.getFullYear())) && 0 === (z = w(g.getMonth() - v.getMonth())) && (z = w(g.getDate() - v.getDate()));
        return z;
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
        var v = g.U;
        for (g = new Date(new Date(g.V + 1900, 0, 1).getTime()); 0 < v; ) {
          var w = g.getMonth(),
            z = (Fb(g.getFullYear()) ? Gb : Hb)[w];
          if (v > z - g.getDate()) ((v -= z - g.getDate() + 1), g.setDate(1), 11 > w ? g.setMonth(w + 1) : (g.setMonth(0), g.setFullYear(g.getFullYear() + 1)));
          else {
            g.setDate(g.getDate() + v);
            break;
          }
        }
        w = new Date(g.getFullYear() + 1, 0, 4);
        v = n(new Date(g.getFullYear(), 0, 4));
        w = n(w);
        return 0 >= k(v, g) ? (0 >= k(w, g) ? g.getFullYear() + 1 : g.getFullYear()) : g.getFullYear() - 1;
      }
      var p = E[(d + 40) >> 2];
      d = {
        Pa: E[d >> 2],
        Oa: E[(d + 4) >> 2],
        ea: E[(d + 8) >> 2],
        pa: E[(d + 12) >> 2],
        fa: E[(d + 16) >> 2],
        V: E[(d + 20) >> 2],
        M: E[(d + 24) >> 2],
        U: E[(d + 28) >> 2],
        fb: E[(d + 32) >> 2],
        Na: E[(d + 36) >> 2],
        Qa: p ? B(p) : "",
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
          return q[g.M].substring(0, 3);
        },
        "%A": function (g) {
          return q[g.M];
        },
        "%b": function (g) {
          return t[g.fa].substring(0, 3);
        },
        "%B": function (g) {
          return t[g.fa];
        },
        "%C": function (g) {
          return h(((g.V + 1900) / 100) | 0, 2);
        },
        "%d": function (g) {
          return h(g.pa, 2);
        },
        "%e": function (g) {
          return e(g.pa, 2, " ");
        },
        "%g": function (g) {
          return m(g).toString().substring(2);
        },
        "%G": function (g) {
          return m(g);
        },
        "%H": function (g) {
          return h(g.ea, 2);
        },
        "%I": function (g) {
          g = g.ea;
          0 == g ? (g = 12) : 12 < g && (g -= 12);
          return h(g, 2);
        },
        "%j": function (g) {
          for (var v = 0, w = 0; w <= g.fa - 1; v += (Fb(g.V + 1900) ? Gb : Hb)[w++]);
          return h(g.pa + v, 3);
        },
        "%m": function (g) {
          return h(g.fa + 1, 2);
        },
        "%M": function (g) {
          return h(g.Oa, 2);
        },
        "%n": function () {
          return "\n";
        },
        "%p": function (g) {
          return 0 <= g.ea && 12 > g.ea ? "AM" : "PM";
        },
        "%S": function (g) {
          return h(g.Pa, 2);
        },
        "%t": function () {
          return "\t";
        },
        "%u": function (g) {
          return g.M || 7;
        },
        "%U": function (g) {
          return h(Math.floor((g.U + 7 - g.M) / 7), 2);
        },
        "%V": function (g) {
          var v = Math.floor((g.U + 7 - ((g.M + 6) % 7)) / 7);
          2 >= (g.M + 371 - g.U - 2) % 7 && v++;
          if (v) 53 == v && ((w = (g.M + 371 - g.U) % 7), 4 == w || (3 == w && Fb(g.V)) || (v = 1));
          else {
            v = 52;
            var w = (g.M + 7 - g.U - 1) % 7;
            (4 == w || (5 == w && Fb((g.V % 400) - 1))) && v++;
          }
          return h(v, 2);
        },
        "%w": function (g) {
          return g.M;
        },
        "%W": function (g) {
          return h(Math.floor((g.U + 7 - ((g.M + 6) % 7)) / 7), 2);
        },
        "%y": function (g) {
          return (g.V + 1900).toString().substring(2);
        },
        "%Y": function (g) {
          return g.V + 1900;
        },
        "%z": function (g) {
          g = g.Na;
          var v = 0 <= g;
          g = Math.abs(g) / 60;
          return (v ? "+" : "-") + String("0000" + ((g / 60) * 100 + (g % 60))).slice(-4);
        },
        "%Z": function (g) {
          return g.Qa;
        },
        "%%": function () {
          return "%";
        },
      };
      c = c.replace(/%%/g, "\x00\x00");
      for (r in p) c.includes(r) && (c = c.replace(new RegExp(r, "g"), p[r](d)));
      c = c.replace(/\0\0/g, "%");
      r = Ja(c, !1);
      if (r.length > b) return 0;
      D.set(r, a);
      return r.length - 1;
    }
    var Z = void 0,
      Jb = [];
    function Kb(a, b, c, d) {
      var e = {
        string: (p) => {
          var r = 0;
          if (null !== p && void 0 !== p && 0 !== p) {
            var q = (p.length << 2) + 1;
            r = Lb(q);
            la(p, C, r, q);
          }
          return r;
        },
        array: (p) => {
          var r = Lb(p.length);
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
          m ? (0 === k && (k = Mb()), (h[n] = m(d[n]))) : (h[n] = d[n]);
        }
      c = a.apply(null, h);
      return (c = (function (p) {
        0 !== k && Nb(k);
        return "string" === b ? B(p) : "boolean" === b ? !!p : p;
      })(c));
    }
    function ab(a, b, c, d) {
      a || (a = this);
      this.parent = a;
      this.N = a.N;
      this.$ = null;
      this.id = Wa++;
      this.name = b;
      this.mode = c;
      this.D = {};
      this.F = {};
      this.ca = d;
    }
    Object.defineProperties(ab.prototype, {
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
      Ja: {
        get: function () {
          return 16384 === (this.mode & 61440);
        },
      },
      va: {
        get: function () {
          return 8192 === (this.mode & 61440);
        },
      },
    });
    rb();
    Q = Array(4096);
    hb(N, "/");
    T("/tmp", 16895, 0);
    T("/home", 16895, 0);
    T("/home/web_user", 16895, 0);
    (() => {
      T("/dev", 16895, 0);
      Ma(259, { read: () => 0, write: (b, c, d, e) => e });
      ib("/dev/null", 259);
      La(1280, Oa);
      La(1536, Pa);
      ib("/dev/tty", 1280);
      ib("/dev/tty1", 1536);
      var a = Ha();
      V("/dev", "random", a);
      V("/dev", "urandom", a);
      T("/dev/shm", 16895, 0);
      T("/dev/shm/tmp", 16895, 0);
    })();
    (() => {
      T("/proc", 16895, 0);
      var a = T("/proc/self", 16895, 0);
      T("/proc/self/fd", 16895, 0);
      hb(
        {
          N: () => {
            var b = Ra(a, "fd", 16895, 73);
            b.D = {
              Z: (c, d) => {
                var e = P[+d];
                if (!e) throw new M(8);
                c = { parent: null, N: { xa: "fake" }, D: { aa: () => e.path } };
                return (c.parent = c);
              },
            };
            return b;
          },
        },
        "/proc/self/fd",
      );
    })();
    var zb;
    f.FS_createPath = ub;
    f.FS_createDataFile = wb;
    f.FS_createPreloadedFile = Ab;
    f.FS_unlink = kb;
    f.FS_createLazyFile = yb;
    f.FS_createDevice = V;
    var Qb = {
      a: function (a) {
        return Ob(a + 24) + 24;
      },
      b: function (a, b, c) {
        new Ca(a).J(b, c);
        Da++;
        throw a;
      },
      i: function (a, b, c) {
        Bb = c;
        try {
          var d = Y(a);
          switch (b) {
            case 0:
              var e = X();
              return 0 > e ? -28 : gb(d, e).P;
            case 1:
            case 2:
              return 0;
            case 3:
              return d.flags;
            case 4:
              return ((e = X()), (d.flags |= e), 0);
            case 5:
              return ((e = X()), (na[(e + 0) >> 1] = 2), 0);
            case 6:
            case 7:
              return 0;
            case 16:
            case 8:
              return -28;
            case 9:
              return ((E[Pb() >> 2] = 28), -1);
            default:
              return -28;
          }
        } catch (h) {
          if ("undefined" == typeof W || !(h instanceof M)) throw h;
          return -h.O;
        }
      },
      j: function (a, b, c) {
        Bb = c;
        try {
          var d = Y(a);
          switch (b) {
            case 21509:
            case 21505:
              return d.H ? 0 : -59;
            case 21510:
            case 21511:
            case 21512:
            case 21506:
            case 21507:
            case 21508:
              return d.H ? 0 : -59;
            case 21519:
              if (!d.H) return -59;
              var e = X();
              return (E[e >> 2] = 0);
            case 21520:
              return d.H ? -28 : -59;
            case 21531:
              a = e = X();
              if (!d.F.Ia) throw new M(59);
              return d.F.Ia(d, b, a);
            case 21523:
              return d.H ? 0 : -59;
            case 21524:
              return d.H ? 0 : -59;
            default:
              return -28;
          }
        } catch (h) {
          if ("undefined" == typeof W || !(h instanceof M)) throw h;
          return -h.O;
        }
      },
      h: function (a, b, c, d) {
        Bb = d;
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
          return nb(b, c, n).P;
        } catch (m) {
          if ("undefined" == typeof W || !(m instanceof M)) throw m;
          return -m.O;
        }
      },
      l: function () {
        return !0;
      },
      d: function () {
        y("");
      },
      k: () => performance.now(),
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
              ia.grow((e - ma.byteLength + 65535) >>> 16);
              oa();
              var h = 1;
              break a;
            } catch (k) {}
            h = void 0;
          }
          if (h) return !0;
        }
        return !1;
      },
      o: function (a, b) {
        var c = 0;
        Db().forEach(function (d, e) {
          var h = b + c;
          e = F[(a + 4 * e) >> 2] = h;
          for (h = 0; h < d.length; ++h) D[e++ >> 0] = d.charCodeAt(h);
          D[e >> 0] = 0;
          c += d.length + 1;
        });
        return 0;
      },
      p: function (a, b) {
        var c = Db();
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
          ob(b);
          return 0;
        } catch (c) {
          if ("undefined" == typeof W || !(c instanceof M)) throw c;
          return c.O;
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
              var m = e,
                p = k,
                r = n,
                q = void 0,
                t = D;
              if (0 > r || 0 > q) throw new M(28);
              if (null === m.P) throw new M(8);
              if (1 === (m.flags & 2097155)) throw new M(8);
              if (16384 === (m.node.mode & 61440)) throw new M(31);
              if (!m.F.read) throw new M(28);
              var g = "undefined" != typeof q;
              if (!g) q = m.position;
              else if (!m.seekable) throw new M(70);
              var v = m.F.read(m, t, p, r, q);
              g || (m.position += v);
              var w = v;
              if (0 > w) {
                var z = -1;
                break a;
              }
              b += w;
              if (w < n) break;
            }
            z = b;
          }
          E[d >> 2] = z;
          return 0;
        } catch (U) {
          if ("undefined" == typeof W || !(U instanceof M)) throw U;
          return U.O;
        }
      },
      m: function (a, b, c, d, e) {
        try {
          b = (c + 2097152) >>> 0 < 4194305 - !!b ? (b >>> 0) + 4294967296 * c : NaN;
          if (isNaN(b)) return 61;
          var h = Y(a);
          pb(h, b, d);
          Aa = [
            h.position >>> 0,
            ((K = h.position), 1 <= +Math.abs(K) ? (0 < K ? (Math.min(+Math.floor(K / 4294967296), 4294967295) | 0) >>> 0 : ~~+Math.ceil((K - +(~~K >>> 0)) / 4294967296) >>> 0) : 0),
          ];
          E[e >> 2] = Aa[0];
          E[(e + 4) >> 2] = Aa[1];
          h.la && 0 === b && 0 === d && (h.la = null);
          return 0;
        } catch (k) {
          if ("undefined" == typeof W || !(k instanceof M)) throw k;
          return k.O;
        }
      },
      f: function (a, b, c, d) {
        try {
          a: {
            var e = Y(a);
            a = b;
            for (var h = (b = 0); h < c; h++) {
              var k = F[a >> 2],
                n = F[(a + 4) >> 2];
              a += 8;
              var m = qb(e, D, k, n);
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
          return r.O;
        }
      },
      n: function (a, b, c, d) {
        return Ib(a, b, c, d);
      },
    };
    (function () {
      function a(e) {
        f.asm = e.exports;
        ia = f.asm.q;
        oa();
        G = f.asm.v;
        qa.unshift(f.asm.r);
        va("wasm-instantiate");
      }
      function b(e) {
        a(e.instance);
      }
      function c(e) {
        return za()
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
      var d = { a: Qb };
      ua("wasm-instantiate");
      if (f.instantiateWasm)
        try {
          return f.instantiateWasm(d, a);
        } catch (e) {
          return (l("Module.instantiateWasm callback failed with error: " + e), !1);
        }
      (function () {
        return x || "function" != typeof WebAssembly.instantiateStreaming || wa() || "function" != typeof fetch
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
      return (f.___wasm_call_ctors = f.asm.r).apply(null, arguments);
    };
    f._initialize = function () {
      return (f._initialize = f.asm.s).apply(null, arguments);
    };
    f._mrz_read = function () {
      return (f._mrz_read = f.asm.t).apply(null, arguments);
    };
    var Ob = (f._malloc = function () {
      return (Ob = f._malloc = f.asm.u).apply(null, arguments);
    });
    f._free = function () {
      return (f._free = f.asm.w).apply(null, arguments);
    };
    var Pb = (f.___errno_location = function () {
        return (Pb = f.___errno_location = f.asm.x).apply(null, arguments);
      }),
      Mb = (f.stackSave = function () {
        return (Mb = f.stackSave = f.asm.y).apply(null, arguments);
      }),
      Nb = (f.stackRestore = function () {
        return (Nb = f.stackRestore = f.asm.z).apply(null, arguments);
      }),
      Lb = (f.stackAlloc = function () {
        return (Lb = f.stackAlloc = f.asm.A).apply(null, arguments);
      });
    f.___cxa_is_pointer_type = function () {
      return (f.___cxa_is_pointer_type = f.asm.B).apply(null, arguments);
    };
    f.UTF8ToString = B;
    f.addRunDependency = ua;
    f.removeRunDependency = va;
    f.FS_createPath = ub;
    f.FS_createDataFile = wb;
    f.FS_createPreloadedFile = Ab;
    f.FS_createLazyFile = yb;
    f.FS_createDevice = V;
    f.FS_unlink = kb;
    f.ccall = Kb;
    f.cwrap = function (a, b, c, d) {
      c = c || [];
      var e = c.every((h) => "number" === h || "boolean" === h);
      return "string" !== b && e && !d
        ? f["_" + a]
        : function () {
            return Kb(a, b, c, arguments, d);
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
      if (Jb.length) c = Jb.pop();
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
    var Rb;
    I = function Sb() {
      Rb || Tb();
      Rb || (I = Sb);
    };
    function Tb() {
      function a() {
        if (!Rb && ((Rb = !0), (f.calledRun = !0), !ja)) {
          f.noFSInit ||
            sb ||
            ((sb = !0),
            rb(),
            (f.stdin = f.stdin),
            (f.stdout = f.stdout),
            (f.stderr = f.stderr),
            f.stdin ? V("/dev", "stdin", f.stdin) : jb("/dev/tty", "/dev/stdin"),
            f.stdout ? V("/dev", "stdout", null, f.stdout) : jb("/dev/tty", "/dev/stdout"),
            f.stderr ? V("/dev", "stderr", null, f.stderr) : jb("/dev/tty1", "/dev/stderr"),
            nb("/dev/stdin", 0),
            nb("/dev/stdout", 1),
            nb("/dev/stderr", 1));
          Xa = !1;
          Ba(qa);
          aa(f);
          if (f.onRuntimeInitialized) f.onRuntimeInitialized();
          if (f.postRun)
            for ("function" == typeof f.postRun && (f.postRun = [f.postRun]); f.postRun.length; ) {
              var b = f.postRun.shift();
              ra.unshift(b);
            }
          Ba(ra);
        }
      }
      if (!(0 < H)) {
        if (f.preRun) for ("function" == typeof f.preRun && (f.preRun = [f.preRun]); f.preRun.length; ) sa();
        Ba(pa);
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
    Tb();

    return passportModule.ready;
  };
})();
if (typeof exports === "object" && typeof module === "object") module.exports = passportModule;
else if (typeof define === "function" && define["amd"])
  define([], function () {
    return passportModule;
  });
else if (typeof exports === "object") exports["passportModule"] = passportModule;
