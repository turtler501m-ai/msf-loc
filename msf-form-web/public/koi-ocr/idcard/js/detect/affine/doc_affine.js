var affineModule = (() => {
  var _scriptDir = typeof document !== "undefined" && document.currentScript ? document.currentScript.src : undefined;

  return function (affineModule) {
    affineModule = affineModule || {};

    var h;
    h || (h = typeof affineModule !== "undefined" ? affineModule : {});
    var aa, ba;
    h.ready = new Promise(function (a, b) {
      aa = a;
      ba = b;
    });
    var ca = Object.assign({}, h),
      da = "./this.program",
      ea = (a, b) => {
        throw b;
      },
      k = "";
    "undefined" != typeof document && document.currentScript && (k = document.currentScript.src);
    _scriptDir && (k = _scriptDir);
    0 !== k.indexOf("blob:") ? (k = k.substr(0, k.replace(/[?#].*/, "").lastIndexOf("/") + 1)) : (k = "");
    var fa = h.print || console.log.bind(console),
      n = h.printErr || console.warn.bind(console);
    Object.assign(h, ca);
    ca = null;
    h.thisProgram && (da = h.thisProgram);
    h.quit && (ea = h.quit);
    var ha = 0,
      w;
    h.wasmBinary && (w = h.wasmBinary);
    var noExitRuntime = h.noExitRuntime || !0;
    "object" != typeof WebAssembly && x("no native wasm support detected");
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
          var g = a[b++] & 63;
          if (192 == (e & 224)) d += String.fromCharCode(((e & 31) << 6) | g);
          else {
            var l = a[b++] & 63;
            e = 224 == (e & 240) ? ((e & 15) << 12) | (g << 6) | l : ((e & 7) << 18) | (g << 12) | (l << 6) | (a[b++] & 63);
            65536 > e ? (d += String.fromCharCode(e)) : ((e -= 65536), (d += String.fromCharCode(55296 | (e >> 10), 56320 | (e & 1023))));
          }
        } else d += String.fromCharCode(e);
      }
      return d;
    }
    function A(a, b) {
      return a ? z(B, a, b) : "";
    }
    function la(a, b, c, d) {
      if (!(0 < d)) return 0;
      var e = c;
      d = c + d - 1;
      for (var g = 0; g < a.length; ++g) {
        var l = a.charCodeAt(g);
        if (55296 <= l && 57343 >= l) {
          var p = a.charCodeAt(++g);
          l = (65536 + ((l & 1023) << 10)) | (p & 1023);
        }
        if (127 >= l) {
          if (c >= d) break;
          b[c++] = l;
        } else {
          if (2047 >= l) {
            if (c + 1 >= d) break;
            b[c++] = 192 | (l >> 6);
          } else {
            if (65535 >= l) {
              if (c + 2 >= d) break;
              b[c++] = 224 | (l >> 12);
            } else {
              if (c + 3 >= d) break;
              b[c++] = 240 | (l >> 18);
              b[c++] = 128 | ((l >> 12) & 63);
            }
            b[c++] = 128 | ((l >> 6) & 63);
          }
          b[c++] = 128 | (l & 63);
        }
      }
      b[c] = 0;
      return c - e;
    }
    var ma, C, B, na, D, F;
    function oa() {
      var a = ia.buffer;
      ma = a;
      h.HEAP8 = C = new Int8Array(a);
      h.HEAP16 = na = new Int16Array(a);
      h.HEAP32 = D = new Int32Array(a);
      h.HEAPU8 = B = new Uint8Array(a);
      h.HEAPU16 = new Uint16Array(a);
      h.HEAPU32 = F = new Uint32Array(a);
      h.HEAPF32 = new Float32Array(a);
      h.HEAPF64 = new Float64Array(a);
    }
    var G,
      pa = [],
      qa = [],
      ra = [];
    function sa() {
      var a = h.preRun.shift();
      pa.unshift(a);
    }
    var H = 0,
      ta = null,
      I = null;
    function x(a) {
      if (h.onAbort) h.onAbort(a);
      a = "Aborted(" + a + ")";
      n(a);
      ja = !0;
      a = new WebAssembly.RuntimeError(a + ". Build with -sASSERTIONS for more info.");
      ba(a);
      throw a;
    }
    function ua() {
      return J.startsWith("data:application/octet-stream;base64,");
    }
    var J;
    J = "../../detect/affine/doc_affine.wasm";
    if (!ua()) {
      var va = J;
      J = h.locateFile ? h.locateFile(va, k) : k + va;
    }
    function wa() {
      var a = J;
      try {
        if (a == J && w) return new Uint8Array(w);
        throw "both async and sync fetching of the wasm failed";
      } catch (b) {
        x(b);
      }
    }
    function xa() {
      return w || "function" != typeof fetch
        ? Promise.resolve().then(function () {
            return wa();
          })
        : fetch(J, { credentials: "same-origin" })
            .then(function (a) {
              if (!a.ok) throw "failed to load wasm binary file at '" + J + "'";
              return a.arrayBuffer();
            })
            .catch(function () {
              return wa();
            });
    }
    var K, ya;
    function za(a) {
      this.name = "ExitStatus";
      this.message = "Program terminated with exit(" + a + ")";
      this.status = a;
    }
    function Aa(a) {
      for (; 0 < a.length; ) a.shift()(h);
    }
    function Ba(a) {
      this.ea = a - 24;
      this.Na = function (b) {
        F[(this.ea + 4) >> 2] = b;
      };
      this.Ka = function (b) {
        F[(this.ea + 8) >> 2] = b;
      };
      this.La = function () {
        D[this.ea >> 2] = 0;
      };
      this.Ja = function () {
        C[(this.ea + 12) >> 0] = 0;
      };
      this.Ma = function () {
        C[(this.ea + 13) >> 0] = 0;
      };
      this.ha = function (b, c) {
        this.Ia();
        this.Na(b);
        this.Ka(c);
        this.La();
        this.Ja();
        this.Ma();
      };
      this.Ia = function () {
        F[(this.ea + 16) >> 2] = 0;
      };
    }
    var Ca = 0,
      Da = (a, b) => {
        for (var c = 0, d = a.length - 1; 0 <= d; d--) {
          var e = a[d];
          "." === e ? a.splice(d, 1) : ".." === e ? (a.splice(d, 1), c++) : c && (a.splice(d, 1), c--);
        }
        if (b) for (; c; c--) a.unshift("..");
        return a;
      },
      M = (a) => {
        var b = "/" === a.charAt(0),
          c = "/" === a.substr(-1);
        (a = Da(
          a.split("/").filter((d) => !!d),
          !b,
        ).join("/")) ||
          b ||
          (a = ".");
        a && c && (a += "/");
        return (b ? "/" : "") + a;
      },
      Ea = (a) => {
        var b = /^(\/?|)([\s\S]*?)((?:\.{1,2}|[^\/]+?|)(\.[^.\/]*|))(?:[\/]*)$/.exec(a).slice(1);
        a = b[0];
        b = b[1];
        if (!a && !b) return ".";
        b && (b = b.substr(0, b.length - 1));
        return a + b;
      },
      Fa = (a) => {
        if ("/" === a) return "/";
        a = M(a);
        a = a.replace(/\/$/, "");
        var b = a.lastIndexOf("/");
        return -1 === b ? a : a.substr(b + 1);
      };
    function Ga() {
      if ("object" == typeof crypto && "function" == typeof crypto.getRandomValues) {
        var a = new Uint8Array(1);
        return () => {
          crypto.getRandomValues(a);
          return a[0];
        };
      }
      return () => x("randomDevice");
    }
    function Ha() {
      for (var a = "", b = !1, c = arguments.length - 1; -1 <= c && !b; c--) {
        b = 0 <= c ? arguments[c] : "/";
        if ("string" != typeof b) throw new TypeError("Arguments to path.resolve must be strings");
        if (!b) return "";
        a = b + "/" + a;
        b = "/" === b.charAt(0);
      }
      a = Da(
        a.split("/").filter((d) => !!d),
        !b,
      ).join("/");
      return (b ? "/" : "") + a || ".";
    }
    function Ia(a, b) {
      for (var c = 0, d = 0; d < a.length; ++d) {
        var e = a.charCodeAt(d);
        127 >= e ? c++ : 2047 >= e ? (c += 2) : 55296 <= e && 57343 >= e ? ((c += 4), ++d) : (c += 3);
      }
      c = Array(c + 1);
      a = la(a, c, 0, c.length);
      b && (c.length = a);
      return c;
    }
    var Ja = [];
    function Ka(a, b) {
      Ja[a] = { input: [], W: [], ia: b };
      La(a, Ma);
    }
    var Ma = {
        open: function (a) {
          var b = Ja[a.node.na];
          if (!b) throw new N(43);
          a.V = b;
          a.seekable = !1;
        },
        close: function (a) {
          a.V.ia.flush(a.V);
        },
        flush: function (a) {
          a.V.ia.flush(a.V);
        },
        read: function (a, b, c, d) {
          if (!a.V || !a.V.ia.Aa) throw new N(60);
          for (var e = 0, g = 0; g < d; g++) {
            try {
              var l = a.V.ia.Aa(a.V);
            } catch (p) {
              throw new N(29);
            }
            if (void 0 === l && 0 === e) throw new N(6);
            if (null === l || void 0 === l) break;
            e++;
            b[c + g] = l;
          }
          e && (a.node.timestamp = Date.now());
          return e;
        },
        write: function (a, b, c, d) {
          if (!a.V || !a.V.ia.sa) throw new N(60);
          try {
            for (var e = 0; e < d; e++) a.V.ia.sa(a.V, b[c + e]);
          } catch (g) {
            throw new N(29);
          }
          d && (a.node.timestamp = Date.now());
          return e;
        },
      },
      Na = {
        Aa: function (a) {
          if (!a.input.length) {
            var b = null;
            "undefined" != typeof window && "function" == typeof window.prompt
              ? ((b = window.prompt("Input: ")), null !== b && (b += "\n"))
              : "function" == typeof readline && ((b = readline()), null !== b && (b += "\n"));
            if (!b) return null;
            a.input = Ia(b, !0);
          }
          return a.input.shift();
        },
        sa: function (a, b) {
          null === b || 10 === b ? (fa(z(a.W, 0)), (a.W = [])) : 0 != b && a.W.push(b);
        },
        flush: function (a) {
          a.W && 0 < a.W.length && (fa(z(a.W, 0)), (a.W = []));
        },
      },
      Oa = {
        sa: function (a, b) {
          null === b || 10 === b ? (n(z(a.W, 0)), (a.W = [])) : 0 != b && a.W.push(b);
        },
        flush: function (a) {
          a.W && 0 < a.W.length && (n(z(a.W, 0)), (a.W = []));
        },
      },
      O = {
        X: null,
        $: function () {
          return O.createNode(null, "/", 16895, 0);
        },
        createNode: function (a, b, c, d) {
          if (24576 === (c & 61440) || 4096 === (c & 61440)) throw new N(63);
          O.X ||
            (O.X = {
              dir: { node: { ba: O.S.ba, Y: O.S.Y, ja: O.S.ja, la: O.S.la, Fa: O.S.Fa, Ha: O.S.Ha, Ga: O.S.Ga, Ea: O.S.Ea, oa: O.S.oa }, stream: { da: O.T.da } },
              file: { node: { ba: O.S.ba, Y: O.S.Y }, stream: { da: O.T.da, read: O.T.read, write: O.T.write, va: O.T.va, Ba: O.T.Ba, Da: O.T.Da } },
              link: { node: { ba: O.S.ba, Y: O.S.Y, ka: O.S.ka }, stream: {} },
              wa: { node: { ba: O.S.ba, Y: O.S.Y }, stream: Pa },
            });
          c = Qa(a, b, c, d);
          16384 === (c.mode & 61440)
            ? ((c.S = O.X.dir.node), (c.T = O.X.dir.stream), (c.R = {}))
            : 32768 === (c.mode & 61440)
              ? ((c.S = O.X.file.node), (c.T = O.X.file.stream), (c.U = 0), (c.R = null))
              : 40960 === (c.mode & 61440)
                ? ((c.S = O.X.link.node), (c.T = O.X.link.stream))
                : 8192 === (c.mode & 61440) && ((c.S = O.X.wa.node), (c.T = O.X.wa.stream));
          c.timestamp = Date.now();
          a && ((a.R[b] = c), (a.timestamp = c.timestamp));
          return c;
        },
        eb: function (a) {
          return a.R ? (a.R.subarray ? a.R.subarray(0, a.U) : new Uint8Array(a.R)) : new Uint8Array(0);
        },
        xa: function (a, b) {
          var c = a.R ? a.R.length : 0;
          c >= b || ((b = Math.max(b, (c * (1048576 > c ? 2 : 1.125)) >>> 0)), 0 != c && (b = Math.max(b, 256)), (c = a.R), (a.R = new Uint8Array(b)), 0 < a.U && a.R.set(c.subarray(0, a.U), 0));
        },
        Sa: function (a, b) {
          if (a.U != b)
            if (0 == b) ((a.R = null), (a.U = 0));
            else {
              var c = a.R;
              a.R = new Uint8Array(b);
              c && a.R.set(c.subarray(0, Math.min(b, a.U)));
              a.U = b;
            }
        },
        S: {
          ba: function (a) {
            var b = {};
            b.cb = 8192 === (a.mode & 61440) ? a.id : 1;
            b.gb = a.id;
            b.mode = a.mode;
            b.ib = 1;
            b.uid = 0;
            b.fb = 0;
            b.na = a.na;
            16384 === (a.mode & 61440) ? (b.size = 4096) : 32768 === (a.mode & 61440) ? (b.size = a.U) : 40960 === (a.mode & 61440) ? (b.size = a.link.length) : (b.size = 0);
            b.$a = new Date(a.timestamp);
            b.hb = new Date(a.timestamp);
            b.bb = new Date(a.timestamp);
            b.Oa = 4096;
            b.ab = Math.ceil(b.size / b.Oa);
            return b;
          },
          Y: function (a, b) {
            void 0 !== b.mode && (a.mode = b.mode);
            void 0 !== b.timestamp && (a.timestamp = b.timestamp);
            void 0 !== b.size && O.Sa(a, b.size);
          },
          ja: function () {
            throw Ra[44];
          },
          la: function (a, b, c, d) {
            return O.createNode(a, b, c, d);
          },
          Fa: function (a, b, c) {
            if (16384 === (a.mode & 61440)) {
              try {
                var d = Sa(b, c);
              } catch (g) {}
              if (d) for (var e in d.R) throw new N(55);
            }
            delete a.parent.R[a.name];
            a.parent.timestamp = Date.now();
            a.name = c;
            b.R[c] = a;
            b.timestamp = a.parent.timestamp;
            a.parent = b;
          },
          Ha: function (a, b) {
            delete a.R[b];
            a.timestamp = Date.now();
          },
          Ga: function (a, b) {
            var c = Sa(a, b),
              d;
            for (d in c.R) throw new N(55);
            delete a.R[b];
            a.timestamp = Date.now();
          },
          Ea: function (a) {
            var b = [".", ".."],
              c;
            for (c in a.R) a.R.hasOwnProperty(c) && b.push(c);
            return b;
          },
          oa: function (a, b, c) {
            a = O.createNode(a, b, 41471, 0);
            a.link = c;
            return a;
          },
          ka: function (a) {
            if (40960 !== (a.mode & 61440)) throw new N(28);
            return a.link;
          },
        },
        T: {
          read: function (a, b, c, d, e) {
            var g = a.node.R;
            if (e >= a.node.U) return 0;
            a = Math.min(a.node.U - e, d);
            if (8 < a && g.subarray) b.set(g.subarray(e, e + a), c);
            else for (d = 0; d < a; d++) b[c + d] = g[e + d];
            return a;
          },
          write: function (a, b, c, d, e, g) {
            b.buffer === C.buffer && (g = !1);
            if (!d) return 0;
            a = a.node;
            a.timestamp = Date.now();
            if (b.subarray && (!a.R || a.R.subarray)) {
              if (g) return ((a.R = b.subarray(c, c + d)), (a.U = d));
              if (0 === a.U && 0 === e) return ((a.R = b.slice(c, c + d)), (a.U = d));
              if (e + d <= a.U) return (a.R.set(b.subarray(c, c + d), e), d);
            }
            O.xa(a, e + d);
            if (a.R.subarray && b.subarray) a.R.set(b.subarray(c, c + d), e);
            else for (g = 0; g < d; g++) a.R[e + g] = b[c + g];
            a.U = Math.max(a.U, e + d);
            return d;
          },
          da: function (a, b, c) {
            1 === c ? (b += a.position) : 2 === c && 32768 === (a.node.mode & 61440) && (b += a.node.U);
            if (0 > b) throw new N(28);
            return b;
          },
          va: function (a, b, c) {
            O.xa(a.node, b + c);
            a.node.U = Math.max(a.node.U, b + c);
          },
          Ba: function (a, b, c, d, e) {
            if (32768 !== (a.node.mode & 61440)) throw new N(43);
            a = a.node.R;
            if (e & 2 || a.buffer !== ma) {
              if (0 < c || c + b < a.length) a.subarray ? (a = a.subarray(c, c + b)) : (a = Array.prototype.slice.call(a, c, c + b));
              c = !0;
              x();
              b = void 0;
              if (!b) throw new N(48);
              C.set(a, b);
            } else ((c = !1), (b = a.byteOffset));
            return { ea: b, Za: c };
          },
          Da: function (a, b, c, d, e) {
            if (32768 !== (a.node.mode & 61440)) throw new N(43);
            if (e & 2) return 0;
            O.T.write(a, b, 0, d, c, !1);
            return 0;
          },
        },
      },
      Ta = null,
      Ua = {},
      P = [],
      Va = 1,
      Q = null,
      Wa = !0,
      N = null,
      Ra = {},
      R = (a, b = {}) => {
        a = Ha("/", a);
        if (!a) return { path: "", node: null };
        b = Object.assign({ za: !0, ta: 0 }, b);
        if (8 < b.ta) throw new N(32);
        a = Da(
          a.split("/").filter((l) => !!l),
          !1,
        );
        for (var c = Ta, d = "/", e = 0; e < a.length; e++) {
          var g = e === a.length - 1;
          if (g && b.parent) break;
          c = Sa(c, a[e]);
          d = M(d + "/" + a[e]);
          c.ma && (!g || (g && b.za)) && (c = c.ma.root);
          if (!g || b.ya) for (g = 0; 40960 === (c.mode & 61440); ) if (((c = Xa(d)), (d = Ha(Ea(d), c)), (c = R(d, { ta: b.ta + 1 }).node), 40 < g++)) throw new N(32);
        }
        return { path: d, node: c };
      },
      Ya = (a) => {
        for (var b; ; ) {
          if (a === a.parent) return ((a = a.$.Ca), b ? ("/" !== a[a.length - 1] ? a + "/" + b : a + b) : a);
          b = b ? a.name + "/" + b : a.name;
          a = a.parent;
        }
      },
      Za = (a, b) => {
        for (var c = 0, d = 0; d < b.length; d++) c = ((c << 5) - c + b.charCodeAt(d)) | 0;
        return ((a + c) >>> 0) % Q.length;
      },
      Sa = (a, b) => {
        var c;
        if ((c = (c = $a(a, "x")) ? c : a.S.ja ? 0 : 2)) throw new N(c, a);
        for (c = Q[Za(a.id, b)]; c; c = c.Ra) {
          var d = c.name;
          if (c.parent.id === a.id && d === b) return c;
        }
        return a.S.ja(a, b);
      },
      Qa = (a, b, c, d) => {
        a = new ab(a, b, c, d);
        b = Za(a.parent.id, a.name);
        a.Ra = Q[b];
        return (Q[b] = a);
      },
      bb = { r: 0, "r+": 2, w: 577, "w+": 578, a: 1089, "a+": 1090 },
      cb = (a) => {
        var b = ["r", "w", "rw"][a & 3];
        a & 512 && (b += "w");
        return b;
      },
      $a = (a, b) => {
        if (Wa) return 0;
        if (!b.includes("r") || a.mode & 292) {
          if ((b.includes("w") && !(a.mode & 146)) || (b.includes("x") && !(a.mode & 73))) return 2;
        } else return 2;
        return 0;
      },
      db = (a, b) => {
        try {
          return (Sa(a, b), 20);
        } catch (c) {}
        return $a(a, "wx");
      },
      eb = (a = 0) => {
        for (; 4096 >= a; a++) if (!P[a]) return a;
        throw new N(33);
      },
      gb = (a, b) => {
        fb ||
          ((fb = function () {
            this.ha = {};
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
                return this.ha.flags;
              },
              set: function (c) {
                this.ha.flags = c;
              },
            },
            position: {
              get: function () {
                return this.ha.position;
              },
              set: function (c) {
                this.ha.position = c;
              },
            },
          }));
        a = Object.assign(new fb(), a);
        b = eb(b);
        a.aa = b;
        return (P[b] = a);
      },
      Pa = {
        open: (a) => {
          a.T = Ua[a.node.na].T;
          a.T.open && a.T.open(a);
        },
        da: () => {
          throw new N(70);
        },
      },
      La = (a, b) => {
        Ua[a] = { T: b };
      },
      hb = (a, b) => {
        var c = "/" === b,
          d = !b;
        if (c && Ta) throw new N(10);
        if (!c && !d) {
          var e = R(b, { za: !1 });
          b = e.path;
          e = e.node;
          if (e.ma) throw new N(10);
          if (16384 !== (e.mode & 61440)) throw new N(54);
        }
        b = { type: a, jb: {}, Ca: b, Qa: [] };
        a = a.$(b);
        a.$ = b;
        b.root = a;
        c ? (Ta = a) : e && ((e.ma = b), e.$ && e.$.Qa.push(b));
      },
      S = (a, b, c) => {
        var d = R(a, { parent: !0 }).node;
        a = Fa(a);
        if (!a || "." === a || ".." === a) throw new N(28);
        var e = db(d, a);
        if (e) throw new N(e);
        if (!d.S.la) throw new N(63);
        return d.S.la(d, a, b, c);
      },
      ib = (a, b, c) => {
        "undefined" == typeof c && ((c = b), (b = 438));
        S(a, b | 8192, c);
      },
      jb = (a, b) => {
        if (!Ha(a)) throw new N(44);
        var c = R(b, { parent: !0 }).node;
        if (!c) throw new N(44);
        b = Fa(b);
        var d = db(c, b);
        if (d) throw new N(d);
        if (!c.S.oa) throw new N(63);
        c.S.oa(c, b, a);
      },
      Xa = (a) => {
        a = R(a).node;
        if (!a) throw new N(44);
        if (!a.S.ka) throw new N(28);
        return Ha(Ya(a.parent), a.S.ka(a));
      },
      lb = (a, b, c) => {
        if ("" === a) throw new N(44);
        if ("string" == typeof b) {
          var d = bb[b];
          if ("undefined" == typeof d) throw Error("Unknown file open mode: " + b);
          b = d;
        }
        c = b & 64 ? (("undefined" == typeof c ? 438 : c) & 4095) | 32768 : 0;
        if ("object" == typeof a) var e = a;
        else {
          a = M(a);
          try {
            e = R(a, { ya: !(b & 131072) }).node;
          } catch (g) {}
        }
        d = !1;
        if (b & 64)
          if (e) {
            if (b & 128) throw new N(20);
          } else ((e = S(a, c, 0)), (d = !0));
        if (!e) throw new N(44);
        8192 === (e.mode & 61440) && (b &= -513);
        if (b & 65536 && 16384 !== (e.mode & 61440)) throw new N(54);
        if (!d && (c = e ? (40960 === (e.mode & 61440) ? 32 : 16384 === (e.mode & 61440) && ("r" !== cb(b) || b & 512) ? 31 : $a(e, cb(b))) : 44)) throw new N(c);
        if (b & 512 && !d) {
          c = e;
          c = "string" == typeof c ? R(c, { ya: !0 }).node : c;
          if (!c.S.Y) throw new N(63);
          if (16384 === (c.mode & 61440)) throw new N(31);
          if (32768 !== (c.mode & 61440)) throw new N(28);
          if ((d = $a(c, "w"))) throw new N(d);
          c.S.Y(c, { size: 0, timestamp: Date.now() });
        }
        b &= -131713;
        e = gb({ node: e, path: Ya(e), flags: b, seekable: !0, position: 0, T: e.T, Ya: [], error: !1 });
        e.T.open && e.T.open(e);
        !h.logReadFiles || b & 1 || (kb || (kb = {}), a in kb || (kb[a] = 1));
        return e;
      },
      mb = (a, b, c) => {
        if (null === a.aa) throw new N(8);
        if (!a.seekable || !a.T.da) throw new N(70);
        if (0 != c && 1 != c && 2 != c) throw new N(28);
        a.position = a.T.da(a, b, c);
        a.Ya = [];
      },
      nb = () => {
        N ||
          ((N = function (a, b) {
            this.node = b;
            this.Ta = function (c) {
              this.ca = c;
            };
            this.Ta(a);
            this.message = "FS error";
          }),
          (N.prototype = Error()),
          (N.prototype.constructor = N),
          [44].forEach((a) => {
            Ra[a] = new N(a);
            Ra[a].stack = "<generic error, no stack>";
          }));
      },
      ob,
      pb = (a, b) => {
        var c = 0;
        a && (c |= 365);
        b && (c |= 146);
        return c;
      },
      rb = (a, b, c) => {
        a = M("/dev/" + a);
        var d = pb(!!b, !!c);
        qb || (qb = 64);
        var e = (qb++ << 8) | 0;
        La(e, {
          open: (g) => {
            g.seekable = !1;
          },
          close: () => {
            c && c.buffer && c.buffer.length && c(10);
          },
          read: (g, l, p, m) => {
            for (var q = 0, u = 0; u < m; u++) {
              try {
                var v = b();
              } catch (L) {
                throw new N(29);
              }
              if (void 0 === v && 0 === q) throw new N(6);
              if (null === v || void 0 === v) break;
              q++;
              l[p + u] = v;
            }
            q && (g.node.timestamp = Date.now());
            return q;
          },
          write: (g, l, p, m) => {
            for (var q = 0; q < m; q++)
              try {
                c(l[p + q]);
              } catch (u) {
                throw new N(29);
              }
            m && (g.node.timestamp = Date.now());
            return q;
          },
        });
        ib(a, d, e);
      },
      qb,
      T = {},
      fb,
      kb,
      sb = void 0;
    function U() {
      sb += 4;
      return D[(sb - 4) >> 2];
    }
    function V(a) {
      a = P[a];
      if (!a) throw new N(8);
      return a;
    }
    var tb = {};
    function ub() {
      if (!vb) {
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
        for (b in tb) void 0 === tb[b] ? delete a[b] : (a[b] = tb[b]);
        var c = [];
        for (b in a) c.push(b + "=" + a[b]);
        vb = c;
      }
      return vb;
    }
    var vb;
    function wb(a) {
      return 0 === a % 4 && (0 !== a % 100 || 0 === a % 400);
    }
    var xb = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
      yb = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    function zb(a, b, c, d) {
      function e(f, r, t) {
        for (f = "number" == typeof f ? f.toString() : f || ""; f.length < r; ) f = t[0] + f;
        return f;
      }
      function g(f, r) {
        return e(f, r, "0");
      }
      function l(f, r) {
        function t(E) {
          return 0 > E ? -1 : 0 < E ? 1 : 0;
        }
        var y;
        0 === (y = t(f.getFullYear() - r.getFullYear())) && 0 === (y = t(f.getMonth() - r.getMonth())) && (y = t(f.getDate() - r.getDate()));
        return y;
      }
      function p(f) {
        switch (f.getDay()) {
          case 0:
            return new Date(f.getFullYear() - 1, 11, 29);
          case 1:
            return f;
          case 2:
            return new Date(f.getFullYear(), 0, 3);
          case 3:
            return new Date(f.getFullYear(), 0, 2);
          case 4:
            return new Date(f.getFullYear(), 0, 1);
          case 5:
            return new Date(f.getFullYear() - 1, 11, 31);
          case 6:
            return new Date(f.getFullYear() - 1, 11, 30);
        }
      }
      function m(f) {
        var r = f.fa;
        for (f = new Date(new Date(f.ga + 1900, 0, 1).getTime()); 0 < r; ) {
          var t = f.getMonth(),
            y = (wb(f.getFullYear()) ? xb : yb)[t];
          if (r > y - f.getDate()) ((r -= y - f.getDate() + 1), f.setDate(1), 11 > t ? f.setMonth(t + 1) : (f.setMonth(0), f.setFullYear(f.getFullYear() + 1)));
          else {
            f.setDate(f.getDate() + r);
            break;
          }
        }
        t = new Date(f.getFullYear() + 1, 0, 4);
        r = p(new Date(f.getFullYear(), 0, 4));
        t = p(t);
        return 0 >= l(r, f) ? (0 >= l(t, f) ? f.getFullYear() + 1 : f.getFullYear()) : f.getFullYear() - 1;
      }
      var q = D[(d + 40) >> 2];
      d = {
        Wa: D[d >> 2],
        Va: D[(d + 4) >> 2],
        pa: D[(d + 8) >> 2],
        ua: D[(d + 12) >> 2],
        qa: D[(d + 16) >> 2],
        ga: D[(d + 20) >> 2],
        Z: D[(d + 24) >> 2],
        fa: D[(d + 28) >> 2],
        kb: D[(d + 32) >> 2],
        Ua: D[(d + 36) >> 2],
        Xa: q ? A(q) : "",
      };
      c = A(c);
      q = {
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
      for (var u in q) c = c.replace(new RegExp(u, "g"), q[u]);
      var v = "Sunday Monday Tuesday Wednesday Thursday Friday Saturday".split(" "),
        L = "January February March April May June July August September October November December".split(" ");
      q = {
        "%a": function (f) {
          return v[f.Z].substring(0, 3);
        },
        "%A": function (f) {
          return v[f.Z];
        },
        "%b": function (f) {
          return L[f.qa].substring(0, 3);
        },
        "%B": function (f) {
          return L[f.qa];
        },
        "%C": function (f) {
          return g(((f.ga + 1900) / 100) | 0, 2);
        },
        "%d": function (f) {
          return g(f.ua, 2);
        },
        "%e": function (f) {
          return e(f.ua, 2, " ");
        },
        "%g": function (f) {
          return m(f).toString().substring(2);
        },
        "%G": function (f) {
          return m(f);
        },
        "%H": function (f) {
          return g(f.pa, 2);
        },
        "%I": function (f) {
          f = f.pa;
          0 == f ? (f = 12) : 12 < f && (f -= 12);
          return g(f, 2);
        },
        "%j": function (f) {
          for (var r = 0, t = 0; t <= f.qa - 1; r += (wb(f.ga + 1900) ? xb : yb)[t++]);
          return g(f.ua + r, 3);
        },
        "%m": function (f) {
          return g(f.qa + 1, 2);
        },
        "%M": function (f) {
          return g(f.Va, 2);
        },
        "%n": function () {
          return "\n";
        },
        "%p": function (f) {
          return 0 <= f.pa && 12 > f.pa ? "AM" : "PM";
        },
        "%S": function (f) {
          return g(f.Wa, 2);
        },
        "%t": function () {
          return "\t";
        },
        "%u": function (f) {
          return f.Z || 7;
        },
        "%U": function (f) {
          return g(Math.floor((f.fa + 7 - f.Z) / 7), 2);
        },
        "%V": function (f) {
          var r = Math.floor((f.fa + 7 - ((f.Z + 6) % 7)) / 7);
          2 >= (f.Z + 371 - f.fa - 2) % 7 && r++;
          if (r) 53 == r && ((t = (f.Z + 371 - f.fa) % 7), 4 == t || (3 == t && wb(f.ga)) || (r = 1));
          else {
            r = 52;
            var t = (f.Z + 7 - f.fa - 1) % 7;
            (4 == t || (5 == t && wb((f.ga % 400) - 1))) && r++;
          }
          return g(r, 2);
        },
        "%w": function (f) {
          return f.Z;
        },
        "%W": function (f) {
          return g(Math.floor((f.fa + 7 - ((f.Z + 6) % 7)) / 7), 2);
        },
        "%y": function (f) {
          return (f.ga + 1900).toString().substring(2);
        },
        "%Y": function (f) {
          return f.ga + 1900;
        },
        "%z": function (f) {
          f = f.Ua;
          var r = 0 <= f;
          f = Math.abs(f) / 60;
          return (r ? "+" : "-") + String("0000" + ((f / 60) * 100 + (f % 60))).slice(-4);
        },
        "%Z": function (f) {
          return f.Xa;
        },
        "%%": function () {
          return "%";
        },
      };
      c = c.replace(/%%/g, "\x00\x00");
      for (u in q) c.includes(u) && (c = c.replace(new RegExp(u, "g"), q[u](d)));
      c = c.replace(/\0\0/g, "%");
      u = Ia(c, !1);
      if (u.length > b) return 0;
      C.set(u, a);
      return u.length - 1;
    }
    var W = void 0,
      Ab = [];
    function Bb(a, b, c, d) {
      var e = {
        string: (q) => {
          var u = 0;
          if (null !== q && void 0 !== q && 0 !== q) {
            var v = (q.length << 2) + 1;
            u = Cb(v);
            la(q, B, u, v);
          }
          return u;
        },
        array: (q) => {
          var u = Cb(q.length);
          C.set(q, u);
          return u;
        },
      };
      a = h["_" + a];
      var g = [],
        l = 0;
      if (d)
        for (var p = 0; p < d.length; p++) {
          var m = e[c[p]];
          m ? (0 === l && (l = X()), (g[p] = m(d[p]))) : (g[p] = d[p]);
        }
      c = a.apply(null, g);
      return (c = (function (q) {
        0 !== l && Y(l);
        return "string" === b ? A(q) : "boolean" === b ? !!q : q;
      })(c));
    }
    function ab(a, b, c, d) {
      a || (a = this);
      this.parent = a;
      this.$ = a.$;
      this.ma = null;
      this.id = Va++;
      this.name = b;
      this.mode = c;
      this.S = {};
      this.T = {};
      this.na = d;
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
    });
    nb();
    Q = Array(4096);
    hb(O, "/");
    S("/tmp", 16895, 0);
    S("/home", 16895, 0);
    S("/home/web_user", 16895, 0);
    (() => {
      S("/dev", 16895, 0);
      La(259, { read: () => 0, write: (b, c, d, e) => e });
      ib("/dev/null", 259);
      Ka(1280, Na);
      Ka(1536, Oa);
      ib("/dev/tty", 1280);
      ib("/dev/tty1", 1536);
      var a = Ga();
      rb("random", a);
      rb("urandom", a);
      S("/dev/shm", 16895, 0);
      S("/dev/shm/tmp", 16895, 0);
    })();
    (() => {
      S("/proc", 16895, 0);
      var a = S("/proc/self", 16895, 0);
      S("/proc/self/fd", 16895, 0);
      hb(
        {
          $: () => {
            var b = Qa(a, "fd", 16895, 73);
            b.S = {
              ja: (c, d) => {
                var e = P[+d];
                if (!e) throw new N(8);
                c = { parent: null, $: { Ca: "fake" }, S: { ka: () => e.path } };
                return (c.parent = c);
              },
            };
            return b;
          },
        },
        "/proc/self/fd",
      );
    })();
    var Ob = {
      c: function (a) {
        return Db(a + 24) + 24;
      },
      d: function (a, b, c) {
        new Ba(a).ha(b, c);
        Ca++;
        throw a;
      },
      o: function (a, b, c) {
        sb = c;
        try {
          var d = V(a);
          switch (b) {
            case 0:
              var e = U();
              return 0 > e ? -28 : gb(d, e).aa;
            case 1:
            case 2:
              return 0;
            case 3:
              return d.flags;
            case 4:
              return ((e = U()), (d.flags |= e), 0);
            case 5:
              return ((e = U()), (na[(e + 0) >> 1] = 2), 0);
            case 6:
            case 7:
              return 0;
            case 16:
            case 8:
              return -28;
            case 9:
              return ((D[Eb() >> 2] = 28), -1);
            default:
              return -28;
          }
        } catch (g) {
          if ("undefined" == typeof T || !(g instanceof N)) throw g;
          return -g.ca;
        }
      },
      x: function (a, b, c) {
        sb = c;
        try {
          var d = V(a);
          switch (b) {
            case 21509:
            case 21505:
              return d.V ? 0 : -59;
            case 21510:
            case 21511:
            case 21512:
            case 21506:
            case 21507:
            case 21508:
              return d.V ? 0 : -59;
            case 21519:
              if (!d.V) return -59;
              var e = U();
              return (D[e >> 2] = 0);
            case 21520:
              return d.V ? -28 : -59;
            case 21531:
              a = e = U();
              if (!d.T.Pa) throw new N(59);
              return d.T.Pa(d, b, a);
            case 21523:
              return d.V ? 0 : -59;
            case 21524:
              return d.V ? 0 : -59;
            default:
              return -28;
          }
        } catch (g) {
          if ("undefined" == typeof T || !(g instanceof N)) throw g;
          return -g.ca;
        }
      },
      y: function (a, b, c, d) {
        sb = d;
        try {
          b = A(b);
          var e = b;
          if ("/" === e.charAt(0)) b = e;
          else {
            if (-100 === a) var g = "/";
            else {
              var l = P[a];
              if (!l) throw new N(8);
              g = l.path;
            }
            if (0 == e.length) throw new N(44);
            b = M(g + "/" + e);
          }
          var p = d ? U() : 0;
          return lb(b, c, p).aa;
        } catch (m) {
          if ("undefined" == typeof T || !(m instanceof N)) throw m;
          return -m.ca;
        }
      },
      A: function () {
        return !0;
      },
      r: function () {
        throw Infinity;
      },
      l: function () {
        x("");
      },
      s: function () {
        return 536870912;
      },
      z: () => performance.now(),
      m: function (a) {
        var b = B.length;
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
              var g = 1;
              break a;
            } catch (l) {}
            g = void 0;
          }
          if (g) return !0;
        }
        return !1;
      },
      t: function (a, b) {
        var c = 0;
        ub().forEach(function (d, e) {
          var g = b + c;
          e = F[(a + 4 * e) >> 2] = g;
          for (g = 0; g < d.length; ++g) C[e++ >> 0] = d.charCodeAt(g);
          C[e >> 0] = 0;
          c += d.length + 1;
        });
        return 0;
      },
      u: function (a, b) {
        var c = ub();
        F[a >> 2] = c.length;
        var d = 0;
        c.forEach(function (e) {
          d += e.length + 1;
        });
        F[b >> 2] = d;
        return 0;
      },
      B: function (a) {
        if (!noExitRuntime) {
          if (h.onExit) h.onExit(a);
          ja = !0;
        }
        ea(a, new za(a));
      },
      n: function (a) {
        try {
          var b = V(a);
          if (null === b.aa) throw new N(8);
          b.ra && (b.ra = null);
          try {
            b.T.close && b.T.close(b);
          } catch (c) {
            throw c;
          } finally {
            P[b.aa] = null;
          }
          b.aa = null;
          return 0;
        } catch (c) {
          if ("undefined" == typeof T || !(c instanceof N)) throw c;
          return c.ca;
        }
      },
      w: function (a, b, c, d) {
        try {
          a: {
            var e = V(a);
            a = b;
            for (var g = (b = 0); g < c; g++) {
              var l = F[a >> 2],
                p = F[(a + 4) >> 2];
              a += 8;
              var m = e,
                q = l,
                u = p,
                v = void 0,
                L = C;
              if (0 > u || 0 > v) throw new N(28);
              if (null === m.aa) throw new N(8);
              if (1 === (m.flags & 2097155)) throw new N(8);
              if (16384 === (m.node.mode & 61440)) throw new N(31);
              if (!m.T.read) throw new N(28);
              var f = "undefined" != typeof v;
              if (!f) v = m.position;
              else if (!m.seekable) throw new N(70);
              var r = m.T.read(m, L, q, u, v);
              f || (m.position += r);
              var t = r;
              if (0 > t) {
                var y = -1;
                break a;
              }
              b += t;
              if (t < p) break;
            }
            y = b;
          }
          D[d >> 2] = y;
          return 0;
        } catch (E) {
          if ("undefined" == typeof T || !(E instanceof N)) throw E;
          return E.ca;
        }
      },
      p: function (a, b, c, d, e) {
        try {
          b = (c + 2097152) >>> 0 < 4194305 - !!b ? (b >>> 0) + 4294967296 * c : NaN;
          if (isNaN(b)) return 61;
          var g = V(a);
          mb(g, b, d);
          ya = [
            g.position >>> 0,
            ((K = g.position), 1 <= +Math.abs(K) ? (0 < K ? (Math.min(+Math.floor(K / 4294967296), 4294967295) | 0) >>> 0 : ~~+Math.ceil((K - +(~~K >>> 0)) / 4294967296) >>> 0) : 0),
          ];
          D[e >> 2] = ya[0];
          D[(e + 4) >> 2] = ya[1];
          g.ra && 0 === b && 0 === d && (g.ra = null);
          return 0;
        } catch (l) {
          if ("undefined" == typeof T || !(l instanceof N)) throw l;
          return l.ca;
        }
      },
      v: function (a, b, c, d) {
        try {
          a: {
            var e = V(a);
            a = b;
            for (var g = (b = 0); g < c; g++) {
              var l = F[a >> 2],
                p = F[(a + 4) >> 2];
              a += 8;
              var m = e,
                q = l,
                u = p,
                v = void 0,
                L = C;
              if (0 > u || 0 > v) throw new N(28);
              if (null === m.aa) throw new N(8);
              if (0 === (m.flags & 2097155)) throw new N(8);
              if (16384 === (m.node.mode & 61440)) throw new N(31);
              if (!m.T.write) throw new N(28);
              m.seekable && m.flags & 1024 && mb(m, 0, 2);
              var f = "undefined" != typeof v;
              if (!f) v = m.position;
              else if (!m.seekable) throw new N(70);
              var r = m.T.write(m, L, q, u, v, void 0);
              f || (m.position += r);
              var t = r;
              if (0 > t) {
                var y = -1;
                break a;
              }
              b += t;
            }
            y = b;
          }
          F[d >> 2] = y;
          return 0;
        } catch (E) {
          if ("undefined" == typeof T || !(E instanceof N)) throw E;
          return E.ca;
        }
      },
      a: function () {
        return ha;
      },
      f: Fb,
      i: Gb,
      g: Hb,
      D: Ib,
      e: Jb,
      h: Kb,
      j: Lb,
      k: Mb,
      C: Nb,
      b: function (a) {
        ha = a;
      },
      q: function (a, b, c, d) {
        return zb(a, b, c, d);
      },
    };
    (function () {
      function a(e) {
        h.asm = e.exports;
        ia = h.asm.E;
        oa();
        G = h.asm.J;
        qa.unshift(h.asm.F);
        H--;
        h.monitorRunDependencies && h.monitorRunDependencies(H);
        0 == H && (null !== ta && (clearInterval(ta), (ta = null)), I && ((e = I), (I = null), e()));
      }
      function b(e) {
        a(e.instance);
      }
      function c(e) {
        return xa()
          .then(function (g) {
            return WebAssembly.instantiate(g, d);
          })
          .then(function (g) {
            return g;
          })
          .then(e, function (g) {
            n("failed to asynchronously prepare wasm: " + g);
            x(g);
          });
      }
      var d = { a: Ob };
      H++;
      h.monitorRunDependencies && h.monitorRunDependencies(H);
      if (h.instantiateWasm)
        try {
          return h.instantiateWasm(d, a);
        } catch (e) {
          return (n("Module.instantiateWasm callback failed with error: " + e), !1);
        }
      (function () {
        return w || "function" != typeof WebAssembly.instantiateStreaming || ua() || "function" != typeof fetch
          ? c(b)
          : fetch(J, { credentials: "same-origin" }).then(function (e) {
              return WebAssembly.instantiateStreaming(e, d).then(b, function (g) {
                n("wasm streaming compile failed: " + g);
                n("falling back to ArrayBuffer instantiation");
                return c(b);
              });
            });
      })().catch(ba);
      return {};
    })();
    h.___wasm_call_ctors = function () {
      return (h.___wasm_call_ctors = h.asm.F).apply(null, arguments);
    };
    h._wrap_function = function () {
      return (h._wrap_function = h.asm.G).apply(null, arguments);
    };
    var Db = (h._malloc = function () {
      return (Db = h._malloc = h.asm.H).apply(null, arguments);
    });
    h._doc_affine_function = function () {
      return (h._doc_affine_function = h.asm.I).apply(null, arguments);
    };
    h._free = function () {
      return (h._free = h.asm.K).apply(null, arguments);
    };
    var Eb = (h.___errno_location = function () {
        return (Eb = h.___errno_location = h.asm.L).apply(null, arguments);
      }),
      Z = (h._setThrew = function () {
        return (Z = h._setThrew = h.asm.M).apply(null, arguments);
      }),
      X = (h.stackSave = function () {
        return (X = h.stackSave = h.asm.N).apply(null, arguments);
      }),
      Y = (h.stackRestore = function () {
        return (Y = h.stackRestore = h.asm.O).apply(null, arguments);
      }),
      Cb = (h.stackAlloc = function () {
        return (Cb = h.stackAlloc = h.asm.P).apply(null, arguments);
      });
    h.___cxa_is_pointer_type = function () {
      return (h.___cxa_is_pointer_type = h.asm.Q).apply(null, arguments);
    };
    function Jb(a, b) {
      var c = X();
      try {
        G.get(a)(b);
      } catch (d) {
        Y(c);
        if (d !== d + 0) throw d;
        Z(1, 0);
      }
    }
    function Fb(a, b) {
      var c = X();
      try {
        return G.get(a)(b);
      } catch (d) {
        Y(c);
        if (d !== d + 0) throw d;
        Z(1, 0);
      }
    }
    function Lb(a, b, c, d) {
      var e = X();
      try {
        G.get(a)(b, c, d);
      } catch (g) {
        Y(e);
        if (g !== g + 0) throw g;
        Z(1, 0);
      }
    }
    function Gb(a, b, c) {
      var d = X();
      try {
        return G.get(a)(b, c);
      } catch (e) {
        Y(d);
        if (e !== e + 0) throw e;
        Z(1, 0);
      }
    }
    function Kb(a, b, c) {
      var d = X();
      try {
        G.get(a)(b, c);
      } catch (e) {
        Y(d);
        if (e !== e + 0) throw e;
        Z(1, 0);
      }
    }
    function Hb(a, b, c, d) {
      var e = X();
      try {
        return G.get(a)(b, c, d);
      } catch (g) {
        Y(e);
        if (g !== g + 0) throw g;
        Z(1, 0);
      }
    }
    function Ib(a, b, c, d, e) {
      var g = X();
      try {
        return G.get(a)(b, c, d, e);
      } catch (l) {
        Y(g);
        if (l !== l + 0) throw l;
        Z(1, 0);
      }
    }
    function Mb(a, b, c, d, e, g) {
      var l = X();
      try {
        G.get(a)(b, c, d, e, g);
      } catch (p) {
        Y(l);
        if (p !== p + 0) throw p;
        Z(1, 0);
      }
    }
    function Nb(a, b, c, d, e, g, l) {
      var p = X();
      try {
        G.get(a)(b, c, d, e, g, l);
      } catch (m) {
        Y(p);
        if (m !== m + 0) throw m;
        Z(1, 0);
      }
    }
    h.UTF8ToString = A;
    h.ccall = Bb;
    h.cwrap = function (a, b, c, d) {
      c = c || [];
      var e = c.every((g) => "number" === g || "boolean" === g);
      return "string" !== b && e && !d
        ? h["_" + a]
        : function () {
            return Bb(a, b, c, arguments, d);
          };
    };
    h.addFunction = function (a, b) {
      if (!W) {
        W = new WeakMap();
        var c = G.length;
        if (W)
          for (var d = 0; d < 0 + c; d++) {
            var e = G.get(d);
            e && W.set(e, d);
          }
      }
      if (W.has(a)) return W.get(a);
      if (Ab.length) c = Ab.pop();
      else {
        try {
          G.grow(1);
        } catch (p) {
          if (!(p instanceof RangeError)) throw p;
          throw "Unable to grow wasm table. Set ALLOW_TABLE_GROWTH.";
        }
        c = G.length - 1;
      }
      try {
        G.set(c, a);
      } catch (p) {
        if (!(p instanceof TypeError)) throw p;
        if ("function" == typeof WebAssembly.Function) {
          d = WebAssembly.Function;
          e = { i: "i32", j: "i64", f: "f32", d: "f64", p: "i32" };
          for (var g = { parameters: [], results: "v" == b[0] ? [] : [e[b[0]]] }, l = 1; l < b.length; ++l) g.parameters.push(e[b[l]]);
          b = new d(g, a);
        } else {
          d = [1, 96];
          e = b.slice(0, 1);
          b = b.slice(1);
          g = { i: 127, p: 127, j: 126, f: 125, d: 124 };
          l = b.length;
          128 > l ? d.push(l) : d.push((l % 128) | 128, l >> 7);
          for (l = 0; l < b.length; ++l) d.push(g[b[l]]);
          "v" == e ? d.push(0) : d.push(1, g[e]);
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
      W.set(a, c);
      return c;
    };
    h.AsciiToString = function (a) {
      for (var b = ""; ; ) {
        var c = B[a++ >> 0];
        if (!c) return b;
        b += String.fromCharCode(c);
      }
    };
    var Pb;
    I = function Qb() {
      Pb || Rb();
      Pb || (I = Qb);
    };
    function Rb() {
      function a() {
        if (!Pb && ((Pb = !0), (h.calledRun = !0), !ja)) {
          h.noFSInit ||
            ob ||
            ((ob = !0),
            nb(),
            (h.stdin = h.stdin),
            (h.stdout = h.stdout),
            (h.stderr = h.stderr),
            h.stdin ? rb("stdin", h.stdin) : jb("/dev/tty", "/dev/stdin"),
            h.stdout ? rb("stdout", null, h.stdout) : jb("/dev/tty", "/dev/stdout"),
            h.stderr ? rb("stderr", null, h.stderr) : jb("/dev/tty1", "/dev/stderr"),
            lb("/dev/stdin", 0),
            lb("/dev/stdout", 1),
            lb("/dev/stderr", 1));
          Wa = !1;
          Aa(qa);
          aa(h);
          if (h.onRuntimeInitialized) h.onRuntimeInitialized();
          if (h.postRun)
            for ("function" == typeof h.postRun && (h.postRun = [h.postRun]); h.postRun.length; ) {
              var b = h.postRun.shift();
              ra.unshift(b);
            }
          Aa(ra);
        }
      }
      if (!(0 < H)) {
        if (h.preRun) for ("function" == typeof h.preRun && (h.preRun = [h.preRun]); h.preRun.length; ) sa();
        Aa(pa);
        0 < H ||
          (h.setStatus
            ? (h.setStatus("Running..."),
              setTimeout(function () {
                setTimeout(function () {
                  h.setStatus("");
                }, 1);
                a();
              }, 1))
            : a());
      }
    }
    if (h.preInit) for ("function" == typeof h.preInit && (h.preInit = [h.preInit]); 0 < h.preInit.length; ) h.preInit.pop()();
    Rb();

    return affineModule.ready;
  };
})();
if (typeof exports === "object" && typeof module === "object") module.exports = affineModule;
else if (typeof define === "function" && define["amd"])
  define([], function () {
    return affineModule;
  });
else if (typeof exports === "object") exports["affineModule"] = affineModule;
