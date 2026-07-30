var koiTiff = (() => {
  var _scriptDir = typeof document !== "undefined" && document.currentScript ? document.currentScript.src : undefined;

  return function (koiTiff) {
    koiTiff = koiTiff || {};

    var b;
    b || (b = typeof koiTiff !== "undefined" ? koiTiff : {});
    var m, q;
    b.ready = new Promise(function (a, c) {
      m = a;
      q = c;
    });
    var t = Object.assign({}, b),
      u = "./this.program",
      v = (a, c) => {
        throw c;
      },
      w = "";
    "undefined" != typeof document && document.currentScript && (w = document.currentScript.src);
    _scriptDir && (w = _scriptDir);
    0 !== w.indexOf("blob:") ? (w = w.substr(0, w.replace(/[?#].*/, "").lastIndexOf("/") + 1)) : (w = "");
    var aa = b.print || console.log.bind(console),
      z = b.printErr || console.warn.bind(console);
    Object.assign(b, t);
    t = null;
    b.thisProgram && (u = b.thisProgram);
    b.quit && (v = b.quit);
    var A = 0,
      B;
    b.wasmBinary && (B = b.wasmBinary);
    var noExitRuntime = b.noExitRuntime || !0;
    "object" != typeof WebAssembly && C("no native wasm support detected");
    var D,
      E = !1,
      G = "undefined" != typeof TextDecoder ? new TextDecoder("utf8") : void 0;
    function H(a, c, f) {
      var e = c + f;
      for (f = c; a[f] && !(f >= e); ) ++f;
      if (16 < f - c && a.buffer && G) return G.decode(a.subarray(c, f));
      for (e = ""; c < f; ) {
        var d = a[c++];
        if (d & 128) {
          var g = a[c++] & 63;
          if (192 == (d & 224)) e += String.fromCharCode(((d & 31) << 6) | g);
          else {
            var h = a[c++] & 63;
            d = 224 == (d & 240) ? ((d & 15) << 12) | (g << 6) | h : ((d & 7) << 18) | (g << 12) | (h << 6) | (a[c++] & 63);
            65536 > d ? (e += String.fromCharCode(d)) : ((d -= 65536), (e += String.fromCharCode(55296 | (d >> 10), 56320 | (d & 1023))));
          }
        } else e += String.fromCharCode(d);
      }
      return e;
    }
    function ba(a, c) {
      return a ? H(I, a, c) : "";
    }
    var ca, K, I, da, L;
    function ea() {
      var a = D.buffer;
      ca = a;
      b.HEAP8 = K = new Int8Array(a);
      b.HEAP16 = new Int16Array(a);
      b.HEAP32 = da = new Int32Array(a);
      b.HEAPU8 = I = new Uint8Array(a);
      b.HEAPU16 = new Uint16Array(a);
      b.HEAPU32 = L = new Uint32Array(a);
      b.HEAPF32 = new Float32Array(a);
      b.HEAPF64 = new Float64Array(a);
    }
    var M,
      fa = [],
      ha = [],
      ia = [];
    function ja() {
      var a = b.preRun.shift();
      fa.unshift(a);
    }
    var N = 0,
      O = null,
      P = null;
    function C(a) {
      if (b.onAbort) b.onAbort(a);
      a = "Aborted(" + a + ")";
      z(a);
      E = !0;
      a = new WebAssembly.RuntimeError(a + ". Build with -sASSERTIONS for more info.");
      q(a);
      throw a;
    }
    function ka() {
      return Q.startsWith("data:application/octet-stream;base64,");
    }
    var Q;
    Q = "../../detect/tiff/koi_tiff.wasm";
    if (!ka()) {
      var la = Q;
      Q = b.locateFile ? b.locateFile(la, w) : w + la;
    }
    function ma() {
      var a = Q;
      try {
        if (a == Q && B) return new Uint8Array(B);
        throw "both async and sync fetching of the wasm failed";
      } catch (c) {
        C(c);
      }
    }
    function na() {
      return B || "function" != typeof fetch
        ? Promise.resolve().then(function () {
            return ma();
          })
        : fetch(Q, { credentials: "same-origin" })
            .then(function (a) {
              if (!a.ok) throw "failed to load wasm binary file at '" + Q + "'";
              return a.arrayBuffer();
            })
            .catch(function () {
              return ma();
            });
    }
    function oa(a) {
      this.name = "ExitStatus";
      this.message = "Program terminated with exit(" + a + ")";
      this.status = a;
    }
    function R(a) {
      for (; 0 < a.length; ) a.shift()(b);
    }
    function pa(a) {
      this.G = a - 24;
      this.N = function (c) {
        L[(this.G + 4) >> 2] = c;
      };
      this.K = function (c) {
        L[(this.G + 8) >> 2] = c;
      };
      this.L = function () {
        da[this.G >> 2] = 0;
      };
      this.J = function () {
        K[(this.G + 12) >> 0] = 0;
      };
      this.M = function () {
        K[(this.G + 13) >> 0] = 0;
      };
      this.H = function (c, f) {
        this.I();
        this.N(c);
        this.K(f);
        this.L();
        this.J();
        this.M();
      };
      this.I = function () {
        L[(this.G + 16) >> 2] = 0;
      };
    }
    var qa = 0,
      S = {};
    function ra() {
      if (!T) {
        var a = {
            USER: "web_user",
            LOGNAME: "web_user",
            PATH: "/",
            PWD: "/",
            HOME: "/home/web_user",
            LANG: (("object" == typeof navigator && navigator.languages && navigator.languages[0]) || "C").replace("-", "_") + ".UTF-8",
            _: u || "./this.program",
          },
          c;
        for (c in S) void 0 === S[c] ? delete a[c] : (a[c] = S[c]);
        var f = [];
        for (c in a) f.push(c + "=" + a[c]);
        T = f;
      }
      return T;
    }
    var T,
      sa = [null, [], []],
      U = void 0,
      ta = [];
    function ua(a, c, f, e) {
      var d = {
        string: (k) => {
          var r = 0;
          if (null !== k && void 0 !== k && 0 !== k) {
            var x = (k.length << 2) + 1;
            r = V(x);
            var p = r,
              y = I;
            if (0 < x) {
              x = p + x - 1;
              for (var J = 0; J < k.length; ++J) {
                var n = k.charCodeAt(J);
                if (55296 <= n && 57343 >= n) {
                  var xa = k.charCodeAt(++J);
                  n = (65536 + ((n & 1023) << 10)) | (xa & 1023);
                }
                if (127 >= n) {
                  if (p >= x) break;
                  y[p++] = n;
                } else {
                  if (2047 >= n) {
                    if (p + 1 >= x) break;
                    y[p++] = 192 | (n >> 6);
                  } else {
                    if (65535 >= n) {
                      if (p + 2 >= x) break;
                      y[p++] = 224 | (n >> 12);
                    } else {
                      if (p + 3 >= x) break;
                      y[p++] = 240 | (n >> 18);
                      y[p++] = 128 | ((n >> 12) & 63);
                    }
                    y[p++] = 128 | ((n >> 6) & 63);
                  }
                  y[p++] = 128 | (n & 63);
                }
              }
              y[p] = 0;
            }
          }
          return r;
        },
        array: (k) => {
          var r = V(k.length);
          K.set(k, r);
          return r;
        },
      };
      a = b["_" + a];
      var g = [],
        h = 0;
      if (e)
        for (var l = 0; l < e.length; l++) {
          var F = d[f[l]];
          F ? (0 === h && (h = W()), (g[l] = F(e[l]))) : (g[l] = e[l]);
        }
      f = a.apply(null, g);
      return (f = (function (k) {
        0 !== h && X(h);
        return "string" === c ? ba(k) : "boolean" === c ? !!k : k;
      })(f));
    }
    var Ea = {
      t: function (a) {
        return va(a + 24) + 24;
      },
      s: function (a, c, f) {
        new pa(a).H(c, f);
        qa++;
        throw a;
      },
      m: function () {
        throw Infinity;
      },
      l: function () {
        C("");
      },
      g: function (a) {
        var c = I.length;
        a >>>= 0;
        if (536870912 < a) return !1;
        for (var f = 1; 4 >= f; f *= 2) {
          var e = c * (1 + 0.2 / f);
          e = Math.min(e, a + 100663296);
          var d = Math;
          e = Math.max(a, e);
          d = d.min.call(d, 536870912, e + ((65536 - (e % 65536)) % 65536));
          a: {
            try {
              D.grow((d - ca.byteLength + 65535) >>> 16);
              ea();
              var g = 1;
              break a;
            } catch (h) {}
            g = void 0;
          }
          if (g) return !0;
        }
        return !1;
      },
      o: function (a, c) {
        var f = 0;
        ra().forEach(function (e, d) {
          var g = c + f;
          d = L[(a + 4 * d) >> 2] = g;
          for (g = 0; g < e.length; ++g) K[d++ >> 0] = e.charCodeAt(g);
          K[d >> 0] = 0;
          f += e.length + 1;
        });
        return 0;
      },
      p: function (a, c) {
        var f = ra();
        L[a >> 2] = f.length;
        var e = 0;
        f.forEach(function (d) {
          e += d.length + 1;
        });
        L[c >> 2] = e;
        return 0;
      },
      r: function (a) {
        if (!noExitRuntime) {
          if (b.onExit) b.onExit(a);
          E = !0;
        }
        v(a, new oa(a));
      },
      q: function () {
        return 52;
      },
      k: function () {
        return 70;
      },
      n: function (a, c, f, e) {
        for (var d = 0, g = 0; g < f; g++) {
          var h = L[c >> 2],
            l = L[(c + 4) >> 2];
          c += 8;
          for (var F = 0; F < l; F++) {
            var k = I[h + F],
              r = sa[a];
            0 === k || 10 === k ? ((1 === a ? aa : z)(H(r, 0)), (r.length = 0)) : r.push(k);
          }
          d += l;
        }
        L[e >> 2] = d;
        return 0;
      },
      a: function () {
        return A;
      },
      c: wa,
      j: ya,
      e: za,
      i: Aa,
      d: Ba,
      h: Ca,
      f: Da,
      b: function (a) {
        A = a;
      },
    };
    (function () {
      function a(d) {
        b.asm = d.exports;
        D = b.asm.u;
        ea();
        M = b.asm.y;
        ha.unshift(b.asm.v);
        N--;
        b.monitorRunDependencies && b.monitorRunDependencies(N);
        0 == N && (null !== O && (clearInterval(O), (O = null)), P && ((d = P), (P = null), d()));
      }
      function c(d) {
        a(d.instance);
      }
      function f(d) {
        return na()
          .then(function (g) {
            return WebAssembly.instantiate(g, e);
          })
          .then(function (g) {
            return g;
          })
          .then(d, function (g) {
            z("failed to asynchronously prepare wasm: " + g);
            C(g);
          });
      }
      var e = { a: Ea };
      N++;
      b.monitorRunDependencies && b.monitorRunDependencies(N);
      if (b.instantiateWasm)
        try {
          return b.instantiateWasm(e, a);
        } catch (d) {
          return (z("Module.instantiateWasm callback failed with error: " + d), !1);
        }
      (function () {
        return B || "function" != typeof WebAssembly.instantiateStreaming || ka() || "function" != typeof fetch
          ? f(c)
          : fetch(Q, { credentials: "same-origin" }).then(function (d) {
              return WebAssembly.instantiateStreaming(d, e).then(c, function (g) {
                z("wasm streaming compile failed: " + g);
                z("falling back to ArrayBuffer instantiation");
                return f(c);
              });
            });
      })().catch(q);
      return {};
    })();
    b.___wasm_call_ctors = function () {
      return (b.___wasm_call_ctors = b.asm.v).apply(null, arguments);
    };
    b._pdf_quality = function () {
      return (b._pdf_quality = b.asm.w).apply(null, arguments);
    };
    b._generate_tiff_from_images = function () {
      return (b._generate_tiff_from_images = b.asm.x).apply(null, arguments);
    };
    var va = (b._malloc = function () {
      return (va = b._malloc = b.asm.z).apply(null, arguments);
    });
    b._free = function () {
      return (b._free = b.asm.A).apply(null, arguments);
    };
    var Y = (b._setThrew = function () {
        return (Y = b._setThrew = b.asm.B).apply(null, arguments);
      }),
      W = (b.stackSave = function () {
        return (W = b.stackSave = b.asm.C).apply(null, arguments);
      }),
      X = (b.stackRestore = function () {
        return (X = b.stackRestore = b.asm.D).apply(null, arguments);
      }),
      V = (b.stackAlloc = function () {
        return (V = b.stackAlloc = b.asm.E).apply(null, arguments);
      });
    b.___cxa_is_pointer_type = function () {
      return (b.___cxa_is_pointer_type = b.asm.F).apply(null, arguments);
    };
    function wa(a, c) {
      var f = W();
      try {
        return M.get(a)(c);
      } catch (e) {
        X(f);
        if (e !== e + 0) throw e;
        Y(1, 0);
      }
    }
    function Da(a, c, f, e) {
      var d = W();
      try {
        M.get(a)(c, f, e);
      } catch (g) {
        X(d);
        if (g !== g + 0) throw g;
        Y(1, 0);
      }
    }
    function ya(a, c, f) {
      var e = W();
      try {
        return M.get(a)(c, f);
      } catch (d) {
        X(e);
        if (d !== d + 0) throw d;
        Y(1, 0);
      }
    }
    function Ba(a, c) {
      var f = W();
      try {
        M.get(a)(c);
      } catch (e) {
        X(f);
        if (e !== e + 0) throw e;
        Y(1, 0);
      }
    }
    function za(a, c, f, e) {
      var d = W();
      try {
        return M.get(a)(c, f, e);
      } catch (g) {
        X(d);
        if (g !== g + 0) throw g;
        Y(1, 0);
      }
    }
    function Aa(a, c, f, e, d) {
      var g = W();
      try {
        return M.get(a)(c, f, e, d);
      } catch (h) {
        X(g);
        if (h !== h + 0) throw h;
        Y(1, 0);
      }
    }
    function Ca(a, c, f) {
      var e = W();
      try {
        M.get(a)(c, f);
      } catch (d) {
        X(e);
        if (d !== d + 0) throw d;
        Y(1, 0);
      }
    }
    b.UTF8ToString = ba;
    b.ccall = ua;
    b.cwrap = function (a, c, f, e) {
      f = f || [];
      var d = f.every((g) => "number" === g || "boolean" === g);
      return "string" !== c && d && !e
        ? b["_" + a]
        : function () {
            return ua(a, c, f, arguments, e);
          };
    };
    b.addFunction = function (a, c) {
      if (!U) {
        U = new WeakMap();
        var f = M.length;
        if (U)
          for (var e = 0; e < 0 + f; e++) {
            var d = M.get(e);
            d && U.set(d, e);
          }
      }
      if (U.has(a)) return U.get(a);
      if (ta.length) f = ta.pop();
      else {
        try {
          M.grow(1);
        } catch (l) {
          if (!(l instanceof RangeError)) throw l;
          throw "Unable to grow wasm table. Set ALLOW_TABLE_GROWTH.";
        }
        f = M.length - 1;
      }
      try {
        M.set(f, a);
      } catch (l) {
        if (!(l instanceof TypeError)) throw l;
        if ("function" == typeof WebAssembly.Function) {
          e = WebAssembly.Function;
          d = { i: "i32", j: "i64", f: "f32", d: "f64", p: "i32" };
          for (var g = { parameters: [], results: "v" == c[0] ? [] : [d[c[0]]] }, h = 1; h < c.length; ++h) g.parameters.push(d[c[h]]);
          c = new e(g, a);
        } else {
          e = [1, 96];
          d = c.slice(0, 1);
          c = c.slice(1);
          g = { i: 127, p: 127, j: 126, f: 125, d: 124 };
          h = c.length;
          128 > h ? e.push(h) : e.push((h % 128) | 128, h >> 7);
          for (h = 0; h < c.length; ++h) e.push(g[c[h]]);
          "v" == d ? e.push(0) : e.push(1, g[d]);
          c = [0, 97, 115, 109, 1, 0, 0, 0, 1];
          d = e.length;
          128 > d ? c.push(d) : c.push((d % 128) | 128, d >> 7);
          c.push.apply(c, e);
          c.push(2, 7, 1, 1, 101, 1, 102, 0, 0, 7, 5, 1, 1, 102, 0, 0);
          c = new WebAssembly.Module(new Uint8Array(c));
          c = new WebAssembly.Instance(c, { e: { f: a } }).exports.f;
        }
        M.set(f, c);
      }
      U.set(a, f);
      return f;
    };
    b.AsciiToString = function (a) {
      for (var c = ""; ; ) {
        var f = I[a++ >> 0];
        if (!f) return c;
        c += String.fromCharCode(f);
      }
    };
    var Z;
    P = function Fa() {
      Z || Ga();
      Z || (P = Fa);
    };
    function Ga() {
      function a() {
        if (!Z && ((Z = !0), (b.calledRun = !0), !E)) {
          R(ha);
          m(b);
          if (b.onRuntimeInitialized) b.onRuntimeInitialized();
          if (b.postRun)
            for ("function" == typeof b.postRun && (b.postRun = [b.postRun]); b.postRun.length; ) {
              var c = b.postRun.shift();
              ia.unshift(c);
            }
          R(ia);
        }
      }
      if (!(0 < N)) {
        if (b.preRun) for ("function" == typeof b.preRun && (b.preRun = [b.preRun]); b.preRun.length; ) ja();
        R(fa);
        0 < N ||
          (b.setStatus
            ? (b.setStatus("Running..."),
              setTimeout(function () {
                setTimeout(function () {
                  b.setStatus("");
                }, 1);
                a();
              }, 1))
            : a());
      }
    }
    if (b.preInit) for ("function" == typeof b.preInit && (b.preInit = [b.preInit]); 0 < b.preInit.length; ) b.preInit.pop()();
    Ga();

    return koiTiff.ready;
  };
})();
if (typeof exports === "object" && typeof module === "object") module.exports = koiTiff;
else if (typeof define === "function" && define["amd"])
  define([], function () {
    return koiTiff;
  });
else if (typeof exports === "object") exports["koiTiff"] = koiTiff;
