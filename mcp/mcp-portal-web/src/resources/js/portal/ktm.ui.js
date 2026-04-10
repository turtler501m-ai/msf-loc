/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 191);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ (function(module, exports, __webpack_require__) {

/* WEBPACK VAR INJECTION */(function(global) {var check = function (it) {
  return it && it.Math == Math && it;
};

// https://github.com/zloirock/core-js/issues/86#issuecomment-115759028
module.exports =
  // eslint-disable-next-line es-x/no-global-this -- safe
  check(typeof globalThis == 'object' && globalThis) ||
  check(typeof window == 'object' && window) ||
  // eslint-disable-next-line no-restricted-globals -- safe
  check(typeof self == 'object' && self) ||
  check(typeof global == 'object' && global) ||
  // eslint-disable-next-line no-new-func -- fallback
  (function () { return this; })() || Function('return this')();

/* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(142)))

/***/ }),
/* 1 */
/***/ (function(module, exports, __webpack_require__) {

var NATIVE_BIND = __webpack_require__(46);

var FunctionPrototype = Function.prototype;
var bind = FunctionPrototype.bind;
var call = FunctionPrototype.call;
var uncurryThis = NATIVE_BIND && bind.bind(call, call);

module.exports = NATIVE_BIND ? function (fn) {
  return fn && uncurryThis(fn);
} : function (fn) {
  return fn && function () {
    return call.apply(fn, arguments);
  };
};


/***/ }),
/* 2 */
/***/ (function(module, exports) {

module.exports = function (exec) {
  try {
    return !!exec();
  } catch (error) {
    return true;
  }
};


/***/ }),
/* 3 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var getOwnPropertyDescriptor = __webpack_require__(28).f;
var createNonEnumerableProperty = __webpack_require__(34);
var redefine = __webpack_require__(17);
var setGlobal = __webpack_require__(81);
var copyConstructorProperties = __webpack_require__(107);
var isForced = __webpack_require__(87);

/*
  options.target      - name of the target object
  options.global      - target is the global object
  options.stat        - export as static methods of target
  options.proto       - export as prototype methods of target
  options.real        - real prototype method for the `pure` version
  options.forced      - export even if the native feature is available
  options.bind        - bind methods to the target, required for the `pure` version
  options.wrap        - wrap constructors to preventing global pollution, required for the `pure` version
  options.unsafe      - use the simple assignment of property instead of delete + defineProperty
  options.sham        - add a flag to not completely full polyfills
  options.enumerable  - export as enumerable property
  options.noTargetGet - prevent calling a getter on target
  options.name        - the .name of the function if it does not match the key
*/
module.exports = function (options, source) {
  var TARGET = options.target;
  var GLOBAL = options.global;
  var STATIC = options.stat;
  var FORCED, target, key, targetProperty, sourceProperty, descriptor;
  if (GLOBAL) {
    target = global;
  } else if (STATIC) {
    target = global[TARGET] || setGlobal(TARGET, {});
  } else {
    target = (global[TARGET] || {}).prototype;
  }
  if (target) for (key in source) {
    sourceProperty = source[key];
    if (options.noTargetGet) {
      descriptor = getOwnPropertyDescriptor(target, key);
      targetProperty = descriptor && descriptor.value;
    } else targetProperty = target[key];
    FORCED = isForced(GLOBAL ? key : TARGET + (STATIC ? '.' : '#') + key, options.forced);
    // contained in target
    if (!FORCED && targetProperty !== undefined) {
      if (typeof sourceProperty == typeof targetProperty) continue;
      copyConstructorProperties(sourceProperty, targetProperty);
    }
    // add a flag to not completely full polyfills
    if (options.sham || (targetProperty && targetProperty.sham)) {
      createNonEnumerableProperty(sourceProperty, 'sham', true);
    }
    // extend global
    redefine(target, key, sourceProperty, options);
  }
};


/***/ }),
/* 4 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var shared = __webpack_require__(39);
var hasOwn = __webpack_require__(8);
var uid = __webpack_require__(57);
var NATIVE_SYMBOL = __webpack_require__(40);
var USE_SYMBOL_AS_UID = __webpack_require__(99);

var WellKnownSymbolsStore = shared('wks');
var Symbol = global.Symbol;
var symbolFor = Symbol && Symbol['for'];
var createWellKnownSymbol = USE_SYMBOL_AS_UID ? Symbol : Symbol && Symbol.withoutSetter || uid;

module.exports = function (name) {
  if (!hasOwn(WellKnownSymbolsStore, name) || !(NATIVE_SYMBOL || typeof WellKnownSymbolsStore[name] == 'string')) {
    var description = 'Symbol.' + name;
    if (NATIVE_SYMBOL && hasOwn(Symbol, name)) {
      WellKnownSymbolsStore[name] = Symbol[name];
    } else if (USE_SYMBOL_AS_UID && symbolFor) {
      WellKnownSymbolsStore[name] = symbolFor(description);
    } else {
      WellKnownSymbolsStore[name] = createWellKnownSymbol(description);
    }
  } return WellKnownSymbolsStore[name];
};


/***/ }),
/* 5 */
/***/ (function(module, exports) {

// `IsCallable` abstract operation
// https://tc39.es/ecma262/#sec-iscallable
module.exports = function (argument) {
  return typeof argument == 'function';
};


/***/ }),
/* 6 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var isObject = __webpack_require__(11);

var String = global.String;
var TypeError = global.TypeError;

// `Assert: Type(argument) is Object`
module.exports = function (argument) {
  if (isObject(argument)) return argument;
  throw TypeError(String(argument) + ' is not an object');
};


/***/ }),
/* 7 */
/***/ (function(module, exports, __webpack_require__) {

var NATIVE_BIND = __webpack_require__(46);

var call = Function.prototype.call;

module.exports = NATIVE_BIND ? call.bind(call) : function () {
  return call.apply(call, arguments);
};


/***/ }),
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);
var toObject = __webpack_require__(16);

var hasOwnProperty = uncurryThis({}.hasOwnProperty);

// `HasOwnProperty` abstract operation
// https://tc39.es/ecma262/#sec-hasownproperty
// eslint-disable-next-line es-x/no-object-hasown -- safe
module.exports = Object.hasOwn || function hasOwn(it, key) {
  return hasOwnProperty(toObject(it), key);
};


/***/ }),
/* 9 */
/***/ (function(module, exports, __webpack_require__) {

var TO_STRING_TAG_SUPPORT = __webpack_require__(79);
var redefine = __webpack_require__(17);
var toString = __webpack_require__(146);

// `Object.prototype.toString` method
// https://tc39.es/ecma262/#sec-object.prototype.tostring
if (!TO_STRING_TAG_SUPPORT) {
  redefine(Object.prototype, 'toString', toString, { unsafe: true });
}


/***/ }),
/* 10 */
/***/ (function(module, exports, __webpack_require__) {

var fails = __webpack_require__(2);

// Detect IE8's incomplete defineProperty implementation
module.exports = !fails(function () {
  // eslint-disable-next-line es-x/no-object-defineproperty -- required for testing
  return Object.defineProperty({}, 1, { get: function () { return 7; } })[1] != 7;
});


/***/ }),
/* 11 */
/***/ (function(module, exports, __webpack_require__) {

var isCallable = __webpack_require__(5);

module.exports = function (it) {
  return typeof it == 'object' ? it !== null : isCallable(it);
};


/***/ }),
/* 12 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var DESCRIPTORS = __webpack_require__(10);
var IE8_DOM_DEFINE = __webpack_require__(100);
var V8_PROTOTYPE_DEFINE_BUG = __webpack_require__(101);
var anObject = __webpack_require__(6);
var toPropertyKey = __webpack_require__(58);

var TypeError = global.TypeError;
// eslint-disable-next-line es-x/no-object-defineproperty -- safe
var $defineProperty = Object.defineProperty;
// eslint-disable-next-line es-x/no-object-getownpropertydescriptor -- safe
var $getOwnPropertyDescriptor = Object.getOwnPropertyDescriptor;
var ENUMERABLE = 'enumerable';
var CONFIGURABLE = 'configurable';
var WRITABLE = 'writable';

// `Object.defineProperty` method
// https://tc39.es/ecma262/#sec-object.defineproperty
exports.f = DESCRIPTORS ? V8_PROTOTYPE_DEFINE_BUG ? function defineProperty(O, P, Attributes) {
  anObject(O);
  P = toPropertyKey(P);
  anObject(Attributes);
  if (typeof O === 'function' && P === 'prototype' && 'value' in Attributes && WRITABLE in Attributes && !Attributes[WRITABLE]) {
    var current = $getOwnPropertyDescriptor(O, P);
    if (current && current[WRITABLE]) {
      O[P] = Attributes.value;
      Attributes = {
        configurable: CONFIGURABLE in Attributes ? Attributes[CONFIGURABLE] : current[CONFIGURABLE],
        enumerable: ENUMERABLE in Attributes ? Attributes[ENUMERABLE] : current[ENUMERABLE],
        writable: false
      };
    }
  } return $defineProperty(O, P, Attributes);
} : $defineProperty : function defineProperty(O, P, Attributes) {
  anObject(O);
  P = toPropertyKey(P);
  anObject(Attributes);
  if (IE8_DOM_DEFINE) try {
    return $defineProperty(O, P, Attributes);
  } catch (error) { /* empty */ }
  if ('get' in Attributes || 'set' in Attributes) throw TypeError('Accessors not supported');
  if ('value' in Attributes) O[P] = Attributes.value;
  return O;
};


/***/ }),
/* 13 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var classof = __webpack_require__(61);

var String = global.String;

module.exports = function (argument) {
  if (classof(argument) === 'Symbol') throw TypeError('Cannot convert a Symbol value to a string');
  return String(argument);
};


/***/ }),
/* 14 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var DOMIterables = __webpack_require__(103);
var DOMTokenListPrototype = __webpack_require__(104);
var forEach = __webpack_require__(147);
var createNonEnumerableProperty = __webpack_require__(34);

var handlePrototype = function (CollectionPrototype) {
  // some Chrome versions have non-configurable methods on DOMTokenList
  if (CollectionPrototype && CollectionPrototype.forEach !== forEach) try {
    createNonEnumerableProperty(CollectionPrototype, 'forEach', forEach);
  } catch (error) {
    CollectionPrototype.forEach = forEach;
  }
};

for (var COLLECTION_NAME in DOMIterables) {
  if (DOMIterables[COLLECTION_NAME]) {
    handlePrototype(global[COLLECTION_NAME] && global[COLLECTION_NAME].prototype);
  }
}

handlePrototype(DOMTokenListPrototype);


/***/ }),
/* 15 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var exec = __webpack_require__(74);

// `RegExp.prototype.exec` method
// https://tc39.es/ecma262/#sec-regexp.prototype.exec
$({ target: 'RegExp', proto: true, forced: /./.exec !== exec }, {
  exec: exec
});


/***/ }),
/* 16 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var requireObjectCoercible = __webpack_require__(19);

var Object = global.Object;

// `ToObject` abstract operation
// https://tc39.es/ecma262/#sec-toobject
module.exports = function (argument) {
  return Object(requireObjectCoercible(argument));
};


/***/ }),
/* 17 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var isCallable = __webpack_require__(5);
var hasOwn = __webpack_require__(8);
var createNonEnumerableProperty = __webpack_require__(34);
var setGlobal = __webpack_require__(81);
var inspectSource = __webpack_require__(84);
var InternalStateModule = __webpack_require__(41);
var CONFIGURABLE_FUNCTION_NAME = __webpack_require__(51).CONFIGURABLE;

var getInternalState = InternalStateModule.get;
var enforceInternalState = InternalStateModule.enforce;
var TEMPLATE = String(String).split('String');

(module.exports = function (O, key, value, options) {
  var unsafe = options ? !!options.unsafe : false;
  var simple = options ? !!options.enumerable : false;
  var noTargetGet = options ? !!options.noTargetGet : false;
  var name = options && options.name !== undefined ? options.name : key;
  var state;
  if (isCallable(value)) {
    if (String(name).slice(0, 7) === 'Symbol(') {
      name = '[' + String(name).replace(/^Symbol\(([^)]*)\)/, '$1') + ']';
    }
    if (!hasOwn(value, 'name') || (CONFIGURABLE_FUNCTION_NAME && value.name !== name)) {
      createNonEnumerableProperty(value, 'name', name);
    }
    state = enforceInternalState(value);
    if (!state.source) {
      state.source = TEMPLATE.join(typeof name == 'string' ? name : '');
    }
  }
  if (O === global) {
    if (simple) O[key] = value;
    else setGlobal(key, value);
    return;
  } else if (!unsafe) {
    delete O[key];
  } else if (!noTargetGet && O[key]) {
    simple = true;
  }
  if (simple) O[key] = value;
  else createNonEnumerableProperty(O, key, value);
// add fake Function#toString for correct work wrapped methods / constructors with methods like LoDash isNative
})(Function.prototype, 'toString', function toString() {
  return isCallable(this) && getInternalState(this).source || inspectSource(this);
});


/***/ }),
/* 18 */
/***/ (function(module, exports, __webpack_require__) {

// toObject with fallback for non-array-like ES3 strings
var IndexedObject = __webpack_require__(64);
var requireObjectCoercible = __webpack_require__(19);

module.exports = function (it) {
  return IndexedObject(requireObjectCoercible(it));
};


/***/ }),
/* 19 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);

var TypeError = global.TypeError;

// `RequireObjectCoercible` abstract operation
// https://tc39.es/ecma262/#sec-requireobjectcoercible
module.exports = function (it) {
  if (it == undefined) throw TypeError("Can't call method on " + it);
  return it;
};


/***/ }),
/* 20 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var isCallable = __webpack_require__(5);

var aFunction = function (argument) {
  return isCallable(argument) ? argument : undefined;
};

module.exports = function (namespace, method) {
  return arguments.length < 2 ? aFunction(global[namespace]) : global[namespace] && global[namespace][method];
};


/***/ }),
/* 21 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var toObject = __webpack_require__(16);
var nativeKeys = __webpack_require__(71);
var fails = __webpack_require__(2);

var FAILS_ON_PRIMITIVES = fails(function () { nativeKeys(1); });

// `Object.keys` method
// https://tc39.es/ecma262/#sec-object.keys
$({ target: 'Object', stat: true, forced: FAILS_ON_PRIMITIVES }, {
  keys: function keys(it) {
    return nativeKeys(toObject(it));
  }
});


/***/ }),
/* 22 */
/***/ (function(module, exports, __webpack_require__) {

// TODO: Remove this module from `core-js@4` since it's split to modules listed below
__webpack_require__(149);
__webpack_require__(153);
__webpack_require__(154);
__webpack_require__(155);
__webpack_require__(156);


/***/ }),
/* 23 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var toIndexedObject = __webpack_require__(18);
var addToUnscopables = __webpack_require__(94);
var Iterators = __webpack_require__(56);
var InternalStateModule = __webpack_require__(41);
var defineProperty = __webpack_require__(12).f;
var defineIterator = __webpack_require__(95);
var IS_PURE = __webpack_require__(33);
var DESCRIPTORS = __webpack_require__(10);

var ARRAY_ITERATOR = 'Array Iterator';
var setInternalState = InternalStateModule.set;
var getInternalState = InternalStateModule.getterFor(ARRAY_ITERATOR);

// `Array.prototype.entries` method
// https://tc39.es/ecma262/#sec-array.prototype.entries
// `Array.prototype.keys` method
// https://tc39.es/ecma262/#sec-array.prototype.keys
// `Array.prototype.values` method
// https://tc39.es/ecma262/#sec-array.prototype.values
// `Array.prototype[@@iterator]` method
// https://tc39.es/ecma262/#sec-array.prototype-@@iterator
// `CreateArrayIterator` internal method
// https://tc39.es/ecma262/#sec-createarrayiterator
module.exports = defineIterator(Array, 'Array', function (iterated, kind) {
  setInternalState(this, {
    type: ARRAY_ITERATOR,
    target: toIndexedObject(iterated), // target
    index: 0,                          // next index
    kind: kind                         // kind
  });
// `%ArrayIteratorPrototype%.next` method
// https://tc39.es/ecma262/#sec-%arrayiteratorprototype%.next
}, function () {
  var state = getInternalState(this);
  var target = state.target;
  var kind = state.kind;
  var index = state.index++;
  if (!target || index >= target.length) {
    state.target = undefined;
    return { value: undefined, done: true };
  }
  if (kind == 'keys') return { value: index, done: false };
  if (kind == 'values') return { value: target[index], done: false };
  return { value: [index, target[index]], done: false };
}, 'values');

// argumentsList[@@iterator] is %ArrayProto_values%
// https://tc39.es/ecma262/#sec-createunmappedargumentsobject
// https://tc39.es/ecma262/#sec-createmappedargumentsobject
var values = Iterators.Arguments = Iterators.Array;

// https://tc39.es/ecma262/#sec-array.prototype-@@unscopables
addToUnscopables('keys');
addToUnscopables('values');
addToUnscopables('entries');

// V8 ~ Chrome 45- bug
if (!IS_PURE && DESCRIPTORS && values.name !== 'values') try {
  defineProperty(values, 'name', { value: 'values' });
} catch (error) { /* empty */ }


/***/ }),
/* 24 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var $filter = __webpack_require__(62).filter;
var arrayMethodHasSpeciesSupport = __webpack_require__(70);

var HAS_SPECIES_SUPPORT = arrayMethodHasSpeciesSupport('filter');

// `Array.prototype.filter` method
// https://tc39.es/ecma262/#sec-array.prototype.filter
// with adding support of @@species
$({ target: 'Array', proto: true, forced: !HAS_SPECIES_SUPPORT }, {
  filter: function filter(callbackfn /* , thisArg */) {
    return $filter(this, callbackfn, arguments.length > 1 ? arguments[1] : undefined);
  }
});


/***/ }),
/* 25 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var charAt = __webpack_require__(119).charAt;
var toString = __webpack_require__(13);
var InternalStateModule = __webpack_require__(41);
var defineIterator = __webpack_require__(95);

var STRING_ITERATOR = 'String Iterator';
var setInternalState = InternalStateModule.set;
var getInternalState = InternalStateModule.getterFor(STRING_ITERATOR);

// `String.prototype[@@iterator]` method
// https://tc39.es/ecma262/#sec-string.prototype-@@iterator
defineIterator(String, 'String', function (iterated) {
  setInternalState(this, {
    type: STRING_ITERATOR,
    string: toString(iterated),
    index: 0
  });
// `%StringIteratorPrototype%.next` method
// https://tc39.es/ecma262/#sec-%stringiteratorprototype%.next
}, function next() {
  var state = getInternalState(this);
  var string = state.string;
  var index = state.index;
  var point;
  if (index >= string.length) return { value: undefined, done: true };
  point = charAt(string, index);
  state.index += point.length;
  return { value: point, done: false };
});


/***/ }),
/* 26 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var DOMIterables = __webpack_require__(103);
var DOMTokenListPrototype = __webpack_require__(104);
var ArrayIteratorMethods = __webpack_require__(23);
var createNonEnumerableProperty = __webpack_require__(34);
var wellKnownSymbol = __webpack_require__(4);

var ITERATOR = wellKnownSymbol('iterator');
var TO_STRING_TAG = wellKnownSymbol('toStringTag');
var ArrayValues = ArrayIteratorMethods.values;

var handlePrototype = function (CollectionPrototype, COLLECTION_NAME) {
  if (CollectionPrototype) {
    // some Chrome versions have non-configurable methods on DOMTokenList
    if (CollectionPrototype[ITERATOR] !== ArrayValues) try {
      createNonEnumerableProperty(CollectionPrototype, ITERATOR, ArrayValues);
    } catch (error) {
      CollectionPrototype[ITERATOR] = ArrayValues;
    }
    if (!CollectionPrototype[TO_STRING_TAG]) {
      createNonEnumerableProperty(CollectionPrototype, TO_STRING_TAG, COLLECTION_NAME);
    }
    if (DOMIterables[COLLECTION_NAME]) for (var METHOD_NAME in ArrayIteratorMethods) {
      // some Chrome versions have non-configurable methods on DOMTokenList
      if (CollectionPrototype[METHOD_NAME] !== ArrayIteratorMethods[METHOD_NAME]) try {
        createNonEnumerableProperty(CollectionPrototype, METHOD_NAME, ArrayIteratorMethods[METHOD_NAME]);
      } catch (error) {
        CollectionPrototype[METHOD_NAME] = ArrayIteratorMethods[METHOD_NAME];
      }
    }
  }
};

for (var COLLECTION_NAME in DOMIterables) {
  handlePrototype(global[COLLECTION_NAME] && global[COLLECTION_NAME].prototype, COLLECTION_NAME);
}

handlePrototype(DOMTokenListPrototype, 'DOMTokenList');


/***/ }),
/* 27 */
/***/ (function(module, exports, __webpack_require__) {

var toLength = __webpack_require__(52);

// `LengthOfArrayLike` abstract operation
// https://tc39.es/ecma262/#sec-lengthofarraylike
module.exports = function (obj) {
  return toLength(obj.length);
};


/***/ }),
/* 28 */
/***/ (function(module, exports, __webpack_require__) {

var DESCRIPTORS = __webpack_require__(10);
var call = __webpack_require__(7);
var propertyIsEnumerableModule = __webpack_require__(85);
var createPropertyDescriptor = __webpack_require__(49);
var toIndexedObject = __webpack_require__(18);
var toPropertyKey = __webpack_require__(58);
var hasOwn = __webpack_require__(8);
var IE8_DOM_DEFINE = __webpack_require__(100);

// eslint-disable-next-line es-x/no-object-getownpropertydescriptor -- safe
var $getOwnPropertyDescriptor = Object.getOwnPropertyDescriptor;

// `Object.getOwnPropertyDescriptor` method
// https://tc39.es/ecma262/#sec-object.getownpropertydescriptor
exports.f = DESCRIPTORS ? $getOwnPropertyDescriptor : function getOwnPropertyDescriptor(O, P) {
  O = toIndexedObject(O);
  P = toPropertyKey(P);
  if (IE8_DOM_DEFINE) try {
    return $getOwnPropertyDescriptor(O, P);
  } catch (error) { /* empty */ }
  if (hasOwn(O, P)) return createPropertyDescriptor(!call(propertyIsEnumerableModule.f, O, P), O[P]);
};


/***/ }),
/* 29 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var fails = __webpack_require__(2);
var toIndexedObject = __webpack_require__(18);
var nativeGetOwnPropertyDescriptor = __webpack_require__(28).f;
var DESCRIPTORS = __webpack_require__(10);

var FAILS_ON_PRIMITIVES = fails(function () { nativeGetOwnPropertyDescriptor(1); });
var FORCED = !DESCRIPTORS || FAILS_ON_PRIMITIVES;

// `Object.getOwnPropertyDescriptor` method
// https://tc39.es/ecma262/#sec-object.getownpropertydescriptor
$({ target: 'Object', stat: true, forced: FORCED, sham: !DESCRIPTORS }, {
  getOwnPropertyDescriptor: function getOwnPropertyDescriptor(it, key) {
    return nativeGetOwnPropertyDescriptor(toIndexedObject(it), key);
  }
});


/***/ }),
/* 30 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var DESCRIPTORS = __webpack_require__(10);
var ownKeys = __webpack_require__(108);
var toIndexedObject = __webpack_require__(18);
var getOwnPropertyDescriptorModule = __webpack_require__(28);
var createProperty = __webpack_require__(54);

// `Object.getOwnPropertyDescriptors` method
// https://tc39.es/ecma262/#sec-object.getownpropertydescriptors
$({ target: 'Object', stat: true, sham: !DESCRIPTORS }, {
  getOwnPropertyDescriptors: function getOwnPropertyDescriptors(object) {
    var O = toIndexedObject(object);
    var getOwnPropertyDescriptor = getOwnPropertyDescriptorModule.f;
    var keys = ownKeys(O);
    var result = {};
    var index = 0;
    var key, descriptor;
    while (keys.length > index) {
      descriptor = getOwnPropertyDescriptor(O, key = keys[index++]);
      if (descriptor !== undefined) createProperty(result, key, descriptor);
    }
    return result;
  }
});


/***/ }),
/* 31 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
// `Symbol.prototype.description` getter
// https://tc39.es/ecma262/#sec-symbol.prototype.description

var $ = __webpack_require__(3);
var DESCRIPTORS = __webpack_require__(10);
var global = __webpack_require__(0);
var uncurryThis = __webpack_require__(1);
var hasOwn = __webpack_require__(8);
var isCallable = __webpack_require__(5);
var isPrototypeOf = __webpack_require__(35);
var toString = __webpack_require__(13);
var defineProperty = __webpack_require__(12).f;
var copyConstructorProperties = __webpack_require__(107);

var NativeSymbol = global.Symbol;
var SymbolPrototype = NativeSymbol && NativeSymbol.prototype;

if (DESCRIPTORS && isCallable(NativeSymbol) && (!('description' in SymbolPrototype) ||
  // Safari 12 bug
  NativeSymbol().description !== undefined
)) {
  var EmptyStringDescriptionStore = {};
  // wrap Symbol constructor for correct work with undefined description
  var SymbolWrapper = function Symbol() {
    var description = arguments.length < 1 || arguments[0] === undefined ? undefined : toString(arguments[0]);
    var result = isPrototypeOf(SymbolPrototype, this)
      ? new NativeSymbol(description)
      // in Edge 13, String(Symbol(undefined)) === 'Symbol(undefined)'
      : description === undefined ? NativeSymbol() : NativeSymbol(description);
    if (description === '') EmptyStringDescriptionStore[result] = true;
    return result;
  };

  copyConstructorProperties(SymbolWrapper, NativeSymbol);
  SymbolWrapper.prototype = SymbolPrototype;
  SymbolPrototype.constructor = SymbolWrapper;

  var NATIVE_SYMBOL = String(NativeSymbol('test')) == 'Symbol(test)';
  var symbolToString = uncurryThis(SymbolPrototype.toString);
  var symbolValueOf = uncurryThis(SymbolPrototype.valueOf);
  var regexp = /^Symbol\((.*)\)[^)]+$/;
  var replace = uncurryThis(''.replace);
  var stringSlice = uncurryThis(''.slice);

  defineProperty(SymbolPrototype, 'description', {
    configurable: true,
    get: function description() {
      var symbol = symbolValueOf(this);
      var string = symbolToString(symbol);
      if (hasOwn(EmptyStringDescriptionStore, symbol)) return '';
      var desc = NATIVE_SYMBOL ? stringSlice(string, 7, -1) : replace(string, regexp, '$1');
      return desc === '' ? undefined : desc;
    }
  });

  $({ global: true, forced: true }, {
    Symbol: SymbolWrapper
  });
}


/***/ }),
/* 32 */
/***/ (function(module, exports, __webpack_require__) {

var defineWellKnownSymbol = __webpack_require__(115);

// `Symbol.iterator` well-known symbol
// https://tc39.es/ecma262/#sec-symbol.iterator
defineWellKnownSymbol('iterator');


/***/ }),
/* 33 */
/***/ (function(module, exports) {

module.exports = false;


/***/ }),
/* 34 */
/***/ (function(module, exports, __webpack_require__) {

var DESCRIPTORS = __webpack_require__(10);
var definePropertyModule = __webpack_require__(12);
var createPropertyDescriptor = __webpack_require__(49);

module.exports = DESCRIPTORS ? function (object, key, value) {
  return definePropertyModule.f(object, key, createPropertyDescriptor(1, value));
} : function (object, key, value) {
  object[key] = value;
  return object;
};


/***/ }),
/* 35 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);

module.exports = uncurryThis({}.isPrototypeOf);


/***/ }),
/* 36 */
/***/ (function(module, exports, __webpack_require__) {

var aCallable = __webpack_require__(59);

// `GetMethod` abstract operation
// https://tc39.es/ecma262/#sec-getmethod
module.exports = function (V, P) {
  var func = V[P];
  return func == null ? undefined : aCallable(func);
};


/***/ }),
/* 37 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);

var toString = uncurryThis({}.toString);
var stringSlice = uncurryThis(''.slice);

module.exports = function (it) {
  return stringSlice(toString(it), 8, -1);
};


/***/ }),
/* 38 */
/***/ (function(module, exports, __webpack_require__) {

/* global ActiveXObject -- old IE, WSH */
var anObject = __webpack_require__(6);
var definePropertiesModule = __webpack_require__(111);
var enumBugKeys = __webpack_require__(86);
var hiddenKeys = __webpack_require__(50);
var html = __webpack_require__(150);
var documentCreateElement = __webpack_require__(83);
var sharedKey = __webpack_require__(60);

var GT = '>';
var LT = '<';
var PROTOTYPE = 'prototype';
var SCRIPT = 'script';
var IE_PROTO = sharedKey('IE_PROTO');

var EmptyConstructor = function () { /* empty */ };

var scriptTag = function (content) {
  return LT + SCRIPT + GT + content + LT + '/' + SCRIPT + GT;
};

// Create object with fake `null` prototype: use ActiveX Object with cleared prototype
var NullProtoObjectViaActiveX = function (activeXDocument) {
  activeXDocument.write(scriptTag(''));
  activeXDocument.close();
  var temp = activeXDocument.parentWindow.Object;
  activeXDocument = null; // avoid memory leak
  return temp;
};

// Create object with fake `null` prototype: use iframe Object with cleared prototype
var NullProtoObjectViaIFrame = function () {
  // Thrash, waste and sodomy: IE GC bug
  var iframe = documentCreateElement('iframe');
  var JS = 'java' + SCRIPT + ':';
  var iframeDocument;
  iframe.style.display = 'none';
  html.appendChild(iframe);
  // https://github.com/zloirock/core-js/issues/475
  iframe.src = String(JS);
  iframeDocument = iframe.contentWindow.document;
  iframeDocument.open();
  iframeDocument.write(scriptTag('document.F=Object'));
  iframeDocument.close();
  return iframeDocument.F;
};

// Check for document.domain and active x support
// No need to use active x approach when document.domain is not set
// see https://github.com/es-shims/es5-shim/issues/150
// variation of https://github.com/kitcambridge/es5-shim/commit/4f738ac066346
// avoid IE GC bug
var activeXDocument;
var NullProtoObject = function () {
  try {
    activeXDocument = new ActiveXObject('htmlfile');
  } catch (error) { /* ignore */ }
  NullProtoObject = typeof document != 'undefined'
    ? document.domain && activeXDocument
      ? NullProtoObjectViaActiveX(activeXDocument) // old IE
      : NullProtoObjectViaIFrame()
    : NullProtoObjectViaActiveX(activeXDocument); // WSH
  var length = enumBugKeys.length;
  while (length--) delete NullProtoObject[PROTOTYPE][enumBugKeys[length]];
  return NullProtoObject();
};

hiddenKeys[IE_PROTO] = true;

// `Object.create` method
// https://tc39.es/ecma262/#sec-object.create
// eslint-disable-next-line es-x/no-object-create -- safe
module.exports = Object.create || function create(O, Properties) {
  var result;
  if (O !== null) {
    EmptyConstructor[PROTOTYPE] = anObject(O);
    result = new EmptyConstructor();
    EmptyConstructor[PROTOTYPE] = null;
    // add "__proto__" for Object.getPrototypeOf polyfill
    result[IE_PROTO] = O;
  } else result = NullProtoObject();
  return Properties === undefined ? result : definePropertiesModule.f(result, Properties);
};


/***/ }),
/* 39 */
/***/ (function(module, exports, __webpack_require__) {

var IS_PURE = __webpack_require__(33);
var store = __webpack_require__(80);

(module.exports = function (key, value) {
  return store[key] || (store[key] = value !== undefined ? value : {});
})('versions', []).push({
  version: '3.22.0',
  mode: IS_PURE ? 'pure' : 'global',
  copyright: '© 2014-2022 Denis Pushkarev (zloirock.ru)',
  license: 'https://github.com/zloirock/core-js/blob/v3.22.0/LICENSE',
  source: 'https://github.com/zloirock/core-js'
});


/***/ }),
/* 40 */
/***/ (function(module, exports, __webpack_require__) {

/* eslint-disable es-x/no-symbol -- required for testing */
var V8_VERSION = __webpack_require__(82);
var fails = __webpack_require__(2);

// eslint-disable-next-line es-x/no-object-getownpropertysymbols -- required for testing
module.exports = !!Object.getOwnPropertySymbols && !fails(function () {
  var symbol = Symbol();
  // Chrome 38 Symbol has incorrect toString conversion
  // `get-own-property-symbols` polyfill symbols converted to object are not Symbol instances
  return !String(symbol) || !(Object(symbol) instanceof Symbol) ||
    // Chrome 38-40 symbols are not inherited from DOM collections prototypes to instances
    !Symbol.sham && V8_VERSION && V8_VERSION < 41;
});


/***/ }),
/* 41 */
/***/ (function(module, exports, __webpack_require__) {

var NATIVE_WEAK_MAP = __webpack_require__(145);
var global = __webpack_require__(0);
var uncurryThis = __webpack_require__(1);
var isObject = __webpack_require__(11);
var createNonEnumerableProperty = __webpack_require__(34);
var hasOwn = __webpack_require__(8);
var shared = __webpack_require__(80);
var sharedKey = __webpack_require__(60);
var hiddenKeys = __webpack_require__(50);

var OBJECT_ALREADY_INITIALIZED = 'Object already initialized';
var TypeError = global.TypeError;
var WeakMap = global.WeakMap;
var set, get, has;

var enforce = function (it) {
  return has(it) ? get(it) : set(it, {});
};

var getterFor = function (TYPE) {
  return function (it) {
    var state;
    if (!isObject(it) || (state = get(it)).type !== TYPE) {
      throw TypeError('Incompatible receiver, ' + TYPE + ' required');
    } return state;
  };
};

if (NATIVE_WEAK_MAP || shared.state) {
  var store = shared.state || (shared.state = new WeakMap());
  var wmget = uncurryThis(store.get);
  var wmhas = uncurryThis(store.has);
  var wmset = uncurryThis(store.set);
  set = function (it, metadata) {
    if (wmhas(store, it)) throw new TypeError(OBJECT_ALREADY_INITIALIZED);
    metadata.facade = it;
    wmset(store, it, metadata);
    return metadata;
  };
  get = function (it) {
    return wmget(store, it) || {};
  };
  has = function (it) {
    return wmhas(store, it);
  };
} else {
  var STATE = sharedKey('state');
  hiddenKeys[STATE] = true;
  set = function (it, metadata) {
    if (hasOwn(it, STATE)) throw new TypeError(OBJECT_ALREADY_INITIALIZED);
    metadata.facade = it;
    createNonEnumerableProperty(it, STATE, metadata);
    return metadata;
  };
  get = function (it) {
    return hasOwn(it, STATE) ? it[STATE] : {};
  };
  has = function (it) {
    return hasOwn(it, STATE);
  };
}

module.exports = {
  set: set,
  get: get,
  has: has,
  enforce: enforce,
  getterFor: getterFor
};


/***/ }),
/* 42 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var global = __webpack_require__(0);
var fails = __webpack_require__(2);
var isArray = __webpack_require__(66);
var isObject = __webpack_require__(11);
var toObject = __webpack_require__(16);
var lengthOfArrayLike = __webpack_require__(27);
var createProperty = __webpack_require__(54);
var arraySpeciesCreate = __webpack_require__(105);
var arrayMethodHasSpeciesSupport = __webpack_require__(70);
var wellKnownSymbol = __webpack_require__(4);
var V8_VERSION = __webpack_require__(82);

var IS_CONCAT_SPREADABLE = wellKnownSymbol('isConcatSpreadable');
var MAX_SAFE_INTEGER = 0x1FFFFFFFFFFFFF;
var MAXIMUM_ALLOWED_INDEX_EXCEEDED = 'Maximum allowed index exceeded';
var TypeError = global.TypeError;

// We can't use this feature detection in V8 since it causes
// deoptimization and serious performance degradation
// https://github.com/zloirock/core-js/issues/679
var IS_CONCAT_SPREADABLE_SUPPORT = V8_VERSION >= 51 || !fails(function () {
  var array = [];
  array[IS_CONCAT_SPREADABLE] = false;
  return array.concat()[0] !== array;
});

var SPECIES_SUPPORT = arrayMethodHasSpeciesSupport('concat');

var isConcatSpreadable = function (O) {
  if (!isObject(O)) return false;
  var spreadable = O[IS_CONCAT_SPREADABLE];
  return spreadable !== undefined ? !!spreadable : isArray(O);
};

var FORCED = !IS_CONCAT_SPREADABLE_SUPPORT || !SPECIES_SUPPORT;

// `Array.prototype.concat` method
// https://tc39.es/ecma262/#sec-array.prototype.concat
// with adding support of @@isConcatSpreadable and @@species
$({ target: 'Array', proto: true, forced: FORCED }, {
  // eslint-disable-next-line no-unused-vars -- required for `.length`
  concat: function concat(arg) {
    var O = toObject(this);
    var A = arraySpeciesCreate(O, 0);
    var n = 0;
    var i, k, length, len, E;
    for (i = -1, length = arguments.length; i < length; i++) {
      E = i === -1 ? O : arguments[i];
      if (isConcatSpreadable(E)) {
        len = lengthOfArrayLike(E);
        if (n + len > MAX_SAFE_INTEGER) throw TypeError(MAXIMUM_ALLOWED_INDEX_EXCEEDED);
        for (k = 0; k < len; k++, n++) if (k in E) createProperty(A, n, E[k]);
      } else {
        if (n >= MAX_SAFE_INTEGER) throw TypeError(MAXIMUM_ALLOWED_INDEX_EXCEEDED);
        createProperty(A, n++, E);
      }
    }
    A.length = n;
    return A;
  }
});


/***/ }),
/* 43 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var fails = __webpack_require__(2);
var toObject = __webpack_require__(16);
var nativeGetPrototypeOf = __webpack_require__(75);
var CORRECT_PROTOTYPE_GETTER = __webpack_require__(121);

var FAILS_ON_PRIMITIVES = fails(function () { nativeGetPrototypeOf(1); });

// `Object.getPrototypeOf` method
// https://tc39.es/ecma262/#sec-object.getprototypeof
$({ target: 'Object', stat: true, forced: FAILS_ON_PRIMITIVES, sham: !CORRECT_PROTOTYPE_GETTER }, {
  getPrototypeOf: function getPrototypeOf(it) {
    return nativeGetPrototypeOf(toObject(it));
  }
});



/***/ }),
/* 44 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var getBuiltIn = __webpack_require__(20);
var apply = __webpack_require__(73);
var bind = __webpack_require__(181);
var aConstructor = __webpack_require__(118);
var anObject = __webpack_require__(6);
var isObject = __webpack_require__(11);
var create = __webpack_require__(38);
var fails = __webpack_require__(2);

var nativeConstruct = getBuiltIn('Reflect', 'construct');
var ObjectPrototype = Object.prototype;
var push = [].push;

// `Reflect.construct` method
// https://tc39.es/ecma262/#sec-reflect.construct
// MS Edge supports only 2 arguments and argumentsList argument is optional
// FF Nightly sets third argument as `new.target`, but does not create `this` from it
var NEW_TARGET_BUG = fails(function () {
  function F() { /* empty */ }
  return !(nativeConstruct(function () { /* empty */ }, [], F) instanceof F);
});

var ARGS_BUG = !fails(function () {
  nativeConstruct(function () { /* empty */ });
});

var FORCED = NEW_TARGET_BUG || ARGS_BUG;

$({ target: 'Reflect', stat: true, forced: FORCED, sham: FORCED }, {
  construct: function construct(Target, args /* , newTarget */) {
    aConstructor(Target);
    anObject(args);
    var newTarget = arguments.length < 3 ? Target : aConstructor(arguments[2]);
    if (ARGS_BUG && !NEW_TARGET_BUG) return nativeConstruct(Target, args, newTarget);
    if (Target == newTarget) {
      // w/o altered newTarget, optimization for 0-4 arguments
      switch (args.length) {
        case 0: return new Target();
        case 1: return new Target(args[0]);
        case 2: return new Target(args[0], args[1]);
        case 3: return new Target(args[0], args[1], args[2]);
        case 4: return new Target(args[0], args[1], args[2], args[3]);
      }
      // w/o altered newTarget, lot of arguments case
      var $args = [null];
      apply(push, $args, args);
      return new (apply(bind, Target, $args))();
    }
    // with altered newTarget, not support built-in constructors
    var proto = newTarget.prototype;
    var instance = create(isObject(proto) ? proto : ObjectPrototype);
    var result = apply(Target, instance, args);
    return isObject(result) ? result : instance;
  }
});


/***/ }),
/* 45 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var call = __webpack_require__(7);
var isObject = __webpack_require__(11);
var anObject = __webpack_require__(6);
var isDataDescriptor = __webpack_require__(182);
var getOwnPropertyDescriptorModule = __webpack_require__(28);
var getPrototypeOf = __webpack_require__(75);

// `Reflect.get` method
// https://tc39.es/ecma262/#sec-reflect.get
function get(target, propertyKey /* , receiver */) {
  var receiver = arguments.length < 3 ? target : arguments[2];
  var descriptor, prototype;
  if (anObject(target) === receiver) return target[propertyKey];
  descriptor = getOwnPropertyDescriptorModule.f(target, propertyKey);
  if (descriptor) return isDataDescriptor(descriptor)
    ? descriptor.value
    : descriptor.get === undefined ? undefined : call(descriptor.get, receiver);
  if (isObject(prototype = getPrototypeOf(target))) return get(prototype, propertyKey, receiver);
}

$({ target: 'Reflect', stat: true }, {
  get: get
});


/***/ }),
/* 46 */
/***/ (function(module, exports, __webpack_require__) {

var fails = __webpack_require__(2);

module.exports = !fails(function () {
  // eslint-disable-next-line es-x/no-function-prototype-bind -- safe
  var test = (function () { /* empty */ }).bind();
  // eslint-disable-next-line no-prototype-builtins -- safe
  return typeof test != 'function' || test.hasOwnProperty('prototype');
});


/***/ }),
/* 47 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var getBuiltIn = __webpack_require__(20);
var isCallable = __webpack_require__(5);
var isPrototypeOf = __webpack_require__(35);
var USE_SYMBOL_AS_UID = __webpack_require__(99);

var Object = global.Object;

module.exports = USE_SYMBOL_AS_UID ? function (it) {
  return typeof it == 'symbol';
} : function (it) {
  var $Symbol = getBuiltIn('Symbol');
  return isCallable($Symbol) && isPrototypeOf($Symbol.prototype, Object(it));
};


/***/ }),
/* 48 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);

var String = global.String;

module.exports = function (argument) {
  try {
    return String(argument);
  } catch (error) {
    return 'Object';
  }
};


/***/ }),
/* 49 */
/***/ (function(module, exports) {

module.exports = function (bitmap, value) {
  return {
    enumerable: !(bitmap & 1),
    configurable: !(bitmap & 2),
    writable: !(bitmap & 4),
    value: value
  };
};


/***/ }),
/* 50 */
/***/ (function(module, exports) {

module.exports = {};


/***/ }),
/* 51 */
/***/ (function(module, exports, __webpack_require__) {

var DESCRIPTORS = __webpack_require__(10);
var hasOwn = __webpack_require__(8);

var FunctionPrototype = Function.prototype;
// eslint-disable-next-line es-x/no-object-getownpropertydescriptor -- safe
var getDescriptor = DESCRIPTORS && Object.getOwnPropertyDescriptor;

var EXISTS = hasOwn(FunctionPrototype, 'name');
// additional protection from minified / mangled / dropped function names
var PROPER = EXISTS && (function something() { /* empty */ }).name === 'something';
var CONFIGURABLE = EXISTS && (!DESCRIPTORS || (DESCRIPTORS && getDescriptor(FunctionPrototype, 'name').configurable));

module.exports = {
  EXISTS: EXISTS,
  PROPER: PROPER,
  CONFIGURABLE: CONFIGURABLE
};


/***/ }),
/* 52 */
/***/ (function(module, exports, __webpack_require__) {

var toIntegerOrInfinity = __webpack_require__(65);

var min = Math.min;

// `ToLength` abstract operation
// https://tc39.es/ecma262/#sec-tolength
module.exports = function (argument) {
  return argument > 0 ? min(toIntegerOrInfinity(argument), 0x1FFFFFFFFFFFFF) : 0; // 2 ** 53 - 1 == 9007199254740991
};


/***/ }),
/* 53 */
/***/ (function(module, exports, __webpack_require__) {

var internalObjectKeys = __webpack_require__(109);
var enumBugKeys = __webpack_require__(86);

var hiddenKeys = enumBugKeys.concat('length', 'prototype');

// `Object.getOwnPropertyNames` method
// https://tc39.es/ecma262/#sec-object.getownpropertynames
// eslint-disable-next-line es-x/no-object-getownpropertynames -- safe
exports.f = Object.getOwnPropertyNames || function getOwnPropertyNames(O) {
  return internalObjectKeys(O, hiddenKeys);
};


/***/ }),
/* 54 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var toPropertyKey = __webpack_require__(58);
var definePropertyModule = __webpack_require__(12);
var createPropertyDescriptor = __webpack_require__(49);

module.exports = function (object, key, value) {
  var propertyKey = toPropertyKey(key);
  if (propertyKey in object) definePropertyModule.f(object, propertyKey, createPropertyDescriptor(0, value));
  else object[propertyKey] = value;
};


/***/ }),
/* 55 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var apply = __webpack_require__(73);
var call = __webpack_require__(7);
var uncurryThis = __webpack_require__(1);
var fixRegExpWellKnownSymbolLogic = __webpack_require__(90);
var isRegExp = __webpack_require__(91);
var anObject = __webpack_require__(6);
var requireObjectCoercible = __webpack_require__(19);
var speciesConstructor = __webpack_require__(160);
var advanceStringIndex = __webpack_require__(92);
var toLength = __webpack_require__(52);
var toString = __webpack_require__(13);
var getMethod = __webpack_require__(36);
var arraySlice = __webpack_require__(113);
var callRegExpExec = __webpack_require__(93);
var regexpExec = __webpack_require__(74);
var stickyHelpers = __webpack_require__(117);
var fails = __webpack_require__(2);

var UNSUPPORTED_Y = stickyHelpers.UNSUPPORTED_Y;
var MAX_UINT32 = 0xFFFFFFFF;
var min = Math.min;
var $push = [].push;
var exec = uncurryThis(/./.exec);
var push = uncurryThis($push);
var stringSlice = uncurryThis(''.slice);

// Chrome 51 has a buggy "split" implementation when RegExp#exec !== nativeExec
// Weex JS has frozen built-in prototypes, so use try / catch wrapper
var SPLIT_WORKS_WITH_OVERWRITTEN_EXEC = !fails(function () {
  // eslint-disable-next-line regexp/no-empty-group -- required for testing
  var re = /(?:)/;
  var originalExec = re.exec;
  re.exec = function () { return originalExec.apply(this, arguments); };
  var result = 'ab'.split(re);
  return result.length !== 2 || result[0] !== 'a' || result[1] !== 'b';
});

// @@split logic
fixRegExpWellKnownSymbolLogic('split', function (SPLIT, nativeSplit, maybeCallNative) {
  var internalSplit;
  if (
    'abbc'.split(/(b)*/)[1] == 'c' ||
    // eslint-disable-next-line regexp/no-empty-group -- required for testing
    'test'.split(/(?:)/, -1).length != 4 ||
    'ab'.split(/(?:ab)*/).length != 2 ||
    '.'.split(/(.?)(.?)/).length != 4 ||
    // eslint-disable-next-line regexp/no-empty-capturing-group, regexp/no-empty-group -- required for testing
    '.'.split(/()()/).length > 1 ||
    ''.split(/.?/).length
  ) {
    // based on es5-shim implementation, need to rework it
    internalSplit = function (separator, limit) {
      var string = toString(requireObjectCoercible(this));
      var lim = limit === undefined ? MAX_UINT32 : limit >>> 0;
      if (lim === 0) return [];
      if (separator === undefined) return [string];
      // If `separator` is not a regex, use native split
      if (!isRegExp(separator)) {
        return call(nativeSplit, string, separator, lim);
      }
      var output = [];
      var flags = (separator.ignoreCase ? 'i' : '') +
                  (separator.multiline ? 'm' : '') +
                  (separator.unicode ? 'u' : '') +
                  (separator.sticky ? 'y' : '');
      var lastLastIndex = 0;
      // Make `global` and avoid `lastIndex` issues by working with a copy
      var separatorCopy = new RegExp(separator.source, flags + 'g');
      var match, lastIndex, lastLength;
      while (match = call(regexpExec, separatorCopy, string)) {
        lastIndex = separatorCopy.lastIndex;
        if (lastIndex > lastLastIndex) {
          push(output, stringSlice(string, lastLastIndex, match.index));
          if (match.length > 1 && match.index < string.length) apply($push, output, arraySlice(match, 1));
          lastLength = match[0].length;
          lastLastIndex = lastIndex;
          if (output.length >= lim) break;
        }
        if (separatorCopy.lastIndex === match.index) separatorCopy.lastIndex++; // Avoid an infinite loop
      }
      if (lastLastIndex === string.length) {
        if (lastLength || !exec(separatorCopy, '')) push(output, '');
      } else push(output, stringSlice(string, lastLastIndex));
      return output.length > lim ? arraySlice(output, 0, lim) : output;
    };
  // Chakra, V8
  } else if ('0'.split(undefined, 0).length) {
    internalSplit = function (separator, limit) {
      return separator === undefined && limit === 0 ? [] : call(nativeSplit, this, separator, limit);
    };
  } else internalSplit = nativeSplit;

  return [
    // `String.prototype.split` method
    // https://tc39.es/ecma262/#sec-string.prototype.split
    function split(separator, limit) {
      var O = requireObjectCoercible(this);
      var splitter = separator == undefined ? undefined : getMethod(separator, SPLIT);
      return splitter
        ? call(splitter, separator, O, limit)
        : call(internalSplit, toString(O), separator, limit);
    },
    // `RegExp.prototype[@@split]` method
    // https://tc39.es/ecma262/#sec-regexp.prototype-@@split
    //
    // NOTE: This cannot be properly polyfilled in engines that don't support
    // the 'y' flag.
    function (string, limit) {
      var rx = anObject(this);
      var S = toString(string);
      var res = maybeCallNative(internalSplit, rx, S, limit, internalSplit !== nativeSplit);

      if (res.done) return res.value;

      var C = speciesConstructor(rx, RegExp);

      var unicodeMatching = rx.unicode;
      var flags = (rx.ignoreCase ? 'i' : '') +
                  (rx.multiline ? 'm' : '') +
                  (rx.unicode ? 'u' : '') +
                  (UNSUPPORTED_Y ? 'g' : 'y');

      // ^(? + rx + ) is needed, in combination with some S slicing, to
      // simulate the 'y' flag.
      var splitter = new C(UNSUPPORTED_Y ? '^(?:' + rx.source + ')' : rx, flags);
      var lim = limit === undefined ? MAX_UINT32 : limit >>> 0;
      if (lim === 0) return [];
      if (S.length === 0) return callRegExpExec(splitter, S) === null ? [S] : [];
      var p = 0;
      var q = 0;
      var A = [];
      while (q < S.length) {
        splitter.lastIndex = UNSUPPORTED_Y ? 0 : q;
        var z = callRegExpExec(splitter, UNSUPPORTED_Y ? stringSlice(S, q) : S);
        var e;
        if (
          z === null ||
          (e = min(toLength(splitter.lastIndex + (UNSUPPORTED_Y ? q : 0)), S.length)) === p
        ) {
          q = advanceStringIndex(S, q, unicodeMatching);
        } else {
          push(A, stringSlice(S, p, q));
          if (A.length === lim) return A;
          for (var i = 1; i <= z.length - 1; i++) {
            push(A, z[i]);
            if (A.length === lim) return A;
          }
          q = p = e;
        }
      }
      push(A, stringSlice(S, p));
      return A;
    }
  ];
}, !SPLIT_WORKS_WITH_OVERWRITTEN_EXEC, UNSUPPORTED_Y);


/***/ }),
/* 56 */
/***/ (function(module, exports) {

module.exports = {};


/***/ }),
/* 57 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);

var id = 0;
var postfix = Math.random();
var toString = uncurryThis(1.0.toString);

module.exports = function (key) {
  return 'Symbol(' + (key === undefined ? '' : key) + ')_' + toString(++id + postfix, 36);
};


/***/ }),
/* 58 */
/***/ (function(module, exports, __webpack_require__) {

var toPrimitive = __webpack_require__(102);
var isSymbol = __webpack_require__(47);

// `ToPropertyKey` abstract operation
// https://tc39.es/ecma262/#sec-topropertykey
module.exports = function (argument) {
  var key = toPrimitive(argument, 'string');
  return isSymbol(key) ? key : key + '';
};


/***/ }),
/* 59 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var isCallable = __webpack_require__(5);
var tryToString = __webpack_require__(48);

var TypeError = global.TypeError;

// `Assert: IsCallable(argument) is true`
module.exports = function (argument) {
  if (isCallable(argument)) return argument;
  throw TypeError(tryToString(argument) + ' is not a function');
};


/***/ }),
/* 60 */
/***/ (function(module, exports, __webpack_require__) {

var shared = __webpack_require__(39);
var uid = __webpack_require__(57);

var keys = shared('keys');

module.exports = function (key) {
  return keys[key] || (keys[key] = uid(key));
};


/***/ }),
/* 61 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var TO_STRING_TAG_SUPPORT = __webpack_require__(79);
var isCallable = __webpack_require__(5);
var classofRaw = __webpack_require__(37);
var wellKnownSymbol = __webpack_require__(4);

var TO_STRING_TAG = wellKnownSymbol('toStringTag');
var Object = global.Object;

// ES3 wrong here
var CORRECT_ARGUMENTS = classofRaw(function () { return arguments; }()) == 'Arguments';

// fallback for IE11 Script Access Denied error
var tryGet = function (it, key) {
  try {
    return it[key];
  } catch (error) { /* empty */ }
};

// getting tag from ES6+ `Object.prototype.toString`
module.exports = TO_STRING_TAG_SUPPORT ? classofRaw : function (it) {
  var O, tag, result;
  return it === undefined ? 'Undefined' : it === null ? 'Null'
    // @@toStringTag case
    : typeof (tag = tryGet(O = Object(it), TO_STRING_TAG)) == 'string' ? tag
    // builtinTag case
    : CORRECT_ARGUMENTS ? classofRaw(O)
    // ES3 arguments fallback
    : (result = classofRaw(O)) == 'Object' && isCallable(O.callee) ? 'Arguments' : result;
};


/***/ }),
/* 62 */
/***/ (function(module, exports, __webpack_require__) {

var bind = __webpack_require__(63);
var uncurryThis = __webpack_require__(1);
var IndexedObject = __webpack_require__(64);
var toObject = __webpack_require__(16);
var lengthOfArrayLike = __webpack_require__(27);
var arraySpeciesCreate = __webpack_require__(105);

var push = uncurryThis([].push);

// `Array.prototype.{ forEach, map, filter, some, every, find, findIndex, filterReject }` methods implementation
var createMethod = function (TYPE) {
  var IS_MAP = TYPE == 1;
  var IS_FILTER = TYPE == 2;
  var IS_SOME = TYPE == 3;
  var IS_EVERY = TYPE == 4;
  var IS_FIND_INDEX = TYPE == 6;
  var IS_FILTER_REJECT = TYPE == 7;
  var NO_HOLES = TYPE == 5 || IS_FIND_INDEX;
  return function ($this, callbackfn, that, specificCreate) {
    var O = toObject($this);
    var self = IndexedObject(O);
    var boundFunction = bind(callbackfn, that);
    var length = lengthOfArrayLike(self);
    var index = 0;
    var create = specificCreate || arraySpeciesCreate;
    var target = IS_MAP ? create($this, length) : IS_FILTER || IS_FILTER_REJECT ? create($this, 0) : undefined;
    var value, result;
    for (;length > index; index++) if (NO_HOLES || index in self) {
      value = self[index];
      result = boundFunction(value, index, O);
      if (TYPE) {
        if (IS_MAP) target[index] = result; // map
        else if (result) switch (TYPE) {
          case 3: return true;              // some
          case 5: return value;             // find
          case 6: return index;             // findIndex
          case 2: push(target, value);      // filter
        } else switch (TYPE) {
          case 4: return false;             // every
          case 7: push(target, value);      // filterReject
        }
      }
    }
    return IS_FIND_INDEX ? -1 : IS_SOME || IS_EVERY ? IS_EVERY : target;
  };
};

module.exports = {
  // `Array.prototype.forEach` method
  // https://tc39.es/ecma262/#sec-array.prototype.foreach
  forEach: createMethod(0),
  // `Array.prototype.map` method
  // https://tc39.es/ecma262/#sec-array.prototype.map
  map: createMethod(1),
  // `Array.prototype.filter` method
  // https://tc39.es/ecma262/#sec-array.prototype.filter
  filter: createMethod(2),
  // `Array.prototype.some` method
  // https://tc39.es/ecma262/#sec-array.prototype.some
  some: createMethod(3),
  // `Array.prototype.every` method
  // https://tc39.es/ecma262/#sec-array.prototype.every
  every: createMethod(4),
  // `Array.prototype.find` method
  // https://tc39.es/ecma262/#sec-array.prototype.find
  find: createMethod(5),
  // `Array.prototype.findIndex` method
  // https://tc39.es/ecma262/#sec-array.prototype.findIndex
  findIndex: createMethod(6),
  // `Array.prototype.filterReject` method
  // https://github.com/tc39/proposal-array-filtering
  filterReject: createMethod(7)
};


/***/ }),
/* 63 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);
var aCallable = __webpack_require__(59);
var NATIVE_BIND = __webpack_require__(46);

var bind = uncurryThis(uncurryThis.bind);

// optional / simple context binding
module.exports = function (fn, that) {
  aCallable(fn);
  return that === undefined ? fn : NATIVE_BIND ? bind(fn, that) : function (/* ...args */) {
    return fn.apply(that, arguments);
  };
};


/***/ }),
/* 64 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var uncurryThis = __webpack_require__(1);
var fails = __webpack_require__(2);
var classof = __webpack_require__(37);

var Object = global.Object;
var split = uncurryThis(''.split);

// fallback for non-array-like ES3 and non-enumerable old V8 strings
module.exports = fails(function () {
  // throws an error in rhino, see https://github.com/mozilla/rhino/issues/346
  // eslint-disable-next-line no-prototype-builtins -- safe
  return !Object('z').propertyIsEnumerable(0);
}) ? function (it) {
  return classof(it) == 'String' ? split(it, '') : Object(it);
} : Object;


/***/ }),
/* 65 */
/***/ (function(module, exports) {

var ceil = Math.ceil;
var floor = Math.floor;

// `ToIntegerOrInfinity` abstract operation
// https://tc39.es/ecma262/#sec-tointegerorinfinity
module.exports = function (argument) {
  var number = +argument;
  // eslint-disable-next-line no-self-compare -- safe
  return number !== number || number === 0 ? 0 : (number > 0 ? floor : ceil)(number);
};


/***/ }),
/* 66 */
/***/ (function(module, exports, __webpack_require__) {

var classof = __webpack_require__(37);

// `IsArray` abstract operation
// https://tc39.es/ecma262/#sec-isarray
// eslint-disable-next-line es-x/no-array-isarray -- safe
module.exports = Array.isArray || function isArray(argument) {
  return classof(argument) == 'Array';
};


/***/ }),
/* 67 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);
var fails = __webpack_require__(2);
var isCallable = __webpack_require__(5);
var classof = __webpack_require__(61);
var getBuiltIn = __webpack_require__(20);
var inspectSource = __webpack_require__(84);

var noop = function () { /* empty */ };
var empty = [];
var construct = getBuiltIn('Reflect', 'construct');
var constructorRegExp = /^\s*(?:class|function)\b/;
var exec = uncurryThis(constructorRegExp.exec);
var INCORRECT_TO_STRING = !constructorRegExp.exec(noop);

var isConstructorModern = function isConstructor(argument) {
  if (!isCallable(argument)) return false;
  try {
    construct(noop, empty, argument);
    return true;
  } catch (error) {
    return false;
  }
};

var isConstructorLegacy = function isConstructor(argument) {
  if (!isCallable(argument)) return false;
  switch (classof(argument)) {
    case 'AsyncFunction':
    case 'GeneratorFunction':
    case 'AsyncGeneratorFunction': return false;
  }
  try {
    // we can't check .prototype since constructors produced by .bind haven't it
    // `Function#toString` throws on some built-it function in some legacy engines
    // (for example, `DOMQuad` and similar in FF41-)
    return INCORRECT_TO_STRING || !!exec(constructorRegExp, inspectSource(argument));
  } catch (error) {
    return true;
  }
};

isConstructorLegacy.sham = true;

// `IsConstructor` abstract operation
// https://tc39.es/ecma262/#sec-isconstructor
module.exports = !construct || fails(function () {
  var called;
  return isConstructorModern(isConstructorModern.call)
    || !isConstructorModern(Object)
    || !isConstructorModern(function () { called = true; })
    || called;
}) ? isConstructorLegacy : isConstructorModern;


/***/ }),
/* 68 */
/***/ (function(module, exports, __webpack_require__) {

var toIntegerOrInfinity = __webpack_require__(65);

var max = Math.max;
var min = Math.min;

// Helper for a popular repeating case of the spec:
// Let integer be ? ToInteger(index).
// If integer < 0, let result be max((length + integer), 0); else let result be min(integer, length).
module.exports = function (index, length) {
  var integer = toIntegerOrInfinity(index);
  return integer < 0 ? max(integer + length, 0) : min(integer, length);
};


/***/ }),
/* 69 */
/***/ (function(module, exports) {

// eslint-disable-next-line es-x/no-object-getownpropertysymbols -- safe
exports.f = Object.getOwnPropertySymbols;


/***/ }),
/* 70 */
/***/ (function(module, exports, __webpack_require__) {

var fails = __webpack_require__(2);
var wellKnownSymbol = __webpack_require__(4);
var V8_VERSION = __webpack_require__(82);

var SPECIES = wellKnownSymbol('species');

module.exports = function (METHOD_NAME) {
  // We can't use this feature detection in V8 since it causes
  // deoptimization and serious performance degradation
  // https://github.com/zloirock/core-js/issues/677
  return V8_VERSION >= 51 || !fails(function () {
    var array = [];
    var constructor = array.constructor = {};
    constructor[SPECIES] = function () {
      return { foo: 1 };
    };
    return array[METHOD_NAME](Boolean).foo !== 1;
  });
};


/***/ }),
/* 71 */
/***/ (function(module, exports, __webpack_require__) {

var internalObjectKeys = __webpack_require__(109);
var enumBugKeys = __webpack_require__(86);

// `Object.keys` method
// https://tc39.es/ecma262/#sec-object.keys
// eslint-disable-next-line es-x/no-object-keys -- safe
module.exports = Object.keys || function keys(O) {
  return internalObjectKeys(O, enumBugKeys);
};


/***/ }),
/* 72 */
/***/ (function(module, exports, __webpack_require__) {

var defineProperty = __webpack_require__(12).f;
var hasOwn = __webpack_require__(8);
var wellKnownSymbol = __webpack_require__(4);

var TO_STRING_TAG = wellKnownSymbol('toStringTag');

module.exports = function (target, TAG, STATIC) {
  if (target && !STATIC) target = target.prototype;
  if (target && !hasOwn(target, TO_STRING_TAG)) {
    defineProperty(target, TO_STRING_TAG, { configurable: true, value: TAG });
  }
};


/***/ }),
/* 73 */
/***/ (function(module, exports, __webpack_require__) {

var NATIVE_BIND = __webpack_require__(46);

var FunctionPrototype = Function.prototype;
var apply = FunctionPrototype.apply;
var call = FunctionPrototype.call;

// eslint-disable-next-line es-x/no-reflect -- safe
module.exports = typeof Reflect == 'object' && Reflect.apply || (NATIVE_BIND ? call.bind(apply) : function () {
  return call.apply(apply, arguments);
});


/***/ }),
/* 74 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

/* eslint-disable regexp/no-empty-capturing-group, regexp/no-empty-group, regexp/no-lazy-ends -- testing */
/* eslint-disable regexp/no-useless-quantifier -- testing */
var call = __webpack_require__(7);
var uncurryThis = __webpack_require__(1);
var toString = __webpack_require__(13);
var regexpFlags = __webpack_require__(89);
var stickyHelpers = __webpack_require__(117);
var shared = __webpack_require__(39);
var create = __webpack_require__(38);
var getInternalState = __webpack_require__(41).get;
var UNSUPPORTED_DOT_ALL = __webpack_require__(158);
var UNSUPPORTED_NCG = __webpack_require__(159);

var nativeReplace = shared('native-string-replace', String.prototype.replace);
var nativeExec = RegExp.prototype.exec;
var patchedExec = nativeExec;
var charAt = uncurryThis(''.charAt);
var indexOf = uncurryThis(''.indexOf);
var replace = uncurryThis(''.replace);
var stringSlice = uncurryThis(''.slice);

var UPDATES_LAST_INDEX_WRONG = (function () {
  var re1 = /a/;
  var re2 = /b*/g;
  call(nativeExec, re1, 'a');
  call(nativeExec, re2, 'a');
  return re1.lastIndex !== 0 || re2.lastIndex !== 0;
})();

var UNSUPPORTED_Y = stickyHelpers.BROKEN_CARET;

// nonparticipating capturing group, copied from es5-shim's String#split patch.
var NPCG_INCLUDED = /()??/.exec('')[1] !== undefined;

var PATCH = UPDATES_LAST_INDEX_WRONG || NPCG_INCLUDED || UNSUPPORTED_Y || UNSUPPORTED_DOT_ALL || UNSUPPORTED_NCG;

if (PATCH) {
  patchedExec = function exec(string) {
    var re = this;
    var state = getInternalState(re);
    var str = toString(string);
    var raw = state.raw;
    var result, reCopy, lastIndex, match, i, object, group;

    if (raw) {
      raw.lastIndex = re.lastIndex;
      result = call(patchedExec, raw, str);
      re.lastIndex = raw.lastIndex;
      return result;
    }

    var groups = state.groups;
    var sticky = UNSUPPORTED_Y && re.sticky;
    var flags = call(regexpFlags, re);
    var source = re.source;
    var charsAdded = 0;
    var strCopy = str;

    if (sticky) {
      flags = replace(flags, 'y', '');
      if (indexOf(flags, 'g') === -1) {
        flags += 'g';
      }

      strCopy = stringSlice(str, re.lastIndex);
      // Support anchored sticky behavior.
      if (re.lastIndex > 0 && (!re.multiline || re.multiline && charAt(str, re.lastIndex - 1) !== '\n')) {
        source = '(?: ' + source + ')';
        strCopy = ' ' + strCopy;
        charsAdded++;
      }
      // ^(? + rx + ) is needed, in combination with some str slicing, to
      // simulate the 'y' flag.
      reCopy = new RegExp('^(?:' + source + ')', flags);
    }

    if (NPCG_INCLUDED) {
      reCopy = new RegExp('^' + source + '$(?!\\s)', flags);
    }
    if (UPDATES_LAST_INDEX_WRONG) lastIndex = re.lastIndex;

    match = call(nativeExec, sticky ? reCopy : re, strCopy);

    if (sticky) {
      if (match) {
        match.input = stringSlice(match.input, charsAdded);
        match[0] = stringSlice(match[0], charsAdded);
        match.index = re.lastIndex;
        re.lastIndex += match[0].length;
      } else re.lastIndex = 0;
    } else if (UPDATES_LAST_INDEX_WRONG && match) {
      re.lastIndex = re.global ? match.index + match[0].length : lastIndex;
    }
    if (NPCG_INCLUDED && match && match.length > 1) {
      // Fix browsers whose `exec` methods don't consistently return `undefined`
      // for NPCG, like IE8. NOTE: This doesn' work for /(.?)?/
      call(nativeReplace, match[0], reCopy, function () {
        for (i = 1; i < arguments.length - 2; i++) {
          if (arguments[i] === undefined) match[i] = undefined;
        }
      });
    }

    if (match && groups) {
      match.groups = object = create(null);
      for (i = 0; i < groups.length; i++) {
        group = groups[i];
        object[group[0]] = match[group[1]];
      }
    }

    return match;
  };
}

module.exports = patchedExec;


/***/ }),
/* 75 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var hasOwn = __webpack_require__(8);
var isCallable = __webpack_require__(5);
var toObject = __webpack_require__(16);
var sharedKey = __webpack_require__(60);
var CORRECT_PROTOTYPE_GETTER = __webpack_require__(121);

var IE_PROTO = sharedKey('IE_PROTO');
var Object = global.Object;
var ObjectPrototype = Object.prototype;

// `Object.getPrototypeOf` method
// https://tc39.es/ecma262/#sec-object.getprototypeof
module.exports = CORRECT_PROTOTYPE_GETTER ? Object.getPrototypeOf : function (O) {
  var object = toObject(O);
  if (hasOwn(object, IE_PROTO)) return object[IE_PROTO];
  var constructor = object.constructor;
  if (isCallable(constructor) && object instanceof constructor) {
    return constructor.prototype;
  } return object instanceof Object ? ObjectPrototype : null;
};


/***/ }),
/* 76 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var apply = __webpack_require__(73);
var call = __webpack_require__(7);
var uncurryThis = __webpack_require__(1);
var fixRegExpWellKnownSymbolLogic = __webpack_require__(90);
var fails = __webpack_require__(2);
var anObject = __webpack_require__(6);
var isCallable = __webpack_require__(5);
var toIntegerOrInfinity = __webpack_require__(65);
var toLength = __webpack_require__(52);
var toString = __webpack_require__(13);
var requireObjectCoercible = __webpack_require__(19);
var advanceStringIndex = __webpack_require__(92);
var getMethod = __webpack_require__(36);
var getSubstitution = __webpack_require__(133);
var regExpExec = __webpack_require__(93);
var wellKnownSymbol = __webpack_require__(4);

var REPLACE = wellKnownSymbol('replace');
var max = Math.max;
var min = Math.min;
var concat = uncurryThis([].concat);
var push = uncurryThis([].push);
var stringIndexOf = uncurryThis(''.indexOf);
var stringSlice = uncurryThis(''.slice);

var maybeToString = function (it) {
  return it === undefined ? it : String(it);
};

// IE <= 11 replaces $0 with the whole match, as if it was $&
// https://stackoverflow.com/questions/6024666/getting-ie-to-replace-a-regex-with-the-literal-string-0
var REPLACE_KEEPS_$0 = (function () {
  // eslint-disable-next-line regexp/prefer-escape-replacement-dollar-char -- required for testing
  return 'a'.replace(/./, '$0') === '$0';
})();

// Safari <= 13.0.3(?) substitutes nth capture where n>m with an empty string
var REGEXP_REPLACE_SUBSTITUTES_UNDEFINED_CAPTURE = (function () {
  if (/./[REPLACE]) {
    return /./[REPLACE]('a', '$0') === '';
  }
  return false;
})();

var REPLACE_SUPPORTS_NAMED_GROUPS = !fails(function () {
  var re = /./;
  re.exec = function () {
    var result = [];
    result.groups = { a: '7' };
    return result;
  };
  // eslint-disable-next-line regexp/no-useless-dollar-replacements -- false positive
  return ''.replace(re, '$<a>') !== '7';
});

// @@replace logic
fixRegExpWellKnownSymbolLogic('replace', function (_, nativeReplace, maybeCallNative) {
  var UNSAFE_SUBSTITUTE = REGEXP_REPLACE_SUBSTITUTES_UNDEFINED_CAPTURE ? '$' : '$0';

  return [
    // `String.prototype.replace` method
    // https://tc39.es/ecma262/#sec-string.prototype.replace
    function replace(searchValue, replaceValue) {
      var O = requireObjectCoercible(this);
      var replacer = searchValue == undefined ? undefined : getMethod(searchValue, REPLACE);
      return replacer
        ? call(replacer, searchValue, O, replaceValue)
        : call(nativeReplace, toString(O), searchValue, replaceValue);
    },
    // `RegExp.prototype[@@replace]` method
    // https://tc39.es/ecma262/#sec-regexp.prototype-@@replace
    function (string, replaceValue) {
      var rx = anObject(this);
      var S = toString(string);

      if (
        typeof replaceValue == 'string' &&
        stringIndexOf(replaceValue, UNSAFE_SUBSTITUTE) === -1 &&
        stringIndexOf(replaceValue, '$<') === -1
      ) {
        var res = maybeCallNative(nativeReplace, rx, S, replaceValue);
        if (res.done) return res.value;
      }

      var functionalReplace = isCallable(replaceValue);
      if (!functionalReplace) replaceValue = toString(replaceValue);

      var global = rx.global;
      if (global) {
        var fullUnicode = rx.unicode;
        rx.lastIndex = 0;
      }
      var results = [];
      while (true) {
        var result = regExpExec(rx, S);
        if (result === null) break;

        push(results, result);
        if (!global) break;

        var matchStr = toString(result[0]);
        if (matchStr === '') rx.lastIndex = advanceStringIndex(S, toLength(rx.lastIndex), fullUnicode);
      }

      var accumulatedResult = '';
      var nextSourcePosition = 0;
      for (var i = 0; i < results.length; i++) {
        result = results[i];

        var matched = toString(result[0]);
        var position = max(min(toIntegerOrInfinity(result.index), S.length), 0);
        var captures = [];
        // NOTE: This is equivalent to
        //   captures = result.slice(1).map(maybeToString)
        // but for some reason `nativeSlice.call(result, 1, result.length)` (called in
        // the slice polyfill when slicing native arrays) "doesn't work" in safari 9 and
        // causes a crash (https://pastebin.com/N21QzeQA) when trying to debug it.
        for (var j = 1; j < result.length; j++) push(captures, maybeToString(result[j]));
        var namedCaptures = result.groups;
        if (functionalReplace) {
          var replacerArgs = concat([matched], captures, position, S);
          if (namedCaptures !== undefined) push(replacerArgs, namedCaptures);
          var replacement = toString(apply(replaceValue, undefined, replacerArgs));
        } else {
          replacement = getSubstitution(matched, S, position, captures, namedCaptures, replaceValue);
        }
        if (position >= nextSourcePosition) {
          accumulatedResult += stringSlice(S, nextSourcePosition, position) + replacement;
          nextSourcePosition = position + matched.length;
        }
      }
      return accumulatedResult + stringSlice(S, nextSourcePosition);
    }
  ];
}, !REPLACE_SUPPORTS_NAMED_GROUPS || !REPLACE_KEEPS_$0 || REGEXP_REPLACE_SUBSTITUTES_UNDEFINED_CAPTURE);


/***/ }),
/* 77 */
/***/ (function(module, exports, __webpack_require__) {

var DESCRIPTORS = __webpack_require__(10);
var FUNCTION_NAME_EXISTS = __webpack_require__(51).EXISTS;
var uncurryThis = __webpack_require__(1);
var defineProperty = __webpack_require__(12).f;

var FunctionPrototype = Function.prototype;
var functionToString = uncurryThis(FunctionPrototype.toString);
var nameRE = /function\b(?:\s|\/\*[\S\s]*?\*\/|\/\/[^\n\r]*[\n\r]+)*([^\s(/]*)/;
var regExpExec = uncurryThis(nameRE.exec);
var NAME = 'name';

// Function instances `.name` property
// https://tc39.es/ecma262/#sec-function-instances-name
if (DESCRIPTORS && !FUNCTION_NAME_EXISTS) {
  defineProperty(FunctionPrototype, NAME, {
    configurable: true,
    get: function () {
      try {
        return regExpExec(nameRE, functionToString(this))[1];
      } catch (error) {
        return '';
      }
    }
  });
}


/***/ }),
/* 78 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var from = __webpack_require__(173);
var checkCorrectnessOfIteration = __webpack_require__(130);

var INCORRECT_ITERATION = !checkCorrectnessOfIteration(function (iterable) {
  // eslint-disable-next-line es-x/no-array-from -- required for testing
  Array.from(iterable);
});

// `Array.from` method
// https://tc39.es/ecma262/#sec-array.from
$({ target: 'Array', stat: true, forced: INCORRECT_ITERATION }, {
  from: from
});


/***/ }),
/* 79 */
/***/ (function(module, exports, __webpack_require__) {

var wellKnownSymbol = __webpack_require__(4);

var TO_STRING_TAG = wellKnownSymbol('toStringTag');
var test = {};

test[TO_STRING_TAG] = 'z';

module.exports = String(test) === '[object z]';


/***/ }),
/* 80 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var setGlobal = __webpack_require__(81);

var SHARED = '__core-js_shared__';
var store = global[SHARED] || setGlobal(SHARED, {});

module.exports = store;


/***/ }),
/* 81 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);

// eslint-disable-next-line es-x/no-object-defineproperty -- safe
var defineProperty = Object.defineProperty;

module.exports = function (key, value) {
  try {
    defineProperty(global, key, { value: value, configurable: true, writable: true });
  } catch (error) {
    global[key] = value;
  } return value;
};


/***/ }),
/* 82 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var userAgent = __webpack_require__(143);

var process = global.process;
var Deno = global.Deno;
var versions = process && process.versions || Deno && Deno.version;
var v8 = versions && versions.v8;
var match, version;

if (v8) {
  match = v8.split('.');
  // in old Chrome, versions of V8 isn't V8 = Chrome / 10
  // but their correct versions are not interesting for us
  version = match[0] > 0 && match[0] < 4 ? 1 : +(match[0] + match[1]);
}

// BrowserFS NodeJS `process` polyfill incorrectly set `.v8` to `0.0`
// so check `userAgent` even if `.v8` exists, but 0
if (!version && userAgent) {
  match = userAgent.match(/Edge\/(\d+)/);
  if (!match || match[1] >= 74) {
    match = userAgent.match(/Chrome\/(\d+)/);
    if (match) version = +match[1];
  }
}

module.exports = version;


/***/ }),
/* 83 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var isObject = __webpack_require__(11);

var document = global.document;
// typeof document.createElement is 'object' in old IE
var EXISTS = isObject(document) && isObject(document.createElement);

module.exports = function (it) {
  return EXISTS ? document.createElement(it) : {};
};


/***/ }),
/* 84 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);
var isCallable = __webpack_require__(5);
var store = __webpack_require__(80);

var functionToString = uncurryThis(Function.toString);

// this helper broken in `core-js@3.4.1-3.4.4`, so we can't use `shared` helper
if (!isCallable(store.inspectSource)) {
  store.inspectSource = function (it) {
    return functionToString(it);
  };
}

module.exports = store.inspectSource;


/***/ }),
/* 85 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $propertyIsEnumerable = {}.propertyIsEnumerable;
// eslint-disable-next-line es-x/no-object-getownpropertydescriptor -- safe
var getOwnPropertyDescriptor = Object.getOwnPropertyDescriptor;

// Nashorn ~ JDK8 bug
var NASHORN_BUG = getOwnPropertyDescriptor && !$propertyIsEnumerable.call({ 1: 2 }, 1);

// `Object.prototype.propertyIsEnumerable` method implementation
// https://tc39.es/ecma262/#sec-object.prototype.propertyisenumerable
exports.f = NASHORN_BUG ? function propertyIsEnumerable(V) {
  var descriptor = getOwnPropertyDescriptor(this, V);
  return !!descriptor && descriptor.enumerable;
} : $propertyIsEnumerable;


/***/ }),
/* 86 */
/***/ (function(module, exports) {

// IE8- don't enum bug keys
module.exports = [
  'constructor',
  'hasOwnProperty',
  'isPrototypeOf',
  'propertyIsEnumerable',
  'toLocaleString',
  'toString',
  'valueOf'
];


/***/ }),
/* 87 */
/***/ (function(module, exports, __webpack_require__) {

var fails = __webpack_require__(2);
var isCallable = __webpack_require__(5);

var replacement = /#|\.prototype\./;

var isForced = function (feature, detection) {
  var value = data[normalize(feature)];
  return value == POLYFILL ? true
    : value == NATIVE ? false
    : isCallable(detection) ? fails(detection)
    : !!detection;
};

var normalize = isForced.normalize = function (string) {
  return String(string).replace(replacement, '.').toLowerCase();
};

var data = isForced.data = {};
var NATIVE = isForced.NATIVE = 'N';
var POLYFILL = isForced.POLYFILL = 'P';

module.exports = isForced;


/***/ }),
/* 88 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);

module.exports = uncurryThis([].slice);


/***/ }),
/* 89 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var anObject = __webpack_require__(6);

// `RegExp.prototype.flags` getter implementation
// https://tc39.es/ecma262/#sec-get-regexp.prototype.flags
module.exports = function () {
  var that = anObject(this);
  var result = '';
  if (that.global) result += 'g';
  if (that.ignoreCase) result += 'i';
  if (that.multiline) result += 'm';
  if (that.dotAll) result += 's';
  if (that.unicode) result += 'u';
  if (that.sticky) result += 'y';
  return result;
};


/***/ }),
/* 90 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

// TODO: Remove from `core-js@4` since it's moved to entry points
__webpack_require__(15);
var uncurryThis = __webpack_require__(1);
var redefine = __webpack_require__(17);
var regexpExec = __webpack_require__(74);
var fails = __webpack_require__(2);
var wellKnownSymbol = __webpack_require__(4);
var createNonEnumerableProperty = __webpack_require__(34);

var SPECIES = wellKnownSymbol('species');
var RegExpPrototype = RegExp.prototype;

module.exports = function (KEY, exec, FORCED, SHAM) {
  var SYMBOL = wellKnownSymbol(KEY);

  var DELEGATES_TO_SYMBOL = !fails(function () {
    // String methods call symbol-named RegEp methods
    var O = {};
    O[SYMBOL] = function () { return 7; };
    return ''[KEY](O) != 7;
  });

  var DELEGATES_TO_EXEC = DELEGATES_TO_SYMBOL && !fails(function () {
    // Symbol-named RegExp methods call .exec
    var execCalled = false;
    var re = /a/;

    if (KEY === 'split') {
      // We can't use real regex here since it causes deoptimization
      // and serious performance degradation in V8
      // https://github.com/zloirock/core-js/issues/306
      re = {};
      // RegExp[@@split] doesn't call the regex's exec method, but first creates
      // a new one. We need to return the patched regex when creating the new one.
      re.constructor = {};
      re.constructor[SPECIES] = function () { return re; };
      re.flags = '';
      re[SYMBOL] = /./[SYMBOL];
    }

    re.exec = function () { execCalled = true; return null; };

    re[SYMBOL]('');
    return !execCalled;
  });

  if (
    !DELEGATES_TO_SYMBOL ||
    !DELEGATES_TO_EXEC ||
    FORCED
  ) {
    var uncurriedNativeRegExpMethod = uncurryThis(/./[SYMBOL]);
    var methods = exec(SYMBOL, ''[KEY], function (nativeMethod, regexp, str, arg2, forceStringMethod) {
      var uncurriedNativeMethod = uncurryThis(nativeMethod);
      var $exec = regexp.exec;
      if ($exec === regexpExec || $exec === RegExpPrototype.exec) {
        if (DELEGATES_TO_SYMBOL && !forceStringMethod) {
          // The native String method already delegates to @@method (this
          // polyfilled function), leasing to infinite recursion.
          // We avoid it by directly calling the native @@method method.
          return { done: true, value: uncurriedNativeRegExpMethod(regexp, str, arg2) };
        }
        return { done: true, value: uncurriedNativeMethod(str, regexp, arg2) };
      }
      return { done: false };
    });

    redefine(String.prototype, KEY, methods[0]);
    redefine(RegExpPrototype, SYMBOL, methods[1]);
  }

  if (SHAM) createNonEnumerableProperty(RegExpPrototype[SYMBOL], 'sham', true);
};


/***/ }),
/* 91 */
/***/ (function(module, exports, __webpack_require__) {

var isObject = __webpack_require__(11);
var classof = __webpack_require__(37);
var wellKnownSymbol = __webpack_require__(4);

var MATCH = wellKnownSymbol('match');

// `IsRegExp` abstract operation
// https://tc39.es/ecma262/#sec-isregexp
module.exports = function (it) {
  var isRegExp;
  return isObject(it) && ((isRegExp = it[MATCH]) !== undefined ? !!isRegExp : classof(it) == 'RegExp');
};


/***/ }),
/* 92 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var charAt = __webpack_require__(119).charAt;

// `AdvanceStringIndex` abstract operation
// https://tc39.es/ecma262/#sec-advancestringindex
module.exports = function (S, index, unicode) {
  return index + (unicode ? charAt(S, index).length : 1);
};


/***/ }),
/* 93 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var call = __webpack_require__(7);
var anObject = __webpack_require__(6);
var isCallable = __webpack_require__(5);
var classof = __webpack_require__(37);
var regexpExec = __webpack_require__(74);

var TypeError = global.TypeError;

// `RegExpExec` abstract operation
// https://tc39.es/ecma262/#sec-regexpexec
module.exports = function (R, S) {
  var exec = R.exec;
  if (isCallable(exec)) {
    var result = call(exec, R, S);
    if (result !== null) anObject(result);
    return result;
  }
  if (classof(R) === 'RegExp') return call(regexpExec, R, S);
  throw TypeError('RegExp#exec called on incompatible receiver');
};


/***/ }),
/* 94 */
/***/ (function(module, exports, __webpack_require__) {

var wellKnownSymbol = __webpack_require__(4);
var create = __webpack_require__(38);
var definePropertyModule = __webpack_require__(12);

var UNSCOPABLES = wellKnownSymbol('unscopables');
var ArrayPrototype = Array.prototype;

// Array.prototype[@@unscopables]
// https://tc39.es/ecma262/#sec-array.prototype-@@unscopables
if (ArrayPrototype[UNSCOPABLES] == undefined) {
  definePropertyModule.f(ArrayPrototype, UNSCOPABLES, {
    configurable: true,
    value: create(null)
  });
}

// add a key to Array.prototype[@@unscopables]
module.exports = function (key) {
  ArrayPrototype[UNSCOPABLES][key] = true;
};


/***/ }),
/* 95 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var call = __webpack_require__(7);
var IS_PURE = __webpack_require__(33);
var FunctionName = __webpack_require__(51);
var isCallable = __webpack_require__(5);
var createIteratorConstructor = __webpack_require__(161);
var getPrototypeOf = __webpack_require__(75);
var setPrototypeOf = __webpack_require__(122);
var setToStringTag = __webpack_require__(72);
var createNonEnumerableProperty = __webpack_require__(34);
var redefine = __webpack_require__(17);
var wellKnownSymbol = __webpack_require__(4);
var Iterators = __webpack_require__(56);
var IteratorsCore = __webpack_require__(120);

var PROPER_FUNCTION_NAME = FunctionName.PROPER;
var CONFIGURABLE_FUNCTION_NAME = FunctionName.CONFIGURABLE;
var IteratorPrototype = IteratorsCore.IteratorPrototype;
var BUGGY_SAFARI_ITERATORS = IteratorsCore.BUGGY_SAFARI_ITERATORS;
var ITERATOR = wellKnownSymbol('iterator');
var KEYS = 'keys';
var VALUES = 'values';
var ENTRIES = 'entries';

var returnThis = function () { return this; };

module.exports = function (Iterable, NAME, IteratorConstructor, next, DEFAULT, IS_SET, FORCED) {
  createIteratorConstructor(IteratorConstructor, NAME, next);

  var getIterationMethod = function (KIND) {
    if (KIND === DEFAULT && defaultIterator) return defaultIterator;
    if (!BUGGY_SAFARI_ITERATORS && KIND in IterablePrototype) return IterablePrototype[KIND];
    switch (KIND) {
      case KEYS: return function keys() { return new IteratorConstructor(this, KIND); };
      case VALUES: return function values() { return new IteratorConstructor(this, KIND); };
      case ENTRIES: return function entries() { return new IteratorConstructor(this, KIND); };
    } return function () { return new IteratorConstructor(this); };
  };

  var TO_STRING_TAG = NAME + ' Iterator';
  var INCORRECT_VALUES_NAME = false;
  var IterablePrototype = Iterable.prototype;
  var nativeIterator = IterablePrototype[ITERATOR]
    || IterablePrototype['@@iterator']
    || DEFAULT && IterablePrototype[DEFAULT];
  var defaultIterator = !BUGGY_SAFARI_ITERATORS && nativeIterator || getIterationMethod(DEFAULT);
  var anyNativeIterator = NAME == 'Array' ? IterablePrototype.entries || nativeIterator : nativeIterator;
  var CurrentIteratorPrototype, methods, KEY;

  // fix native
  if (anyNativeIterator) {
    CurrentIteratorPrototype = getPrototypeOf(anyNativeIterator.call(new Iterable()));
    if (CurrentIteratorPrototype !== Object.prototype && CurrentIteratorPrototype.next) {
      if (!IS_PURE && getPrototypeOf(CurrentIteratorPrototype) !== IteratorPrototype) {
        if (setPrototypeOf) {
          setPrototypeOf(CurrentIteratorPrototype, IteratorPrototype);
        } else if (!isCallable(CurrentIteratorPrototype[ITERATOR])) {
          redefine(CurrentIteratorPrototype, ITERATOR, returnThis);
        }
      }
      // Set @@toStringTag to native iterators
      setToStringTag(CurrentIteratorPrototype, TO_STRING_TAG, true, true);
      if (IS_PURE) Iterators[TO_STRING_TAG] = returnThis;
    }
  }

  // fix Array.prototype.{ values, @@iterator }.name in V8 / FF
  if (PROPER_FUNCTION_NAME && DEFAULT == VALUES && nativeIterator && nativeIterator.name !== VALUES) {
    if (!IS_PURE && CONFIGURABLE_FUNCTION_NAME) {
      createNonEnumerableProperty(IterablePrototype, 'name', VALUES);
    } else {
      INCORRECT_VALUES_NAME = true;
      defaultIterator = function values() { return call(nativeIterator, this); };
    }
  }

  // export additional methods
  if (DEFAULT) {
    methods = {
      values: getIterationMethod(VALUES),
      keys: IS_SET ? defaultIterator : getIterationMethod(KEYS),
      entries: getIterationMethod(ENTRIES)
    };
    if (FORCED) for (KEY in methods) {
      if (BUGGY_SAFARI_ITERATORS || INCORRECT_VALUES_NAME || !(KEY in IterablePrototype)) {
        redefine(IterablePrototype, KEY, methods[KEY]);
      }
    } else $({ target: NAME, proto: true, forced: BUGGY_SAFARI_ITERATORS || INCORRECT_VALUES_NAME }, methods);
  }

  // define iterator
  if ((!IS_PURE || FORCED) && IterablePrototype[ITERATOR] !== defaultIterator) {
    redefine(IterablePrototype, ITERATOR, defaultIterator, { name: DEFAULT });
  }
  Iterators[NAME] = defaultIterator;

  return methods;
};


/***/ }),
/* 96 */
/***/ (function(module, exports, __webpack_require__) {

var classof = __webpack_require__(61);
var getMethod = __webpack_require__(36);
var Iterators = __webpack_require__(56);
var wellKnownSymbol = __webpack_require__(4);

var ITERATOR = wellKnownSymbol('iterator');

module.exports = function (it) {
  if (it != undefined) return getMethod(it, ITERATOR)
    || getMethod(it, '@@iterator')
    || Iterators[classof(it)];
};


/***/ }),
/* 97 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var global = __webpack_require__(0);
var isArray = __webpack_require__(66);
var isConstructor = __webpack_require__(67);
var isObject = __webpack_require__(11);
var toAbsoluteIndex = __webpack_require__(68);
var lengthOfArrayLike = __webpack_require__(27);
var toIndexedObject = __webpack_require__(18);
var createProperty = __webpack_require__(54);
var wellKnownSymbol = __webpack_require__(4);
var arrayMethodHasSpeciesSupport = __webpack_require__(70);
var un$Slice = __webpack_require__(88);

var HAS_SPECIES_SUPPORT = arrayMethodHasSpeciesSupport('slice');

var SPECIES = wellKnownSymbol('species');
var Array = global.Array;
var max = Math.max;

// `Array.prototype.slice` method
// https://tc39.es/ecma262/#sec-array.prototype.slice
// fallback for not array-like ES3 strings and DOM objects
$({ target: 'Array', proto: true, forced: !HAS_SPECIES_SUPPORT }, {
  slice: function slice(start, end) {
    var O = toIndexedObject(this);
    var length = lengthOfArrayLike(O);
    var k = toAbsoluteIndex(start, length);
    var fin = toAbsoluteIndex(end === undefined ? length : end, length);
    // inline `ArraySpeciesCreate` for usage native `Array#slice` where it's possible
    var Constructor, result, n;
    if (isArray(O)) {
      Constructor = O.constructor;
      // cross-realm fallback
      if (isConstructor(Constructor) && (Constructor === Array || isArray(Constructor.prototype))) {
        Constructor = undefined;
      } else if (isObject(Constructor)) {
        Constructor = Constructor[SPECIES];
        if (Constructor === null) Constructor = undefined;
      }
      if (Constructor === Array || Constructor === undefined) {
        return un$Slice(O, k, fin);
      }
    }
    result = new (Constructor === undefined ? Array : Constructor)(max(fin - k, 0));
    for (n = 0; k < fin; k++, n++) if (k in O) createProperty(result, n, O[k]);
    result.length = n;
    return result;
  }
});


/***/ }),
/* 98 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var call = __webpack_require__(7);
var fixRegExpWellKnownSymbolLogic = __webpack_require__(90);
var anObject = __webpack_require__(6);
var toLength = __webpack_require__(52);
var toString = __webpack_require__(13);
var requireObjectCoercible = __webpack_require__(19);
var getMethod = __webpack_require__(36);
var advanceStringIndex = __webpack_require__(92);
var regExpExec = __webpack_require__(93);

// @@match logic
fixRegExpWellKnownSymbolLogic('match', function (MATCH, nativeMatch, maybeCallNative) {
  return [
    // `String.prototype.match` method
    // https://tc39.es/ecma262/#sec-string.prototype.match
    function match(regexp) {
      var O = requireObjectCoercible(this);
      var matcher = regexp == undefined ? undefined : getMethod(regexp, MATCH);
      return matcher ? call(matcher, regexp, O) : new RegExp(regexp)[MATCH](toString(O));
    },
    // `RegExp.prototype[@@match]` method
    // https://tc39.es/ecma262/#sec-regexp.prototype-@@match
    function (string) {
      var rx = anObject(this);
      var S = toString(string);
      var res = maybeCallNative(nativeMatch, rx, S);

      if (res.done) return res.value;

      if (!rx.global) return regExpExec(rx, S);

      var fullUnicode = rx.unicode;
      rx.lastIndex = 0;
      var A = [];
      var n = 0;
      var result;
      while ((result = regExpExec(rx, S)) !== null) {
        var matchStr = toString(result[0]);
        A[n] = matchStr;
        if (matchStr === '') rx.lastIndex = advanceStringIndex(S, toLength(rx.lastIndex), fullUnicode);
        n++;
      }
      return n === 0 ? null : A;
    }
  ];
});


/***/ }),
/* 99 */
/***/ (function(module, exports, __webpack_require__) {

/* eslint-disable es-x/no-symbol -- required for testing */
var NATIVE_SYMBOL = __webpack_require__(40);

module.exports = NATIVE_SYMBOL
  && !Symbol.sham
  && typeof Symbol.iterator == 'symbol';


/***/ }),
/* 100 */
/***/ (function(module, exports, __webpack_require__) {

var DESCRIPTORS = __webpack_require__(10);
var fails = __webpack_require__(2);
var createElement = __webpack_require__(83);

// Thanks to IE8 for its funny defineProperty
module.exports = !DESCRIPTORS && !fails(function () {
  // eslint-disable-next-line es-x/no-object-defineproperty -- required for testing
  return Object.defineProperty(createElement('div'), 'a', {
    get: function () { return 7; }
  }).a != 7;
});


/***/ }),
/* 101 */
/***/ (function(module, exports, __webpack_require__) {

var DESCRIPTORS = __webpack_require__(10);
var fails = __webpack_require__(2);

// V8 ~ Chrome 36-
// https://bugs.chromium.org/p/v8/issues/detail?id=3334
module.exports = DESCRIPTORS && fails(function () {
  // eslint-disable-next-line es-x/no-object-defineproperty -- required for testing
  return Object.defineProperty(function () { /* empty */ }, 'prototype', {
    value: 42,
    writable: false
  }).prototype != 42;
});


/***/ }),
/* 102 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var call = __webpack_require__(7);
var isObject = __webpack_require__(11);
var isSymbol = __webpack_require__(47);
var getMethod = __webpack_require__(36);
var ordinaryToPrimitive = __webpack_require__(144);
var wellKnownSymbol = __webpack_require__(4);

var TypeError = global.TypeError;
var TO_PRIMITIVE = wellKnownSymbol('toPrimitive');

// `ToPrimitive` abstract operation
// https://tc39.es/ecma262/#sec-toprimitive
module.exports = function (input, pref) {
  if (!isObject(input) || isSymbol(input)) return input;
  var exoticToPrim = getMethod(input, TO_PRIMITIVE);
  var result;
  if (exoticToPrim) {
    if (pref === undefined) pref = 'default';
    result = call(exoticToPrim, input, pref);
    if (!isObject(result) || isSymbol(result)) return result;
    throw TypeError("Can't convert object to primitive value");
  }
  if (pref === undefined) pref = 'number';
  return ordinaryToPrimitive(input, pref);
};


/***/ }),
/* 103 */
/***/ (function(module, exports) {

// iterable DOM collections
// flag - `iterable` interface - 'entries', 'keys', 'values', 'forEach' methods
module.exports = {
  CSSRuleList: 0,
  CSSStyleDeclaration: 0,
  CSSValueList: 0,
  ClientRectList: 0,
  DOMRectList: 0,
  DOMStringList: 0,
  DOMTokenList: 1,
  DataTransferItemList: 0,
  FileList: 0,
  HTMLAllCollection: 0,
  HTMLCollection: 0,
  HTMLFormElement: 0,
  HTMLSelectElement: 0,
  MediaList: 0,
  MimeTypeArray: 0,
  NamedNodeMap: 0,
  NodeList: 1,
  PaintRequestList: 0,
  Plugin: 0,
  PluginArray: 0,
  SVGLengthList: 0,
  SVGNumberList: 0,
  SVGPathSegList: 0,
  SVGPointList: 0,
  SVGStringList: 0,
  SVGTransformList: 0,
  SourceBufferList: 0,
  StyleSheetList: 0,
  TextTrackCueList: 0,
  TextTrackList: 0,
  TouchList: 0
};


/***/ }),
/* 104 */
/***/ (function(module, exports, __webpack_require__) {

// in old WebKit versions, `element.classList` is not an instance of global `DOMTokenList`
var documentCreateElement = __webpack_require__(83);

var classList = documentCreateElement('span').classList;
var DOMTokenListPrototype = classList && classList.constructor && classList.constructor.prototype;

module.exports = DOMTokenListPrototype === Object.prototype ? undefined : DOMTokenListPrototype;


/***/ }),
/* 105 */
/***/ (function(module, exports, __webpack_require__) {

var arraySpeciesConstructor = __webpack_require__(148);

// `ArraySpeciesCreate` abstract operation
// https://tc39.es/ecma262/#sec-arrayspeciescreate
module.exports = function (originalArray, length) {
  return new (arraySpeciesConstructor(originalArray))(length === 0 ? 0 : length);
};


/***/ }),
/* 106 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var fails = __webpack_require__(2);

module.exports = function (METHOD_NAME, argument) {
  var method = [][METHOD_NAME];
  return !!method && fails(function () {
    // eslint-disable-next-line no-useless-call -- required for testing
    method.call(null, argument || function () { return 1; }, 1);
  });
};


/***/ }),
/* 107 */
/***/ (function(module, exports, __webpack_require__) {

var hasOwn = __webpack_require__(8);
var ownKeys = __webpack_require__(108);
var getOwnPropertyDescriptorModule = __webpack_require__(28);
var definePropertyModule = __webpack_require__(12);

module.exports = function (target, source, exceptions) {
  var keys = ownKeys(source);
  var defineProperty = definePropertyModule.f;
  var getOwnPropertyDescriptor = getOwnPropertyDescriptorModule.f;
  for (var i = 0; i < keys.length; i++) {
    var key = keys[i];
    if (!hasOwn(target, key) && !(exceptions && hasOwn(exceptions, key))) {
      defineProperty(target, key, getOwnPropertyDescriptor(source, key));
    }
  }
};


/***/ }),
/* 108 */
/***/ (function(module, exports, __webpack_require__) {

var getBuiltIn = __webpack_require__(20);
var uncurryThis = __webpack_require__(1);
var getOwnPropertyNamesModule = __webpack_require__(53);
var getOwnPropertySymbolsModule = __webpack_require__(69);
var anObject = __webpack_require__(6);

var concat = uncurryThis([].concat);

// all object keys, includes non-enumerable and symbols
module.exports = getBuiltIn('Reflect', 'ownKeys') || function ownKeys(it) {
  var keys = getOwnPropertyNamesModule.f(anObject(it));
  var getOwnPropertySymbols = getOwnPropertySymbolsModule.f;
  return getOwnPropertySymbols ? concat(keys, getOwnPropertySymbols(it)) : keys;
};


/***/ }),
/* 109 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);
var hasOwn = __webpack_require__(8);
var toIndexedObject = __webpack_require__(18);
var indexOf = __webpack_require__(110).indexOf;
var hiddenKeys = __webpack_require__(50);

var push = uncurryThis([].push);

module.exports = function (object, names) {
  var O = toIndexedObject(object);
  var i = 0;
  var result = [];
  var key;
  for (key in O) !hasOwn(hiddenKeys, key) && hasOwn(O, key) && push(result, key);
  // Don't enum bug & hidden keys
  while (names.length > i) if (hasOwn(O, key = names[i++])) {
    ~indexOf(result, key) || push(result, key);
  }
  return result;
};


/***/ }),
/* 110 */
/***/ (function(module, exports, __webpack_require__) {

var toIndexedObject = __webpack_require__(18);
var toAbsoluteIndex = __webpack_require__(68);
var lengthOfArrayLike = __webpack_require__(27);

// `Array.prototype.{ indexOf, includes }` methods implementation
var createMethod = function (IS_INCLUDES) {
  return function ($this, el, fromIndex) {
    var O = toIndexedObject($this);
    var length = lengthOfArrayLike(O);
    var index = toAbsoluteIndex(fromIndex, length);
    var value;
    // Array#includes uses SameValueZero equality algorithm
    // eslint-disable-next-line no-self-compare -- NaN check
    if (IS_INCLUDES && el != el) while (length > index) {
      value = O[index++];
      // eslint-disable-next-line no-self-compare -- NaN check
      if (value != value) return true;
    // Array#indexOf ignores holes, Array#includes - not
    } else for (;length > index; index++) {
      if ((IS_INCLUDES || index in O) && O[index] === el) return IS_INCLUDES || index || 0;
    } return !IS_INCLUDES && -1;
  };
};

module.exports = {
  // `Array.prototype.includes` method
  // https://tc39.es/ecma262/#sec-array.prototype.includes
  includes: createMethod(true),
  // `Array.prototype.indexOf` method
  // https://tc39.es/ecma262/#sec-array.prototype.indexof
  indexOf: createMethod(false)
};


/***/ }),
/* 111 */
/***/ (function(module, exports, __webpack_require__) {

var DESCRIPTORS = __webpack_require__(10);
var V8_PROTOTYPE_DEFINE_BUG = __webpack_require__(101);
var definePropertyModule = __webpack_require__(12);
var anObject = __webpack_require__(6);
var toIndexedObject = __webpack_require__(18);
var objectKeys = __webpack_require__(71);

// `Object.defineProperties` method
// https://tc39.es/ecma262/#sec-object.defineproperties
// eslint-disable-next-line es-x/no-object-defineproperties -- safe
exports.f = DESCRIPTORS && !V8_PROTOTYPE_DEFINE_BUG ? Object.defineProperties : function defineProperties(O, Properties) {
  anObject(O);
  var props = toIndexedObject(Properties);
  var keys = objectKeys(Properties);
  var length = keys.length;
  var index = 0;
  var key;
  while (length > index) definePropertyModule.f(O, key = keys[index++], props[key]);
  return O;
};


/***/ }),
/* 112 */
/***/ (function(module, exports, __webpack_require__) {

/* eslint-disable es-x/no-object-getownpropertynames -- safe */
var classof = __webpack_require__(37);
var toIndexedObject = __webpack_require__(18);
var $getOwnPropertyNames = __webpack_require__(53).f;
var arraySlice = __webpack_require__(113);

var windowNames = typeof window == 'object' && window && Object.getOwnPropertyNames
  ? Object.getOwnPropertyNames(window) : [];

var getWindowNames = function (it) {
  try {
    return $getOwnPropertyNames(it);
  } catch (error) {
    return arraySlice(windowNames);
  }
};

// fallback for IE11 buggy Object.getOwnPropertyNames with iframe and window
module.exports.f = function getOwnPropertyNames(it) {
  return windowNames && classof(it) == 'Window'
    ? getWindowNames(it)
    : $getOwnPropertyNames(toIndexedObject(it));
};


/***/ }),
/* 113 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var toAbsoluteIndex = __webpack_require__(68);
var lengthOfArrayLike = __webpack_require__(27);
var createProperty = __webpack_require__(54);

var Array = global.Array;
var max = Math.max;

module.exports = function (O, start, end) {
  var length = lengthOfArrayLike(O);
  var k = toAbsoluteIndex(start, length);
  var fin = toAbsoluteIndex(end === undefined ? length : end, length);
  var result = Array(max(fin - k, 0));
  for (var n = 0; k < fin; k++, n++) createProperty(result, n, O[k]);
  result.length = n;
  return result;
};


/***/ }),
/* 114 */
/***/ (function(module, exports, __webpack_require__) {

var wellKnownSymbol = __webpack_require__(4);

exports.f = wellKnownSymbol;


/***/ }),
/* 115 */
/***/ (function(module, exports, __webpack_require__) {

var path = __webpack_require__(151);
var hasOwn = __webpack_require__(8);
var wrappedWellKnownSymbolModule = __webpack_require__(114);
var defineProperty = __webpack_require__(12).f;

module.exports = function (NAME) {
  var Symbol = path.Symbol || (path.Symbol = {});
  if (!hasOwn(Symbol, NAME)) defineProperty(Symbol, NAME, {
    value: wrappedWellKnownSymbolModule.f(NAME)
  });
};


/***/ }),
/* 116 */
/***/ (function(module, exports, __webpack_require__) {

var NATIVE_SYMBOL = __webpack_require__(40);

/* eslint-disable es-x/no-symbol -- safe */
module.exports = NATIVE_SYMBOL && !!Symbol['for'] && !!Symbol.keyFor;


/***/ }),
/* 117 */
/***/ (function(module, exports, __webpack_require__) {

var fails = __webpack_require__(2);
var global = __webpack_require__(0);

// babel-minify and Closure Compiler transpiles RegExp('a', 'y') -> /a/y and it causes SyntaxError
var $RegExp = global.RegExp;

var UNSUPPORTED_Y = fails(function () {
  var re = $RegExp('a', 'y');
  re.lastIndex = 2;
  return re.exec('abcd') != null;
});

// UC Browser bug
// https://github.com/zloirock/core-js/issues/1008
var MISSED_STICKY = UNSUPPORTED_Y || fails(function () {
  return !$RegExp('a', 'y').sticky;
});

var BROKEN_CARET = UNSUPPORTED_Y || fails(function () {
  // https://bugzilla.mozilla.org/show_bug.cgi?id=773687
  var re = $RegExp('^r', 'gy');
  re.lastIndex = 2;
  return re.exec('str') != null;
});

module.exports = {
  BROKEN_CARET: BROKEN_CARET,
  MISSED_STICKY: MISSED_STICKY,
  UNSUPPORTED_Y: UNSUPPORTED_Y
};


/***/ }),
/* 118 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var isConstructor = __webpack_require__(67);
var tryToString = __webpack_require__(48);

var TypeError = global.TypeError;

// `Assert: IsConstructor(argument) is true`
module.exports = function (argument) {
  if (isConstructor(argument)) return argument;
  throw TypeError(tryToString(argument) + ' is not a constructor');
};


/***/ }),
/* 119 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);
var toIntegerOrInfinity = __webpack_require__(65);
var toString = __webpack_require__(13);
var requireObjectCoercible = __webpack_require__(19);

var charAt = uncurryThis(''.charAt);
var charCodeAt = uncurryThis(''.charCodeAt);
var stringSlice = uncurryThis(''.slice);

var createMethod = function (CONVERT_TO_STRING) {
  return function ($this, pos) {
    var S = toString(requireObjectCoercible($this));
    var position = toIntegerOrInfinity(pos);
    var size = S.length;
    var first, second;
    if (position < 0 || position >= size) return CONVERT_TO_STRING ? '' : undefined;
    first = charCodeAt(S, position);
    return first < 0xD800 || first > 0xDBFF || position + 1 === size
      || (second = charCodeAt(S, position + 1)) < 0xDC00 || second > 0xDFFF
        ? CONVERT_TO_STRING
          ? charAt(S, position)
          : first
        : CONVERT_TO_STRING
          ? stringSlice(S, position, position + 2)
          : (first - 0xD800 << 10) + (second - 0xDC00) + 0x10000;
  };
};

module.exports = {
  // `String.prototype.codePointAt` method
  // https://tc39.es/ecma262/#sec-string.prototype.codepointat
  codeAt: createMethod(false),
  // `String.prototype.at` method
  // https://github.com/mathiasbynens/String.prototype.at
  charAt: createMethod(true)
};


/***/ }),
/* 120 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var fails = __webpack_require__(2);
var isCallable = __webpack_require__(5);
var create = __webpack_require__(38);
var getPrototypeOf = __webpack_require__(75);
var redefine = __webpack_require__(17);
var wellKnownSymbol = __webpack_require__(4);
var IS_PURE = __webpack_require__(33);

var ITERATOR = wellKnownSymbol('iterator');
var BUGGY_SAFARI_ITERATORS = false;

// `%IteratorPrototype%` object
// https://tc39.es/ecma262/#sec-%iteratorprototype%-object
var IteratorPrototype, PrototypeOfArrayIteratorPrototype, arrayIterator;

/* eslint-disable es-x/no-array-prototype-keys -- safe */
if ([].keys) {
  arrayIterator = [].keys();
  // Safari 8 has buggy iterators w/o `next`
  if (!('next' in arrayIterator)) BUGGY_SAFARI_ITERATORS = true;
  else {
    PrototypeOfArrayIteratorPrototype = getPrototypeOf(getPrototypeOf(arrayIterator));
    if (PrototypeOfArrayIteratorPrototype !== Object.prototype) IteratorPrototype = PrototypeOfArrayIteratorPrototype;
  }
}

var NEW_ITERATOR_PROTOTYPE = IteratorPrototype == undefined || fails(function () {
  var test = {};
  // FF44- legacy iterators case
  return IteratorPrototype[ITERATOR].call(test) !== test;
});

if (NEW_ITERATOR_PROTOTYPE) IteratorPrototype = {};
else if (IS_PURE) IteratorPrototype = create(IteratorPrototype);

// `%IteratorPrototype%[@@iterator]()` method
// https://tc39.es/ecma262/#sec-%iteratorprototype%-@@iterator
if (!isCallable(IteratorPrototype[ITERATOR])) {
  redefine(IteratorPrototype, ITERATOR, function () {
    return this;
  });
}

module.exports = {
  IteratorPrototype: IteratorPrototype,
  BUGGY_SAFARI_ITERATORS: BUGGY_SAFARI_ITERATORS
};


/***/ }),
/* 121 */
/***/ (function(module, exports, __webpack_require__) {

var fails = __webpack_require__(2);

module.exports = !fails(function () {
  function F() { /* empty */ }
  F.prototype.constructor = null;
  // eslint-disable-next-line es-x/no-object-getprototypeof -- required for testing
  return Object.getPrototypeOf(new F()) !== F.prototype;
});


/***/ }),
/* 122 */
/***/ (function(module, exports, __webpack_require__) {

/* eslint-disable no-proto -- safe */
var uncurryThis = __webpack_require__(1);
var anObject = __webpack_require__(6);
var aPossiblePrototype = __webpack_require__(162);

// `Object.setPrototypeOf` method
// https://tc39.es/ecma262/#sec-object.setprototypeof
// Works with __proto__ only. Old v8 can't work with null proto objects.
// eslint-disable-next-line es-x/no-object-setprototypeof -- safe
module.exports = Object.setPrototypeOf || ('__proto__' in {} ? function () {
  var CORRECT_SETTER = false;
  var test = {};
  var setter;
  try {
    // eslint-disable-next-line es-x/no-object-getownpropertydescriptor -- safe
    setter = uncurryThis(Object.getOwnPropertyDescriptor(Object.prototype, '__proto__').set);
    setter(test, []);
    CORRECT_SETTER = test instanceof Array;
  } catch (error) { /* empty */ }
  return function setPrototypeOf(O, proto) {
    anObject(O);
    aPossiblePrototype(proto);
    if (CORRECT_SETTER) setter(O, proto);
    else O.__proto__ = proto;
    return O;
  };
}() : undefined);


/***/ }),
/* 123 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var global = __webpack_require__(0);
var uncurryThis = __webpack_require__(1);
var isForced = __webpack_require__(87);
var redefine = __webpack_require__(17);
var InternalMetadataModule = __webpack_require__(124);
var iterate = __webpack_require__(125);
var anInstance = __webpack_require__(129);
var isCallable = __webpack_require__(5);
var isObject = __webpack_require__(11);
var fails = __webpack_require__(2);
var checkCorrectnessOfIteration = __webpack_require__(130);
var setToStringTag = __webpack_require__(72);
var inheritIfRequired = __webpack_require__(131);

module.exports = function (CONSTRUCTOR_NAME, wrapper, common) {
  var IS_MAP = CONSTRUCTOR_NAME.indexOf('Map') !== -1;
  var IS_WEAK = CONSTRUCTOR_NAME.indexOf('Weak') !== -1;
  var ADDER = IS_MAP ? 'set' : 'add';
  var NativeConstructor = global[CONSTRUCTOR_NAME];
  var NativePrototype = NativeConstructor && NativeConstructor.prototype;
  var Constructor = NativeConstructor;
  var exported = {};

  var fixMethod = function (KEY) {
    var uncurriedNativeMethod = uncurryThis(NativePrototype[KEY]);
    redefine(NativePrototype, KEY,
      KEY == 'add' ? function add(value) {
        uncurriedNativeMethod(this, value === 0 ? 0 : value);
        return this;
      } : KEY == 'delete' ? function (key) {
        return IS_WEAK && !isObject(key) ? false : uncurriedNativeMethod(this, key === 0 ? 0 : key);
      } : KEY == 'get' ? function get(key) {
        return IS_WEAK && !isObject(key) ? undefined : uncurriedNativeMethod(this, key === 0 ? 0 : key);
      } : KEY == 'has' ? function has(key) {
        return IS_WEAK && !isObject(key) ? false : uncurriedNativeMethod(this, key === 0 ? 0 : key);
      } : function set(key, value) {
        uncurriedNativeMethod(this, key === 0 ? 0 : key, value);
        return this;
      }
    );
  };

  var REPLACE = isForced(
    CONSTRUCTOR_NAME,
    !isCallable(NativeConstructor) || !(IS_WEAK || NativePrototype.forEach && !fails(function () {
      new NativeConstructor().entries().next();
    }))
  );

  if (REPLACE) {
    // create collection constructor
    Constructor = common.getConstructor(wrapper, CONSTRUCTOR_NAME, IS_MAP, ADDER);
    InternalMetadataModule.enable();
  } else if (isForced(CONSTRUCTOR_NAME, true)) {
    var instance = new Constructor();
    // early implementations not supports chaining
    var HASNT_CHAINING = instance[ADDER](IS_WEAK ? {} : -0, 1) != instance;
    // V8 ~ Chromium 40- weak-collections throws on primitives, but should return false
    var THROWS_ON_PRIMITIVES = fails(function () { instance.has(1); });
    // most early implementations doesn't supports iterables, most modern - not close it correctly
    // eslint-disable-next-line no-new -- required for testing
    var ACCEPT_ITERABLES = checkCorrectnessOfIteration(function (iterable) { new NativeConstructor(iterable); });
    // for early implementations -0 and +0 not the same
    var BUGGY_ZERO = !IS_WEAK && fails(function () {
      // V8 ~ Chromium 42- fails only with 5+ elements
      var $instance = new NativeConstructor();
      var index = 5;
      while (index--) $instance[ADDER](index, index);
      return !$instance.has(-0);
    });

    if (!ACCEPT_ITERABLES) {
      Constructor = wrapper(function (dummy, iterable) {
        anInstance(dummy, NativePrototype);
        var that = inheritIfRequired(new NativeConstructor(), dummy, Constructor);
        if (iterable != undefined) iterate(iterable, that[ADDER], { that: that, AS_ENTRIES: IS_MAP });
        return that;
      });
      Constructor.prototype = NativePrototype;
      NativePrototype.constructor = Constructor;
    }

    if (THROWS_ON_PRIMITIVES || BUGGY_ZERO) {
      fixMethod('delete');
      fixMethod('has');
      IS_MAP && fixMethod('get');
    }

    if (BUGGY_ZERO || HASNT_CHAINING) fixMethod(ADDER);

    // weak collections should not contains .clear method
    if (IS_WEAK && NativePrototype.clear) delete NativePrototype.clear;
  }

  exported[CONSTRUCTOR_NAME] = Constructor;
  $({ global: true, forced: Constructor != NativeConstructor }, exported);

  setToStringTag(Constructor, CONSTRUCTOR_NAME);

  if (!IS_WEAK) common.setStrong(Constructor, CONSTRUCTOR_NAME, IS_MAP);

  return Constructor;
};


/***/ }),
/* 124 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var uncurryThis = __webpack_require__(1);
var hiddenKeys = __webpack_require__(50);
var isObject = __webpack_require__(11);
var hasOwn = __webpack_require__(8);
var defineProperty = __webpack_require__(12).f;
var getOwnPropertyNamesModule = __webpack_require__(53);
var getOwnPropertyNamesExternalModule = __webpack_require__(112);
var isExtensible = __webpack_require__(165);
var uid = __webpack_require__(57);
var FREEZING = __webpack_require__(167);

var REQUIRED = false;
var METADATA = uid('meta');
var id = 0;

var setMetadata = function (it) {
  defineProperty(it, METADATA, { value: {
    objectID: 'O' + id++, // object ID
    weakData: {}          // weak collections IDs
  } });
};

var fastKey = function (it, create) {
  // return a primitive with prefix
  if (!isObject(it)) return typeof it == 'symbol' ? it : (typeof it == 'string' ? 'S' : 'P') + it;
  if (!hasOwn(it, METADATA)) {
    // can't set metadata to uncaught frozen object
    if (!isExtensible(it)) return 'F';
    // not necessary to add metadata
    if (!create) return 'E';
    // add missing metadata
    setMetadata(it);
  // return object ID
  } return it[METADATA].objectID;
};

var getWeakData = function (it, create) {
  if (!hasOwn(it, METADATA)) {
    // can't set metadata to uncaught frozen object
    if (!isExtensible(it)) return true;
    // not necessary to add metadata
    if (!create) return false;
    // add missing metadata
    setMetadata(it);
  // return the store of weak collections IDs
  } return it[METADATA].weakData;
};

// add metadata on freeze-family methods calling
var onFreeze = function (it) {
  if (FREEZING && REQUIRED && isExtensible(it) && !hasOwn(it, METADATA)) setMetadata(it);
  return it;
};

var enable = function () {
  meta.enable = function () { /* empty */ };
  REQUIRED = true;
  var getOwnPropertyNames = getOwnPropertyNamesModule.f;
  var splice = uncurryThis([].splice);
  var test = {};
  test[METADATA] = 1;

  // prevent exposing of metadata key
  if (getOwnPropertyNames(test).length) {
    getOwnPropertyNamesModule.f = function (it) {
      var result = getOwnPropertyNames(it);
      for (var i = 0, length = result.length; i < length; i++) {
        if (result[i] === METADATA) {
          splice(result, i, 1);
          break;
        }
      } return result;
    };

    $({ target: 'Object', stat: true, forced: true }, {
      getOwnPropertyNames: getOwnPropertyNamesExternalModule.f
    });
  }
};

var meta = module.exports = {
  enable: enable,
  fastKey: fastKey,
  getWeakData: getWeakData,
  onFreeze: onFreeze
};

hiddenKeys[METADATA] = true;


/***/ }),
/* 125 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var bind = __webpack_require__(63);
var call = __webpack_require__(7);
var anObject = __webpack_require__(6);
var tryToString = __webpack_require__(48);
var isArrayIteratorMethod = __webpack_require__(126);
var lengthOfArrayLike = __webpack_require__(27);
var isPrototypeOf = __webpack_require__(35);
var getIterator = __webpack_require__(127);
var getIteratorMethod = __webpack_require__(96);
var iteratorClose = __webpack_require__(128);

var TypeError = global.TypeError;

var Result = function (stopped, result) {
  this.stopped = stopped;
  this.result = result;
};

var ResultPrototype = Result.prototype;

module.exports = function (iterable, unboundFunction, options) {
  var that = options && options.that;
  var AS_ENTRIES = !!(options && options.AS_ENTRIES);
  var IS_ITERATOR = !!(options && options.IS_ITERATOR);
  var INTERRUPTED = !!(options && options.INTERRUPTED);
  var fn = bind(unboundFunction, that);
  var iterator, iterFn, index, length, result, next, step;

  var stop = function (condition) {
    if (iterator) iteratorClose(iterator, 'normal', condition);
    return new Result(true, condition);
  };

  var callFn = function (value) {
    if (AS_ENTRIES) {
      anObject(value);
      return INTERRUPTED ? fn(value[0], value[1], stop) : fn(value[0], value[1]);
    } return INTERRUPTED ? fn(value, stop) : fn(value);
  };

  if (IS_ITERATOR) {
    iterator = iterable;
  } else {
    iterFn = getIteratorMethod(iterable);
    if (!iterFn) throw TypeError(tryToString(iterable) + ' is not iterable');
    // optimisation for array iterators
    if (isArrayIteratorMethod(iterFn)) {
      for (index = 0, length = lengthOfArrayLike(iterable); length > index; index++) {
        result = callFn(iterable[index]);
        if (result && isPrototypeOf(ResultPrototype, result)) return result;
      } return new Result(false);
    }
    iterator = getIterator(iterable, iterFn);
  }

  next = iterator.next;
  while (!(step = call(next, iterator)).done) {
    try {
      result = callFn(step.value);
    } catch (error) {
      iteratorClose(iterator, 'throw', error);
    }
    if (typeof result == 'object' && result && isPrototypeOf(ResultPrototype, result)) return result;
  } return new Result(false);
};


/***/ }),
/* 126 */
/***/ (function(module, exports, __webpack_require__) {

var wellKnownSymbol = __webpack_require__(4);
var Iterators = __webpack_require__(56);

var ITERATOR = wellKnownSymbol('iterator');
var ArrayPrototype = Array.prototype;

// check on default Array iterator
module.exports = function (it) {
  return it !== undefined && (Iterators.Array === it || ArrayPrototype[ITERATOR] === it);
};


/***/ }),
/* 127 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var call = __webpack_require__(7);
var aCallable = __webpack_require__(59);
var anObject = __webpack_require__(6);
var tryToString = __webpack_require__(48);
var getIteratorMethod = __webpack_require__(96);

var TypeError = global.TypeError;

module.exports = function (argument, usingIterator) {
  var iteratorMethod = arguments.length < 2 ? getIteratorMethod(argument) : usingIterator;
  if (aCallable(iteratorMethod)) return anObject(call(iteratorMethod, argument));
  throw TypeError(tryToString(argument) + ' is not iterable');
};


/***/ }),
/* 128 */
/***/ (function(module, exports, __webpack_require__) {

var call = __webpack_require__(7);
var anObject = __webpack_require__(6);
var getMethod = __webpack_require__(36);

module.exports = function (iterator, kind, value) {
  var innerResult, innerError;
  anObject(iterator);
  try {
    innerResult = getMethod(iterator, 'return');
    if (!innerResult) {
      if (kind === 'throw') throw value;
      return value;
    }
    innerResult = call(innerResult, iterator);
  } catch (error) {
    innerError = true;
    innerResult = error;
  }
  if (kind === 'throw') throw value;
  if (innerError) throw innerResult;
  anObject(innerResult);
  return value;
};


/***/ }),
/* 129 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var isPrototypeOf = __webpack_require__(35);

var TypeError = global.TypeError;

module.exports = function (it, Prototype) {
  if (isPrototypeOf(Prototype, it)) return it;
  throw TypeError('Incorrect invocation');
};


/***/ }),
/* 130 */
/***/ (function(module, exports, __webpack_require__) {

var wellKnownSymbol = __webpack_require__(4);

var ITERATOR = wellKnownSymbol('iterator');
var SAFE_CLOSING = false;

try {
  var called = 0;
  var iteratorWithReturn = {
    next: function () {
      return { done: !!called++ };
    },
    'return': function () {
      SAFE_CLOSING = true;
    }
  };
  iteratorWithReturn[ITERATOR] = function () {
    return this;
  };
  // eslint-disable-next-line es-x/no-array-from, no-throw-literal -- required for testing
  Array.from(iteratorWithReturn, function () { throw 2; });
} catch (error) { /* empty */ }

module.exports = function (exec, SKIP_CLOSING) {
  if (!SKIP_CLOSING && !SAFE_CLOSING) return false;
  var ITERATION_SUPPORT = false;
  try {
    var object = {};
    object[ITERATOR] = function () {
      return {
        next: function () {
          return { done: ITERATION_SUPPORT = true };
        }
      };
    };
    exec(object);
  } catch (error) { /* empty */ }
  return ITERATION_SUPPORT;
};


/***/ }),
/* 131 */
/***/ (function(module, exports, __webpack_require__) {

var isCallable = __webpack_require__(5);
var isObject = __webpack_require__(11);
var setPrototypeOf = __webpack_require__(122);

// makes subclassing work correct for wrapped built-ins
module.exports = function ($this, dummy, Wrapper) {
  var NewTarget, NewTargetPrototype;
  if (
    // it can work only with native `setPrototypeOf`
    setPrototypeOf &&
    // we haven't completely correct pre-ES6 way for getting `new.target`, so use this
    isCallable(NewTarget = dummy.constructor) &&
    NewTarget !== Wrapper &&
    isObject(NewTargetPrototype = NewTarget.prototype) &&
    NewTargetPrototype !== Wrapper.prototype
  ) setPrototypeOf($this, NewTargetPrototype);
  return $this;
};


/***/ }),
/* 132 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var defineProperty = __webpack_require__(12).f;
var create = __webpack_require__(38);
var redefineAll = __webpack_require__(168);
var bind = __webpack_require__(63);
var anInstance = __webpack_require__(129);
var iterate = __webpack_require__(125);
var defineIterator = __webpack_require__(95);
var setSpecies = __webpack_require__(169);
var DESCRIPTORS = __webpack_require__(10);
var fastKey = __webpack_require__(124).fastKey;
var InternalStateModule = __webpack_require__(41);

var setInternalState = InternalStateModule.set;
var internalStateGetterFor = InternalStateModule.getterFor;

module.exports = {
  getConstructor: function (wrapper, CONSTRUCTOR_NAME, IS_MAP, ADDER) {
    var Constructor = wrapper(function (that, iterable) {
      anInstance(that, Prototype);
      setInternalState(that, {
        type: CONSTRUCTOR_NAME,
        index: create(null),
        first: undefined,
        last: undefined,
        size: 0
      });
      if (!DESCRIPTORS) that.size = 0;
      if (iterable != undefined) iterate(iterable, that[ADDER], { that: that, AS_ENTRIES: IS_MAP });
    });

    var Prototype = Constructor.prototype;

    var getInternalState = internalStateGetterFor(CONSTRUCTOR_NAME);

    var define = function (that, key, value) {
      var state = getInternalState(that);
      var entry = getEntry(that, key);
      var previous, index;
      // change existing entry
      if (entry) {
        entry.value = value;
      // create new entry
      } else {
        state.last = entry = {
          index: index = fastKey(key, true),
          key: key,
          value: value,
          previous: previous = state.last,
          next: undefined,
          removed: false
        };
        if (!state.first) state.first = entry;
        if (previous) previous.next = entry;
        if (DESCRIPTORS) state.size++;
        else that.size++;
        // add to index
        if (index !== 'F') state.index[index] = entry;
      } return that;
    };

    var getEntry = function (that, key) {
      var state = getInternalState(that);
      // fast case
      var index = fastKey(key);
      var entry;
      if (index !== 'F') return state.index[index];
      // frozen object case
      for (entry = state.first; entry; entry = entry.next) {
        if (entry.key == key) return entry;
      }
    };

    redefineAll(Prototype, {
      // `{ Map, Set }.prototype.clear()` methods
      // https://tc39.es/ecma262/#sec-map.prototype.clear
      // https://tc39.es/ecma262/#sec-set.prototype.clear
      clear: function clear() {
        var that = this;
        var state = getInternalState(that);
        var data = state.index;
        var entry = state.first;
        while (entry) {
          entry.removed = true;
          if (entry.previous) entry.previous = entry.previous.next = undefined;
          delete data[entry.index];
          entry = entry.next;
        }
        state.first = state.last = undefined;
        if (DESCRIPTORS) state.size = 0;
        else that.size = 0;
      },
      // `{ Map, Set }.prototype.delete(key)` methods
      // https://tc39.es/ecma262/#sec-map.prototype.delete
      // https://tc39.es/ecma262/#sec-set.prototype.delete
      'delete': function (key) {
        var that = this;
        var state = getInternalState(that);
        var entry = getEntry(that, key);
        if (entry) {
          var next = entry.next;
          var prev = entry.previous;
          delete state.index[entry.index];
          entry.removed = true;
          if (prev) prev.next = next;
          if (next) next.previous = prev;
          if (state.first == entry) state.first = next;
          if (state.last == entry) state.last = prev;
          if (DESCRIPTORS) state.size--;
          else that.size--;
        } return !!entry;
      },
      // `{ Map, Set }.prototype.forEach(callbackfn, thisArg = undefined)` methods
      // https://tc39.es/ecma262/#sec-map.prototype.foreach
      // https://tc39.es/ecma262/#sec-set.prototype.foreach
      forEach: function forEach(callbackfn /* , that = undefined */) {
        var state = getInternalState(this);
        var boundFunction = bind(callbackfn, arguments.length > 1 ? arguments[1] : undefined);
        var entry;
        while (entry = entry ? entry.next : state.first) {
          boundFunction(entry.value, entry.key, this);
          // revert to the last existing entry
          while (entry && entry.removed) entry = entry.previous;
        }
      },
      // `{ Map, Set}.prototype.has(key)` methods
      // https://tc39.es/ecma262/#sec-map.prototype.has
      // https://tc39.es/ecma262/#sec-set.prototype.has
      has: function has(key) {
        return !!getEntry(this, key);
      }
    });

    redefineAll(Prototype, IS_MAP ? {
      // `Map.prototype.get(key)` method
      // https://tc39.es/ecma262/#sec-map.prototype.get
      get: function get(key) {
        var entry = getEntry(this, key);
        return entry && entry.value;
      },
      // `Map.prototype.set(key, value)` method
      // https://tc39.es/ecma262/#sec-map.prototype.set
      set: function set(key, value) {
        return define(this, key === 0 ? 0 : key, value);
      }
    } : {
      // `Set.prototype.add(value)` method
      // https://tc39.es/ecma262/#sec-set.prototype.add
      add: function add(value) {
        return define(this, value = value === 0 ? 0 : value, value);
      }
    });
    if (DESCRIPTORS) defineProperty(Prototype, 'size', {
      get: function () {
        return getInternalState(this).size;
      }
    });
    return Constructor;
  },
  setStrong: function (Constructor, CONSTRUCTOR_NAME, IS_MAP) {
    var ITERATOR_NAME = CONSTRUCTOR_NAME + ' Iterator';
    var getInternalCollectionState = internalStateGetterFor(CONSTRUCTOR_NAME);
    var getInternalIteratorState = internalStateGetterFor(ITERATOR_NAME);
    // `{ Map, Set }.prototype.{ keys, values, entries, @@iterator }()` methods
    // https://tc39.es/ecma262/#sec-map.prototype.entries
    // https://tc39.es/ecma262/#sec-map.prototype.keys
    // https://tc39.es/ecma262/#sec-map.prototype.values
    // https://tc39.es/ecma262/#sec-map.prototype-@@iterator
    // https://tc39.es/ecma262/#sec-set.prototype.entries
    // https://tc39.es/ecma262/#sec-set.prototype.keys
    // https://tc39.es/ecma262/#sec-set.prototype.values
    // https://tc39.es/ecma262/#sec-set.prototype-@@iterator
    defineIterator(Constructor, CONSTRUCTOR_NAME, function (iterated, kind) {
      setInternalState(this, {
        type: ITERATOR_NAME,
        target: iterated,
        state: getInternalCollectionState(iterated),
        kind: kind,
        last: undefined
      });
    }, function () {
      var state = getInternalIteratorState(this);
      var kind = state.kind;
      var entry = state.last;
      // revert to the last existing entry
      while (entry && entry.removed) entry = entry.previous;
      // get next entry
      if (!state.target || !(state.last = entry = entry ? entry.next : state.state.first)) {
        // or finish the iteration
        state.target = undefined;
        return { value: undefined, done: true };
      }
      // return step by kind
      if (kind == 'keys') return { value: entry.key, done: false };
      if (kind == 'values') return { value: entry.value, done: false };
      return { value: [entry.key, entry.value], done: false };
    }, IS_MAP ? 'entries' : 'values', !IS_MAP, true);

    // `{ Map, Set }.prototype[@@species]` accessors
    // https://tc39.es/ecma262/#sec-get-map-@@species
    // https://tc39.es/ecma262/#sec-get-set-@@species
    setSpecies(CONSTRUCTOR_NAME);
  }
};


/***/ }),
/* 133 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);
var toObject = __webpack_require__(16);

var floor = Math.floor;
var charAt = uncurryThis(''.charAt);
var replace = uncurryThis(''.replace);
var stringSlice = uncurryThis(''.slice);
var SUBSTITUTION_SYMBOLS = /\$([$&'`]|\d{1,2}|<[^>]*>)/g;
var SUBSTITUTION_SYMBOLS_NO_NAMED = /\$([$&'`]|\d{1,2})/g;

// `GetSubstitution` abstract operation
// https://tc39.es/ecma262/#sec-getsubstitution
module.exports = function (matched, str, position, captures, namedCaptures, replacement) {
  var tailPos = position + matched.length;
  var m = captures.length;
  var symbols = SUBSTITUTION_SYMBOLS_NO_NAMED;
  if (namedCaptures !== undefined) {
    namedCaptures = toObject(namedCaptures);
    symbols = SUBSTITUTION_SYMBOLS;
  }
  return replace(replacement, symbols, function (match, ch) {
    var capture;
    switch (charAt(ch, 0)) {
      case '$': return '$';
      case '&': return matched;
      case '`': return stringSlice(str, 0, position);
      case "'": return stringSlice(str, tailPos);
      case '<':
        capture = namedCaptures[stringSlice(ch, 1, -1)];
        break;
      default: // \d\d?
        var n = +ch;
        if (n === 0) return match;
        if (n > m) {
          var f = floor(n / 10);
          if (f === 0) return match;
          if (f <= m) return captures[f - 1] === undefined ? charAt(ch, 1) : captures[f - 1] + charAt(ch, 1);
          return match;
        }
        capture = captures[n - 1];
    }
    return capture === undefined ? '' : capture;
  });
};


/***/ }),
/* 134 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var isRegExp = __webpack_require__(91);

var TypeError = global.TypeError;

module.exports = function (it) {
  if (isRegExp(it)) {
    throw TypeError("The method doesn't accept regular expressions");
  } return it;
};


/***/ }),
/* 135 */
/***/ (function(module, exports, __webpack_require__) {

var wellKnownSymbol = __webpack_require__(4);

var MATCH = wellKnownSymbol('match');

module.exports = function (METHOD_NAME) {
  var regexp = /./;
  try {
    '/./'[METHOD_NAME](regexp);
  } catch (error1) {
    try {
      regexp[MATCH] = false;
      return '/./'[METHOD_NAME](regexp);
    } catch (error2) { /* empty */ }
  } return false;
};


/***/ }),
/* 136 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);
var requireObjectCoercible = __webpack_require__(19);
var toString = __webpack_require__(13);
var whitespaces = __webpack_require__(137);

var replace = uncurryThis(''.replace);
var whitespace = '[' + whitespaces + ']';
var ltrim = RegExp('^' + whitespace + whitespace + '*');
var rtrim = RegExp(whitespace + whitespace + '*$');

// `String.prototype.{ trim, trimStart, trimEnd, trimLeft, trimRight }` methods implementation
var createMethod = function (TYPE) {
  return function ($this) {
    var string = toString(requireObjectCoercible($this));
    if (TYPE & 1) string = replace(string, ltrim, '');
    if (TYPE & 2) string = replace(string, rtrim, '');
    return string;
  };
};

module.exports = {
  // `String.prototype.{ trimLeft, trimStart }` methods
  // https://tc39.es/ecma262/#sec-string.prototype.trimstart
  start: createMethod(1),
  // `String.prototype.{ trimRight, trimEnd }` methods
  // https://tc39.es/ecma262/#sec-string.prototype.trimend
  end: createMethod(2),
  // `String.prototype.trim` method
  // https://tc39.es/ecma262/#sec-string.prototype.trim
  trim: createMethod(3)
};


/***/ }),
/* 137 */
/***/ (function(module, exports) {

// a string of all valid unicode whitespaces
module.exports = '\u0009\u000A\u000B\u000C\u000D\u0020\u00A0\u1680\u2000\u2001\u2002' +
  '\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200A\u202F\u205F\u3000\u2028\u2029\uFEFF';


/***/ }),
/* 138 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var uncurryThis = __webpack_require__(1);
var PROPER_FUNCTION_NAME = __webpack_require__(51).PROPER;
var redefine = __webpack_require__(17);
var anObject = __webpack_require__(6);
var isPrototypeOf = __webpack_require__(35);
var $toString = __webpack_require__(13);
var fails = __webpack_require__(2);
var regExpFlags = __webpack_require__(89);

var TO_STRING = 'toString';
var RegExpPrototype = RegExp.prototype;
var n$ToString = RegExpPrototype[TO_STRING];
var getFlags = uncurryThis(regExpFlags);

var NOT_GENERIC = fails(function () { return n$ToString.call({ source: 'a', flags: 'b' }) != '/a/b'; });
// FF44- RegExp#toString has a wrong name
var INCORRECT_NAME = PROPER_FUNCTION_NAME && n$ToString.name != TO_STRING;

// `RegExp.prototype.toString` method
// https://tc39.es/ecma262/#sec-regexp.prototype.tostring
if (NOT_GENERIC || INCORRECT_NAME) {
  redefine(RegExp.prototype, TO_STRING, function toString() {
    var R = anObject(this);
    var p = $toString(R.source);
    var rf = R.flags;
    var f = $toString(rf === undefined && isPrototypeOf(RegExpPrototype, R) && !('flags' in RegExpPrototype) ? getFlags(R) : rf);
    return '/' + p + '/' + f;
  }, { unsafe: true });
}


/***/ }),
/* 139 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var DESCRIPTORS = __webpack_require__(10);
var global = __webpack_require__(0);
var uncurryThis = __webpack_require__(1);
var isForced = __webpack_require__(87);
var redefine = __webpack_require__(17);
var hasOwn = __webpack_require__(8);
var inheritIfRequired = __webpack_require__(131);
var isPrototypeOf = __webpack_require__(35);
var isSymbol = __webpack_require__(47);
var toPrimitive = __webpack_require__(102);
var fails = __webpack_require__(2);
var getOwnPropertyNames = __webpack_require__(53).f;
var getOwnPropertyDescriptor = __webpack_require__(28).f;
var defineProperty = __webpack_require__(12).f;
var thisNumberValue = __webpack_require__(187);
var trim = __webpack_require__(136).trim;

var NUMBER = 'Number';
var NativeNumber = global[NUMBER];
var NumberPrototype = NativeNumber.prototype;
var TypeError = global.TypeError;
var arraySlice = uncurryThis(''.slice);
var charCodeAt = uncurryThis(''.charCodeAt);

// `ToNumeric` abstract operation
// https://tc39.es/ecma262/#sec-tonumeric
var toNumeric = function (value) {
  var primValue = toPrimitive(value, 'number');
  return typeof primValue == 'bigint' ? primValue : toNumber(primValue);
};

// `ToNumber` abstract operation
// https://tc39.es/ecma262/#sec-tonumber
var toNumber = function (argument) {
  var it = toPrimitive(argument, 'number');
  var first, third, radix, maxCode, digits, length, index, code;
  if (isSymbol(it)) throw TypeError('Cannot convert a Symbol value to a number');
  if (typeof it == 'string' && it.length > 2) {
    it = trim(it);
    first = charCodeAt(it, 0);
    if (first === 43 || first === 45) {
      third = charCodeAt(it, 2);
      if (third === 88 || third === 120) return NaN; // Number('+0x1') should be NaN, old V8 fix
    } else if (first === 48) {
      switch (charCodeAt(it, 1)) {
        case 66: case 98: radix = 2; maxCode = 49; break; // fast equal of /^0b[01]+$/i
        case 79: case 111: radix = 8; maxCode = 55; break; // fast equal of /^0o[0-7]+$/i
        default: return +it;
      }
      digits = arraySlice(it, 2);
      length = digits.length;
      for (index = 0; index < length; index++) {
        code = charCodeAt(digits, index);
        // parseInt parses a string to a first unavailable symbol
        // but ToNumber should return NaN if a string contains unavailable symbols
        if (code < 48 || code > maxCode) return NaN;
      } return parseInt(digits, radix);
    }
  } return +it;
};

// `Number` constructor
// https://tc39.es/ecma262/#sec-number-constructor
if (isForced(NUMBER, !NativeNumber(' 0o1') || !NativeNumber('0b1') || NativeNumber('+0x1'))) {
  var NumberWrapper = function Number(value) {
    var n = arguments.length < 1 ? 0 : NativeNumber(toNumeric(value));
    var dummy = this;
    // check on 1..constructor(foo) case
    return isPrototypeOf(NumberPrototype, dummy) && fails(function () { thisNumberValue(dummy); })
      ? inheritIfRequired(Object(n), dummy, NumberWrapper) : n;
  };
  for (var keys = DESCRIPTORS ? getOwnPropertyNames(NativeNumber) : (
    // ES3:
    'MAX_VALUE,MIN_VALUE,NaN,NEGATIVE_INFINITY,POSITIVE_INFINITY,' +
    // ES2015 (in case, if modules with ES2015 Number statics required before):
    'EPSILON,MAX_SAFE_INTEGER,MIN_SAFE_INTEGER,isFinite,isInteger,isNaN,isSafeInteger,parseFloat,parseInt,' +
    // ESNext
    'fromString,range'
  ).split(','), j = 0, key; keys.length > j; j++) {
    if (hasOwn(NativeNumber, key = keys[j]) && !hasOwn(NumberWrapper, key)) {
      defineProperty(NumberWrapper, key, getOwnPropertyDescriptor(NativeNumber, key));
    }
  }
  NumberWrapper.prototype = NumberPrototype;
  NumberPrototype.constructor = NumberWrapper;
  redefine(global, NUMBER, NumberWrapper);
}


/***/ }),
/* 140 */
/***/ (function(module, exports, __webpack_require__) {

/* smoothscroll v0.4.4 - 2019 - Dustan Kasten, Jeremias Menichelli - MIT License */
(function () {
  'use strict';

  // polyfill
  function polyfill() {
    // aliases
    var w = window;
    var d = document;

    // return if scroll behavior is supported and polyfill is not forced
    if (
      'scrollBehavior' in d.documentElement.style &&
      w.__forceSmoothScrollPolyfill__ !== true
    ) {
      return;
    }

    // globals
    var Element = w.HTMLElement || w.Element;
    var SCROLL_TIME = 468;

    // object gathering original scroll methods
    var original = {
      scroll: w.scroll || w.scrollTo,
      scrollBy: w.scrollBy,
      elementScroll: Element.prototype.scroll || scrollElement,
      scrollIntoView: Element.prototype.scrollIntoView
    };

    // define timing method
    var now =
      w.performance && w.performance.now
        ? w.performance.now.bind(w.performance)
        : Date.now;

    /**
     * indicates if a the current browser is made by Microsoft
     * @method isMicrosoftBrowser
     * @param {String} userAgent
     * @returns {Boolean}
     */
    function isMicrosoftBrowser(userAgent) {
      var userAgentPatterns = ['MSIE ', 'Trident/', 'Edge/'];

      return new RegExp(userAgentPatterns.join('|')).test(userAgent);
    }

    /*
     * IE has rounding bug rounding down clientHeight and clientWidth and
     * rounding up scrollHeight and scrollWidth causing false positives
     * on hasScrollableSpace
     */
    var ROUNDING_TOLERANCE = isMicrosoftBrowser(w.navigator.userAgent) ? 1 : 0;

    /**
     * changes scroll position inside an element
     * @method scrollElement
     * @param {Number} x
     * @param {Number} y
     * @returns {undefined}
     */
    function scrollElement(x, y) {
      this.scrollLeft = x;
      this.scrollTop = y;
    }

    /**
     * returns result of applying ease math function to a number
     * @method ease
     * @param {Number} k
     * @returns {Number}
     */
    function ease(k) {
      return 0.5 * (1 - Math.cos(Math.PI * k));
    }

    /**
     * indicates if a smooth behavior should be applied
     * @method shouldBailOut
     * @param {Number|Object} firstArg
     * @returns {Boolean}
     */
    function shouldBailOut(firstArg) {
      if (
        firstArg === null ||
        typeof firstArg !== 'object' ||
        firstArg.behavior === undefined ||
        firstArg.behavior === 'auto' ||
        firstArg.behavior === 'instant'
      ) {
        // first argument is not an object/null
        // or behavior is auto, instant or undefined
        return true;
      }

      if (typeof firstArg === 'object' && firstArg.behavior === 'smooth') {
        // first argument is an object and behavior is smooth
        return false;
      }

      // throw error when behavior is not supported
      throw new TypeError(
        'behavior member of ScrollOptions ' +
          firstArg.behavior +
          ' is not a valid value for enumeration ScrollBehavior.'
      );
    }

    /**
     * indicates if an element has scrollable space in the provided axis
     * @method hasScrollableSpace
     * @param {Node} el
     * @param {String} axis
     * @returns {Boolean}
     */
    function hasScrollableSpace(el, axis) {
      if (axis === 'Y') {
        return el.clientHeight + ROUNDING_TOLERANCE < el.scrollHeight;
      }

      if (axis === 'X') {
        return el.clientWidth + ROUNDING_TOLERANCE < el.scrollWidth;
      }
    }

    /**
     * indicates if an element has a scrollable overflow property in the axis
     * @method canOverflow
     * @param {Node} el
     * @param {String} axis
     * @returns {Boolean}
     */
    function canOverflow(el, axis) {
      var overflowValue = w.getComputedStyle(el, null)['overflow' + axis];

      return overflowValue === 'auto' || overflowValue === 'scroll';
    }

    /**
     * indicates if an element can be scrolled in either axis
     * @method isScrollable
     * @param {Node} el
     * @param {String} axis
     * @returns {Boolean}
     */
    function isScrollable(el) {
      var isScrollableY = hasScrollableSpace(el, 'Y') && canOverflow(el, 'Y');
      var isScrollableX = hasScrollableSpace(el, 'X') && canOverflow(el, 'X');

      return isScrollableY || isScrollableX;
    }

    /**
     * finds scrollable parent of an element
     * @method findScrollableParent
     * @param {Node} el
     * @returns {Node} el
     */
    function findScrollableParent(el) {
      while (el !== d.body && isScrollable(el) === false) {
        el = el.parentNode || el.host;
      }

      return el;
    }

    /**
     * self invoked function that, given a context, steps through scrolling
     * @method step
     * @param {Object} context
     * @returns {undefined}
     */
    function step(context) {
      var time = now();
      var value;
      var currentX;
      var currentY;
      var elapsed = (time - context.startTime) / SCROLL_TIME;

      // avoid elapsed times higher than one
      elapsed = elapsed > 1 ? 1 : elapsed;

      // apply easing to elapsed time
      value = ease(elapsed);

      currentX = context.startX + (context.x - context.startX) * value;
      currentY = context.startY + (context.y - context.startY) * value;

      context.method.call(context.scrollable, currentX, currentY);

      // scroll more if we have not reached our destination
      if (currentX !== context.x || currentY !== context.y) {
        w.requestAnimationFrame(step.bind(w, context));
      }
    }

    /**
     * scrolls window or element with a smooth behavior
     * @method smoothScroll
     * @param {Object|Node} el
     * @param {Number} x
     * @param {Number} y
     * @returns {undefined}
     */
    function smoothScroll(el, x, y) {
      var scrollable;
      var startX;
      var startY;
      var method;
      var startTime = now();

      // define scroll context
      if (el === d.body) {
        scrollable = w;
        startX = w.scrollX || w.pageXOffset;
        startY = w.scrollY || w.pageYOffset;
        method = original.scroll;
      } else {
        scrollable = el;
        startX = el.scrollLeft;
        startY = el.scrollTop;
        method = scrollElement;
      }

      // scroll looping over a frame
      step({
        scrollable: scrollable,
        method: method,
        startTime: startTime,
        startX: startX,
        startY: startY,
        x: x,
        y: y
      });
    }

    // ORIGINAL METHODS OVERRIDES
    // w.scroll and w.scrollTo
    w.scroll = w.scrollTo = function() {
      // avoid action when no arguments are passed
      if (arguments[0] === undefined) {
        return;
      }

      // avoid smooth behavior if not required
      if (shouldBailOut(arguments[0]) === true) {
        original.scroll.call(
          w,
          arguments[0].left !== undefined
            ? arguments[0].left
            : typeof arguments[0] !== 'object'
              ? arguments[0]
              : w.scrollX || w.pageXOffset,
          // use top prop, second argument if present or fallback to scrollY
          arguments[0].top !== undefined
            ? arguments[0].top
            : arguments[1] !== undefined
              ? arguments[1]
              : w.scrollY || w.pageYOffset
        );

        return;
      }

      // LET THE SMOOTHNESS BEGIN!
      smoothScroll.call(
        w,
        d.body,
        arguments[0].left !== undefined
          ? ~~arguments[0].left
          : w.scrollX || w.pageXOffset,
        arguments[0].top !== undefined
          ? ~~arguments[0].top
          : w.scrollY || w.pageYOffset
      );
    };

    // w.scrollBy
    w.scrollBy = function() {
      // avoid action when no arguments are passed
      if (arguments[0] === undefined) {
        return;
      }

      // avoid smooth behavior if not required
      if (shouldBailOut(arguments[0])) {
        original.scrollBy.call(
          w,
          arguments[0].left !== undefined
            ? arguments[0].left
            : typeof arguments[0] !== 'object' ? arguments[0] : 0,
          arguments[0].top !== undefined
            ? arguments[0].top
            : arguments[1] !== undefined ? arguments[1] : 0
        );

        return;
      }

      // LET THE SMOOTHNESS BEGIN!
      smoothScroll.call(
        w,
        d.body,
        ~~arguments[0].left + (w.scrollX || w.pageXOffset),
        ~~arguments[0].top + (w.scrollY || w.pageYOffset)
      );
    };

    // Element.prototype.scroll and Element.prototype.scrollTo
    Element.prototype.scroll = Element.prototype.scrollTo = function() {
      // avoid action when no arguments are passed
      if (arguments[0] === undefined) {
        return;
      }

      // avoid smooth behavior if not required
      if (shouldBailOut(arguments[0]) === true) {
        // if one number is passed, throw error to match Firefox implementation
        if (typeof arguments[0] === 'number' && arguments[1] === undefined) {
          throw new SyntaxError('Value could not be converted');
        }

        original.elementScroll.call(
          this,
          // use left prop, first number argument or fallback to scrollLeft
          arguments[0].left !== undefined
            ? ~~arguments[0].left
            : typeof arguments[0] !== 'object' ? ~~arguments[0] : this.scrollLeft,
          // use top prop, second argument or fallback to scrollTop
          arguments[0].top !== undefined
            ? ~~arguments[0].top
            : arguments[1] !== undefined ? ~~arguments[1] : this.scrollTop
        );

        return;
      }

      var left = arguments[0].left;
      var top = arguments[0].top;

      // LET THE SMOOTHNESS BEGIN!
      smoothScroll.call(
        this,
        this,
        typeof left === 'undefined' ? this.scrollLeft : ~~left,
        typeof top === 'undefined' ? this.scrollTop : ~~top
      );
    };

    // Element.prototype.scrollBy
    Element.prototype.scrollBy = function() {
      // avoid action when no arguments are passed
      if (arguments[0] === undefined) {
        return;
      }

      // avoid smooth behavior if not required
      if (shouldBailOut(arguments[0]) === true) {
        original.elementScroll.call(
          this,
          arguments[0].left !== undefined
            ? ~~arguments[0].left + this.scrollLeft
            : ~~arguments[0] + this.scrollLeft,
          arguments[0].top !== undefined
            ? ~~arguments[0].top + this.scrollTop
            : ~~arguments[1] + this.scrollTop
        );

        return;
      }

      this.scroll({
        left: ~~arguments[0].left + this.scrollLeft,
        top: ~~arguments[0].top + this.scrollTop,
        behavior: arguments[0].behavior
      });
    };

    // Element.prototype.scrollIntoView
    Element.prototype.scrollIntoView = function() {
      // avoid smooth behavior if not required
      if (shouldBailOut(arguments[0]) === true) {
        original.scrollIntoView.call(
          this,
          arguments[0] === undefined ? true : arguments[0]
        );

        return;
      }

      // LET THE SMOOTHNESS BEGIN!
      var scrollableParent = findScrollableParent(this);
      var parentRects = scrollableParent.getBoundingClientRect();
      var clientRects = this.getBoundingClientRect();

      if (scrollableParent !== d.body) {
        // reveal element inside parent
        smoothScroll.call(
          this,
          scrollableParent,
          scrollableParent.scrollLeft + clientRects.left - parentRects.left,
          scrollableParent.scrollTop + clientRects.top - parentRects.top
        );

        // reveal parent in viewport unless is fixed
        if (w.getComputedStyle(scrollableParent).position !== 'fixed') {
          w.scrollBy({
            left: parentRects.left,
            top: parentRects.top,
            behavior: 'smooth'
          });
        }
      } else {
        // reveal element in viewport
        w.scrollBy({
          left: clientRects.left,
          top: clientRects.top,
          behavior: 'smooth'
        });
      }
    };
  }

  if (true) {
    // commonjs
    module.exports = { polyfill: polyfill };
  } else {}

}());


/***/ }),
/* 141 */
/***/ (function(module, exports, __webpack_require__) {

(function (global, factory) {
     true ? factory(exports) :
    undefined;
})(this, (function (exports) { 'use strict';

    exports.PipsMode = void 0;
    (function (PipsMode) {
        PipsMode["Range"] = "range";
        PipsMode["Steps"] = "steps";
        PipsMode["Positions"] = "positions";
        PipsMode["Count"] = "count";
        PipsMode["Values"] = "values";
    })(exports.PipsMode || (exports.PipsMode = {}));
    exports.PipsType = void 0;
    (function (PipsType) {
        PipsType[PipsType["None"] = -1] = "None";
        PipsType[PipsType["NoValue"] = 0] = "NoValue";
        PipsType[PipsType["LargeValue"] = 1] = "LargeValue";
        PipsType[PipsType["SmallValue"] = 2] = "SmallValue";
    })(exports.PipsType || (exports.PipsType = {}));
    //region Helper Methods
    function isValidFormatter(entry) {
        return isValidPartialFormatter(entry) && typeof entry.from === "function";
    }
    function isValidPartialFormatter(entry) {
        // partial formatters only need a to function and not a from function
        return typeof entry === "object" && typeof entry.to === "function";
    }
    function removeElement(el) {
        el.parentElement.removeChild(el);
    }
    function isSet(value) {
        return value !== null && value !== undefined;
    }
    // Bindable version
    function preventDefault(e) {
        e.preventDefault();
    }
    // Removes duplicates from an array.
    function unique(array) {
        return array.filter(function (a) {
            return !this[a] ? (this[a] = true) : false;
        }, {});
    }
    // Round a value to the closest 'to'.
    function closest(value, to) {
        return Math.round(value / to) * to;
    }
    // Current position of an element relative to the document.
    function offset(elem, orientation) {
        var rect = elem.getBoundingClientRect();
        var doc = elem.ownerDocument;
        var docElem = doc.documentElement;
        var pageOffset = getPageOffset(doc);
        // getBoundingClientRect contains left scroll in Chrome on Android.
        // I haven't found a feature detection that proves this. Worst case
        // scenario on mis-match: the 'tap' feature on horizontal sliders breaks.
        if (/webkit.*Chrome.*Mobile/i.test(navigator.userAgent)) {
            pageOffset.x = 0;
        }
        return orientation ? rect.top + pageOffset.y - docElem.clientTop : rect.left + pageOffset.x - docElem.clientLeft;
    }
    // Checks whether a value is numerical.
    function isNumeric(a) {
        return typeof a === "number" && !isNaN(a) && isFinite(a);
    }
    // Sets a class and removes it after [duration] ms.
    function addClassFor(element, className, duration) {
        if (duration > 0) {
            addClass(element, className);
            setTimeout(function () {
                removeClass(element, className);
            }, duration);
        }
    }
    // Limits a value to 0 - 100
    function limit(a) {
        return Math.max(Math.min(a, 100), 0);
    }
    // Wraps a variable as an array, if it isn't one yet.
    // Note that an input array is returned by reference!
    function asArray(a) {
        return Array.isArray(a) ? a : [a];
    }
    // Counts decimals
    function countDecimals(numStr) {
        numStr = String(numStr);
        var pieces = numStr.split(".");
        return pieces.length > 1 ? pieces[1].length : 0;
    }
    // http://youmightnotneedjquery.com/#add_class
    function addClass(el, className) {
        if (el.classList && !/\s/.test(className)) {
            el.classList.add(className);
        }
        else {
            el.className += " " + className;
        }
    }
    // http://youmightnotneedjquery.com/#remove_class
    function removeClass(el, className) {
        if (el.classList && !/\s/.test(className)) {
            el.classList.remove(className);
        }
        else {
            el.className = el.className.replace(new RegExp("(^|\\b)" + className.split(" ").join("|") + "(\\b|$)", "gi"), " ");
        }
    }
    // https://plainjs.com/javascript/attributes/adding-removing-and-testing-for-classes-9/
    function hasClass(el, className) {
        return el.classList ? el.classList.contains(className) : new RegExp("\\b" + className + "\\b").test(el.className);
    }
    // https://developer.mozilla.org/en-US/docs/Web/API/Window/scrollY#Notes
    function getPageOffset(doc) {
        var supportPageOffset = window.pageXOffset !== undefined;
        var isCSS1Compat = (doc.compatMode || "") === "CSS1Compat";
        var x = supportPageOffset
            ? window.pageXOffset
            : isCSS1Compat
                ? doc.documentElement.scrollLeft
                : doc.body.scrollLeft;
        var y = supportPageOffset
            ? window.pageYOffset
            : isCSS1Compat
                ? doc.documentElement.scrollTop
                : doc.body.scrollTop;
        return {
            x: x,
            y: y,
        };
    }
    // we provide a function to compute constants instead
    // of accessing window.* as soon as the module needs it
    // so that we do not compute anything if not needed
    function getActions() {
        // Determine the events to bind. IE11 implements pointerEvents without
        // a prefix, which breaks compatibility with the IE10 implementation.
        return window.navigator.pointerEnabled
            ? {
                start: "pointerdown",
                move: "pointermove",
                end: "pointerup",
            }
            : window.navigator.msPointerEnabled
                ? {
                    start: "MSPointerDown",
                    move: "MSPointerMove",
                    end: "MSPointerUp",
                }
                : {
                    start: "mousedown touchstart",
                    move: "mousemove touchmove",
                    end: "mouseup touchend",
                };
    }
    // https://github.com/WICG/EventListenerOptions/blob/gh-pages/explainer.md
    // Issue #785
    function getSupportsPassive() {
        var supportsPassive = false;
        /* eslint-disable */
        try {
            var opts = Object.defineProperty({}, "passive", {
                get: function () {
                    supportsPassive = true;
                },
            });
            // @ts-ignore
            window.addEventListener("test", null, opts);
        }
        catch (e) { }
        /* eslint-enable */
        return supportsPassive;
    }
    function getSupportsTouchActionNone() {
        return window.CSS && CSS.supports && CSS.supports("touch-action", "none");
    }
    //endregion
    //region Range Calculation
    // Determine the size of a sub-range in relation to a full range.
    function subRangeRatio(pa, pb) {
        return 100 / (pb - pa);
    }
    // (percentage) How many percent is this value of this range?
    function fromPercentage(range, value, startRange) {
        return (value * 100) / (range[startRange + 1] - range[startRange]);
    }
    // (percentage) Where is this value on this range?
    function toPercentage(range, value) {
        return fromPercentage(range, range[0] < 0 ? value + Math.abs(range[0]) : value - range[0], 0);
    }
    // (value) How much is this percentage on this range?
    function isPercentage(range, value) {
        return (value * (range[1] - range[0])) / 100 + range[0];
    }
    function getJ(value, arr) {
        var j = 1;
        while (value >= arr[j]) {
            j += 1;
        }
        return j;
    }
    // (percentage) Input a value, find where, on a scale of 0-100, it applies.
    function toStepping(xVal, xPct, value) {
        if (value >= xVal.slice(-1)[0]) {
            return 100;
        }
        var j = getJ(value, xVal);
        var va = xVal[j - 1];
        var vb = xVal[j];
        var pa = xPct[j - 1];
        var pb = xPct[j];
        return pa + toPercentage([va, vb], value) / subRangeRatio(pa, pb);
    }
    // (value) Input a percentage, find where it is on the specified range.
    function fromStepping(xVal, xPct, value) {
        // There is no range group that fits 100
        if (value >= 100) {
            return xVal.slice(-1)[0];
        }
        var j = getJ(value, xPct);
        var va = xVal[j - 1];
        var vb = xVal[j];
        var pa = xPct[j - 1];
        var pb = xPct[j];
        return isPercentage([va, vb], (value - pa) * subRangeRatio(pa, pb));
    }
    // (percentage) Get the step that applies at a certain value.
    function getStep(xPct, xSteps, snap, value) {
        if (value === 100) {
            return value;
        }
        var j = getJ(value, xPct);
        var a = xPct[j - 1];
        var b = xPct[j];
        // If 'snap' is set, steps are used as fixed points on the slider.
        if (snap) {
            // Find the closest position, a or b.
            if (value - a > (b - a) / 2) {
                return b;
            }
            return a;
        }
        if (!xSteps[j - 1]) {
            return value;
        }
        return xPct[j - 1] + closest(value - xPct[j - 1], xSteps[j - 1]);
    }
    //endregion
    //region Spectrum
    var Spectrum = /** @class */ (function () {
        function Spectrum(entry, snap, singleStep) {
            this.xPct = [];
            this.xVal = [];
            this.xSteps = [];
            this.xNumSteps = [];
            this.xHighestCompleteStep = [];
            this.xSteps = [singleStep || false];
            this.xNumSteps = [false];
            this.snap = snap;
            var index;
            var ordered = [];
            // Map the object keys to an array.
            Object.keys(entry).forEach(function (index) {
                ordered.push([asArray(entry[index]), index]);
            });
            // Sort all entries by value (numeric sort).
            ordered.sort(function (a, b) {
                return a[0][0] - b[0][0];
            });
            // Convert all entries to subranges.
            for (index = 0; index < ordered.length; index++) {
                this.handleEntryPoint(ordered[index][1], ordered[index][0]);
            }
            // Store the actual step values.
            // xSteps is sorted in the same order as xPct and xVal.
            this.xNumSteps = this.xSteps.slice(0);
            // Convert all numeric steps to the percentage of the subrange they represent.
            for (index = 0; index < this.xNumSteps.length; index++) {
                this.handleStepPoint(index, this.xNumSteps[index]);
            }
        }
        Spectrum.prototype.getDistance = function (value) {
            var distances = [];
            for (var index = 0; index < this.xNumSteps.length - 1; index++) {
                distances[index] = fromPercentage(this.xVal, value, index);
            }
            return distances;
        };
        // Calculate the percentual distance over the whole scale of ranges.
        // direction: 0 = backwards / 1 = forwards
        Spectrum.prototype.getAbsoluteDistance = function (value, distances, direction) {
            var xPct_index = 0;
            // Calculate range where to start calculation
            if (value < this.xPct[this.xPct.length - 1]) {
                while (value > this.xPct[xPct_index + 1]) {
                    xPct_index++;
                }
            }
            else if (value === this.xPct[this.xPct.length - 1]) {
                xPct_index = this.xPct.length - 2;
            }
            // If looking backwards and the value is exactly at a range separator then look one range further
            if (!direction && value === this.xPct[xPct_index + 1]) {
                xPct_index++;
            }
            if (distances === null) {
                distances = [];
            }
            var start_factor;
            var rest_factor = 1;
            var rest_rel_distance = distances[xPct_index];
            var range_pct = 0;
            var rel_range_distance = 0;
            var abs_distance_counter = 0;
            var range_counter = 0;
            // Calculate what part of the start range the value is
            if (direction) {
                start_factor = (value - this.xPct[xPct_index]) / (this.xPct[xPct_index + 1] - this.xPct[xPct_index]);
            }
            else {
                start_factor = (this.xPct[xPct_index + 1] - value) / (this.xPct[xPct_index + 1] - this.xPct[xPct_index]);
            }
            // Do until the complete distance across ranges is calculated
            while (rest_rel_distance > 0) {
                // Calculate the percentage of total range
                range_pct = this.xPct[xPct_index + 1 + range_counter] - this.xPct[xPct_index + range_counter];
                // Detect if the margin, padding or limit is larger then the current range and calculate
                if (distances[xPct_index + range_counter] * rest_factor + 100 - start_factor * 100 > 100) {
                    // If larger then take the percentual distance of the whole range
                    rel_range_distance = range_pct * start_factor;
                    // Rest factor of relative percentual distance still to be calculated
                    rest_factor = (rest_rel_distance - 100 * start_factor) / distances[xPct_index + range_counter];
                    // Set start factor to 1 as for next range it does not apply.
                    start_factor = 1;
                }
                else {
                    // If smaller or equal then take the percentual distance of the calculate percentual part of that range
                    rel_range_distance = ((distances[xPct_index + range_counter] * range_pct) / 100) * rest_factor;
                    // No rest left as the rest fits in current range
                    rest_factor = 0;
                }
                if (direction) {
                    abs_distance_counter = abs_distance_counter - rel_range_distance;
                    // Limit range to first range when distance becomes outside of minimum range
                    if (this.xPct.length + range_counter >= 1) {
                        range_counter--;
                    }
                }
                else {
                    abs_distance_counter = abs_distance_counter + rel_range_distance;
                    // Limit range to last range when distance becomes outside of maximum range
                    if (this.xPct.length - range_counter >= 1) {
                        range_counter++;
                    }
                }
                // Rest of relative percentual distance still to be calculated
                rest_rel_distance = distances[xPct_index + range_counter] * rest_factor;
            }
            return value + abs_distance_counter;
        };
        Spectrum.prototype.toStepping = function (value) {
            value = toStepping(this.xVal, this.xPct, value);
            return value;
        };
        Spectrum.prototype.fromStepping = function (value) {
            return fromStepping(this.xVal, this.xPct, value);
        };
        Spectrum.prototype.getStep = function (value) {
            value = getStep(this.xPct, this.xSteps, this.snap, value);
            return value;
        };
        Spectrum.prototype.getDefaultStep = function (value, isDown, size) {
            var j = getJ(value, this.xPct);
            // When at the top or stepping down, look at the previous sub-range
            if (value === 100 || (isDown && value === this.xPct[j - 1])) {
                j = Math.max(j - 1, 1);
            }
            return (this.xVal[j] - this.xVal[j - 1]) / size;
        };
        Spectrum.prototype.getNearbySteps = function (value) {
            var j = getJ(value, this.xPct);
            return {
                stepBefore: {
                    startValue: this.xVal[j - 2],
                    step: this.xNumSteps[j - 2],
                    highestStep: this.xHighestCompleteStep[j - 2],
                },
                thisStep: {
                    startValue: this.xVal[j - 1],
                    step: this.xNumSteps[j - 1],
                    highestStep: this.xHighestCompleteStep[j - 1],
                },
                stepAfter: {
                    startValue: this.xVal[j],
                    step: this.xNumSteps[j],
                    highestStep: this.xHighestCompleteStep[j],
                },
            };
        };
        Spectrum.prototype.countStepDecimals = function () {
            var stepDecimals = this.xNumSteps.map(countDecimals);
            return Math.max.apply(null, stepDecimals);
        };
        Spectrum.prototype.hasNoSize = function () {
            return this.xVal[0] === this.xVal[this.xVal.length - 1];
        };
        // Outside testing
        Spectrum.prototype.convert = function (value) {
            return this.getStep(this.toStepping(value));
        };
        Spectrum.prototype.handleEntryPoint = function (index, value) {
            var percentage;
            // Covert min/max syntax to 0 and 100.
            if (index === "min") {
                percentage = 0;
            }
            else if (index === "max") {
                percentage = 100;
            }
            else {
                percentage = parseFloat(index);
            }
            // Check for correct input.
            if (!isNumeric(percentage) || !isNumeric(value[0])) {
                throw new Error("noUiSlider: 'range' value isn't numeric.");
            }
            // Store values.
            this.xPct.push(percentage);
            this.xVal.push(value[0]);
            var value1 = Number(value[1]);
            // NaN will evaluate to false too, but to keep
            // logging clear, set step explicitly. Make sure
            // not to override the 'step' setting with false.
            if (!percentage) {
                if (!isNaN(value1)) {
                    this.xSteps[0] = value1;
                }
            }
            else {
                this.xSteps.push(isNaN(value1) ? false : value1);
            }
            this.xHighestCompleteStep.push(0);
        };
        Spectrum.prototype.handleStepPoint = function (i, n) {
            // Ignore 'false' stepping.
            if (!n) {
                return;
            }
            // Step over zero-length ranges (#948);
            if (this.xVal[i] === this.xVal[i + 1]) {
                this.xSteps[i] = this.xHighestCompleteStep[i] = this.xVal[i];
                return;
            }
            // Factor to range ratio
            this.xSteps[i] =
                fromPercentage([this.xVal[i], this.xVal[i + 1]], n, 0) / subRangeRatio(this.xPct[i], this.xPct[i + 1]);
            var totalSteps = (this.xVal[i + 1] - this.xVal[i]) / this.xNumSteps[i];
            var highestStep = Math.ceil(Number(totalSteps.toFixed(3)) - 1);
            var step = this.xVal[i] + this.xNumSteps[i] * highestStep;
            this.xHighestCompleteStep[i] = step;
        };
        return Spectrum;
    }());
    //endregion
    //region Options
    /*	Every input option is tested and parsed. This will prevent
        endless validation in internal methods. These tests are
        structured with an item for every option available. An
        option can be marked as required by setting the 'r' flag.
        The testing function is provided with three arguments:
            - The provided value for the option;
            - A reference to the options object;
            - The name for the option;

        The testing function returns false when an error is detected,
        or true when everything is OK. It can also modify the option
        object, to make sure all values can be correctly looped elsewhere. */
    //region Defaults
    var defaultFormatter = {
        to: function (value) {
            return value === undefined ? "" : value.toFixed(2);
        },
        from: Number,
    };
    var cssClasses = {
        target: "target",
        base: "base",
        origin: "origin",
        handle: "handle",
        handleLower: "handle-lower",
        handleUpper: "handle-upper",
        touchArea: "touch-area",
        horizontal: "horizontal",
        vertical: "vertical",
        background: "background",
        connect: "connect",
        connects: "connects",
        ltr: "ltr",
        rtl: "rtl",
        textDirectionLtr: "txt-dir-ltr",
        textDirectionRtl: "txt-dir-rtl",
        draggable: "draggable",
        drag: "state-drag",
        tap: "state-tap",
        active: "active",
        tooltip: "tooltip",
        pips: "pips",
        pipsHorizontal: "pips-horizontal",
        pipsVertical: "pips-vertical",
        marker: "marker",
        markerHorizontal: "marker-horizontal",
        markerVertical: "marker-vertical",
        markerNormal: "marker-normal",
        markerLarge: "marker-large",
        markerSub: "marker-sub",
        value: "value",
        valueHorizontal: "value-horizontal",
        valueVertical: "value-vertical",
        valueNormal: "value-normal",
        valueLarge: "value-large",
        valueSub: "value-sub",
    };
    // Namespaces of internal event listeners
    var INTERNAL_EVENT_NS = {
        tooltips: ".__tooltips",
        aria: ".__aria",
    };
    //endregion
    function testStep(parsed, entry) {
        if (!isNumeric(entry)) {
            throw new Error("noUiSlider: 'step' is not numeric.");
        }
        // The step option can still be used to set stepping
        // for linear sliders. Overwritten if set in 'range'.
        parsed.singleStep = entry;
    }
    function testKeyboardPageMultiplier(parsed, entry) {
        if (!isNumeric(entry)) {
            throw new Error("noUiSlider: 'keyboardPageMultiplier' is not numeric.");
        }
        parsed.keyboardPageMultiplier = entry;
    }
    function testKeyboardMultiplier(parsed, entry) {
        if (!isNumeric(entry)) {
            throw new Error("noUiSlider: 'keyboardMultiplier' is not numeric.");
        }
        parsed.keyboardMultiplier = entry;
    }
    function testKeyboardDefaultStep(parsed, entry) {
        if (!isNumeric(entry)) {
            throw new Error("noUiSlider: 'keyboardDefaultStep' is not numeric.");
        }
        parsed.keyboardDefaultStep = entry;
    }
    function testRange(parsed, entry) {
        // Filter incorrect input.
        if (typeof entry !== "object" || Array.isArray(entry)) {
            throw new Error("noUiSlider: 'range' is not an object.");
        }
        // Catch missing start or end.
        if (entry.min === undefined || entry.max === undefined) {
            throw new Error("noUiSlider: Missing 'min' or 'max' in 'range'.");
        }
        parsed.spectrum = new Spectrum(entry, parsed.snap || false, parsed.singleStep);
    }
    function testStart(parsed, entry) {
        entry = asArray(entry);
        // Validate input. Values aren't tested, as the public .val method
        // will always provide a valid location.
        if (!Array.isArray(entry) || !entry.length) {
            throw new Error("noUiSlider: 'start' option is incorrect.");
        }
        // Store the number of handles.
        parsed.handles = entry.length;
        // When the slider is initialized, the .val method will
        // be called with the start options.
        parsed.start = entry;
    }
    function testSnap(parsed, entry) {
        if (typeof entry !== "boolean") {
            throw new Error("noUiSlider: 'snap' option must be a boolean.");
        }
        // Enforce 100% stepping within subranges.
        parsed.snap = entry;
    }
    function testAnimate(parsed, entry) {
        if (typeof entry !== "boolean") {
            throw new Error("noUiSlider: 'animate' option must be a boolean.");
        }
        // Enforce 100% stepping within subranges.
        parsed.animate = entry;
    }
    function testAnimationDuration(parsed, entry) {
        if (typeof entry !== "number") {
            throw new Error("noUiSlider: 'animationDuration' option must be a number.");
        }
        parsed.animationDuration = entry;
    }
    function testConnect(parsed, entry) {
        var connect = [false];
        var i;
        // Map legacy options
        if (entry === "lower") {
            entry = [true, false];
        }
        else if (entry === "upper") {
            entry = [false, true];
        }
        // Handle boolean options
        if (entry === true || entry === false) {
            for (i = 1; i < parsed.handles; i++) {
                connect.push(entry);
            }
            connect.push(false);
        }
        // Reject invalid input
        else if (!Array.isArray(entry) || !entry.length || entry.length !== parsed.handles + 1) {
            throw new Error("noUiSlider: 'connect' option doesn't match handle count.");
        }
        else {
            connect = entry;
        }
        parsed.connect = connect;
    }
    function testOrientation(parsed, entry) {
        // Set orientation to an a numerical value for easy
        // array selection.
        switch (entry) {
            case "horizontal":
                parsed.ort = 0;
                break;
            case "vertical":
                parsed.ort = 1;
                break;
            default:
                throw new Error("noUiSlider: 'orientation' option is invalid.");
        }
    }
    function testMargin(parsed, entry) {
        if (!isNumeric(entry)) {
            throw new Error("noUiSlider: 'margin' option must be numeric.");
        }
        // Issue #582
        if (entry === 0) {
            return;
        }
        parsed.margin = parsed.spectrum.getDistance(entry);
    }
    function testLimit(parsed, entry) {
        if (!isNumeric(entry)) {
            throw new Error("noUiSlider: 'limit' option must be numeric.");
        }
        parsed.limit = parsed.spectrum.getDistance(entry);
        if (!parsed.limit || parsed.handles < 2) {
            throw new Error("noUiSlider: 'limit' option is only supported on linear sliders with 2 or more handles.");
        }
    }
    function testPadding(parsed, entry) {
        var index;
        if (!isNumeric(entry) && !Array.isArray(entry)) {
            throw new Error("noUiSlider: 'padding' option must be numeric or array of exactly 2 numbers.");
        }
        if (Array.isArray(entry) && !(entry.length === 2 || isNumeric(entry[0]) || isNumeric(entry[1]))) {
            throw new Error("noUiSlider: 'padding' option must be numeric or array of exactly 2 numbers.");
        }
        if (entry === 0) {
            return;
        }
        if (!Array.isArray(entry)) {
            entry = [entry, entry];
        }
        // 'getDistance' returns false for invalid values.
        parsed.padding = [parsed.spectrum.getDistance(entry[0]), parsed.spectrum.getDistance(entry[1])];
        for (index = 0; index < parsed.spectrum.xNumSteps.length - 1; index++) {
            // last "range" can't contain step size as it is purely an endpoint.
            if (parsed.padding[0][index] < 0 || parsed.padding[1][index] < 0) {
                throw new Error("noUiSlider: 'padding' option must be a positive number(s).");
            }
        }
        var totalPadding = entry[0] + entry[1];
        var firstValue = parsed.spectrum.xVal[0];
        var lastValue = parsed.spectrum.xVal[parsed.spectrum.xVal.length - 1];
        if (totalPadding / (lastValue - firstValue) > 1) {
            throw new Error("noUiSlider: 'padding' option must not exceed 100% of the range.");
        }
    }
    function testDirection(parsed, entry) {
        // Set direction as a numerical value for easy parsing.
        // Invert connection for RTL sliders, so that the proper
        // handles get the connect/background classes.
        switch (entry) {
            case "ltr":
                parsed.dir = 0;
                break;
            case "rtl":
                parsed.dir = 1;
                break;
            default:
                throw new Error("noUiSlider: 'direction' option was not recognized.");
        }
    }
    function testBehaviour(parsed, entry) {
        // Make sure the input is a string.
        if (typeof entry !== "string") {
            throw new Error("noUiSlider: 'behaviour' must be a string containing options.");
        }
        // Check if the string contains any keywords.
        // None are required.
        var tap = entry.indexOf("tap") >= 0;
        var drag = entry.indexOf("drag") >= 0;
        var fixed = entry.indexOf("fixed") >= 0;
        var snap = entry.indexOf("snap") >= 0;
        var hover = entry.indexOf("hover") >= 0;
        var unconstrained = entry.indexOf("unconstrained") >= 0;
        var dragAll = entry.indexOf("drag-all") >= 0;
        if (fixed) {
            if (parsed.handles !== 2) {
                throw new Error("noUiSlider: 'fixed' behaviour must be used with 2 handles");
            }
            // Use margin to enforce fixed state
            testMargin(parsed, parsed.start[1] - parsed.start[0]);
        }
        if (unconstrained && (parsed.margin || parsed.limit)) {
            throw new Error("noUiSlider: 'unconstrained' behaviour cannot be used with margin or limit");
        }
        parsed.events = {
            tap: tap || snap,
            drag: drag,
            dragAll: dragAll,
            fixed: fixed,
            snap: snap,
            hover: hover,
            unconstrained: unconstrained,
        };
    }
    function testTooltips(parsed, entry) {
        if (entry === false) {
            return;
        }
        if (entry === true || isValidPartialFormatter(entry)) {
            parsed.tooltips = [];
            for (var i = 0; i < parsed.handles; i++) {
                parsed.tooltips.push(entry);
            }
        }
        else {
            entry = asArray(entry);
            if (entry.length !== parsed.handles) {
                throw new Error("noUiSlider: must pass a formatter for all handles.");
            }
            entry.forEach(function (formatter) {
                if (typeof formatter !== "boolean" && !isValidPartialFormatter(formatter)) {
                    throw new Error("noUiSlider: 'tooltips' must be passed a formatter or 'false'.");
                }
            });
            parsed.tooltips = entry;
        }
    }
    function testHandleAttributes(parsed, entry) {
        if (entry.length !== parsed.handles) {
            throw new Error("noUiSlider: must pass a attributes for all handles.");
        }
        parsed.handleAttributes = entry;
    }
    function testAriaFormat(parsed, entry) {
        if (!isValidPartialFormatter(entry)) {
            throw new Error("noUiSlider: 'ariaFormat' requires 'to' method.");
        }
        parsed.ariaFormat = entry;
    }
    function testFormat(parsed, entry) {
        if (!isValidFormatter(entry)) {
            throw new Error("noUiSlider: 'format' requires 'to' and 'from' methods.");
        }
        parsed.format = entry;
    }
    function testKeyboardSupport(parsed, entry) {
        if (typeof entry !== "boolean") {
            throw new Error("noUiSlider: 'keyboardSupport' option must be a boolean.");
        }
        parsed.keyboardSupport = entry;
    }
    function testDocumentElement(parsed, entry) {
        // This is an advanced option. Passed values are used without validation.
        parsed.documentElement = entry;
    }
    function testCssPrefix(parsed, entry) {
        if (typeof entry !== "string" && entry !== false) {
            throw new Error("noUiSlider: 'cssPrefix' must be a string or `false`.");
        }
        parsed.cssPrefix = entry;
    }
    function testCssClasses(parsed, entry) {
        if (typeof entry !== "object") {
            throw new Error("noUiSlider: 'cssClasses' must be an object.");
        }
        if (typeof parsed.cssPrefix === "string") {
            parsed.cssClasses = {};
            Object.keys(entry).forEach(function (key) {
                parsed.cssClasses[key] = parsed.cssPrefix + entry[key];
            });
        }
        else {
            parsed.cssClasses = entry;
        }
    }
    // Test all developer settings and parse to assumption-safe values.
    function testOptions(options) {
        // To prove a fix for #537, freeze options here.
        // If the object is modified, an error will be thrown.
        // Object.freeze(options);
        var parsed = {
            margin: null,
            limit: null,
            padding: null,
            animate: true,
            animationDuration: 300,
            ariaFormat: defaultFormatter,
            format: defaultFormatter,
        };
        // Tests are executed in the order they are presented here.
        var tests = {
            step: { r: false, t: testStep },
            keyboardPageMultiplier: { r: false, t: testKeyboardPageMultiplier },
            keyboardMultiplier: { r: false, t: testKeyboardMultiplier },
            keyboardDefaultStep: { r: false, t: testKeyboardDefaultStep },
            start: { r: true, t: testStart },
            connect: { r: true, t: testConnect },
            direction: { r: true, t: testDirection },
            snap: { r: false, t: testSnap },
            animate: { r: false, t: testAnimate },
            animationDuration: { r: false, t: testAnimationDuration },
            range: { r: true, t: testRange },
            orientation: { r: false, t: testOrientation },
            margin: { r: false, t: testMargin },
            limit: { r: false, t: testLimit },
            padding: { r: false, t: testPadding },
            behaviour: { r: true, t: testBehaviour },
            ariaFormat: { r: false, t: testAriaFormat },
            format: { r: false, t: testFormat },
            tooltips: { r: false, t: testTooltips },
            keyboardSupport: { r: true, t: testKeyboardSupport },
            documentElement: { r: false, t: testDocumentElement },
            cssPrefix: { r: true, t: testCssPrefix },
            cssClasses: { r: true, t: testCssClasses },
            handleAttributes: { r: false, t: testHandleAttributes },
        };
        var defaults = {
            connect: false,
            direction: "ltr",
            behaviour: "tap",
            orientation: "horizontal",
            keyboardSupport: true,
            cssPrefix: "noUi-",
            cssClasses: cssClasses,
            keyboardPageMultiplier: 5,
            keyboardMultiplier: 1,
            keyboardDefaultStep: 10,
        };
        // AriaFormat defaults to regular format, if any.
        if (options.format && !options.ariaFormat) {
            options.ariaFormat = options.format;
        }
        // Run all options through a testing mechanism to ensure correct
        // input. It should be noted that options might get modified to
        // be handled properly. E.g. wrapping integers in arrays.
        Object.keys(tests).forEach(function (name) {
            // If the option isn't set, but it is required, throw an error.
            if (!isSet(options[name]) && defaults[name] === undefined) {
                if (tests[name].r) {
                    throw new Error("noUiSlider: '" + name + "' is required.");
                }
                return;
            }
            tests[name].t(parsed, !isSet(options[name]) ? defaults[name] : options[name]);
        });
        // Forward pips options
        parsed.pips = options.pips;
        // All recent browsers accept unprefixed transform.
        // We need -ms- for IE9 and -webkit- for older Android;
        // Assume use of -webkit- if unprefixed and -ms- are not supported.
        // https://caniuse.com/#feat=transforms2d
        var d = document.createElement("div");
        var msPrefix = d.style.msTransform !== undefined;
        var noPrefix = d.style.transform !== undefined;
        parsed.transformRule = noPrefix ? "transform" : msPrefix ? "msTransform" : "webkitTransform";
        // Pips don't move, so we can place them using left/top.
        var styles = [
            ["left", "top"],
            ["right", "bottom"],
        ];
        parsed.style = styles[parsed.dir][parsed.ort];
        return parsed;
    }
    //endregion
    function scope(target, options, originalOptions) {
        var actions = getActions();
        var supportsTouchActionNone = getSupportsTouchActionNone();
        var supportsPassive = supportsTouchActionNone && getSupportsPassive();
        // All variables local to 'scope' are prefixed with 'scope_'
        // Slider DOM Nodes
        var scope_Target = target;
        var scope_Base;
        var scope_Handles;
        var scope_Connects;
        var scope_Pips;
        var scope_Tooltips;
        // Slider state values
        var scope_Spectrum = options.spectrum;
        var scope_Values = [];
        var scope_Locations = [];
        var scope_HandleNumbers = [];
        var scope_ActiveHandlesCount = 0;
        var scope_Events = {};
        // Document Nodes
        var scope_Document = target.ownerDocument;
        var scope_DocumentElement = options.documentElement || scope_Document.documentElement;
        var scope_Body = scope_Document.body;
        // For horizontal sliders in standard ltr documents,
        // make .noUi-origin overflow to the left so the document doesn't scroll.
        var scope_DirOffset = scope_Document.dir === "rtl" || options.ort === 1 ? 0 : 100;
        // Creates a node, adds it to target, returns the new node.
        function addNodeTo(addTarget, className) {
            var div = scope_Document.createElement("div");
            if (className) {
                addClass(div, className);
            }
            addTarget.appendChild(div);
            return div;
        }
        // Append a origin to the base
        function addOrigin(base, handleNumber) {
            var origin = addNodeTo(base, options.cssClasses.origin);
            var handle = addNodeTo(origin, options.cssClasses.handle);
            addNodeTo(handle, options.cssClasses.touchArea);
            handle.setAttribute("data-handle", String(handleNumber));
            if (options.keyboardSupport) {
                // https://developer.mozilla.org/en-US/docs/Web/HTML/Global_attributes/tabindex
                // 0 = focusable and reachable
                handle.setAttribute("tabindex", "0");
                handle.addEventListener("keydown", function (event) {
                    return eventKeydown(event, handleNumber);
                });
            }
            if (options.handleAttributes !== undefined) {
                var attributes_1 = options.handleAttributes[handleNumber];
                Object.keys(attributes_1).forEach(function (attribute) {
                    handle.setAttribute(attribute, attributes_1[attribute]);
                });
            }
            handle.setAttribute("role", "slider");
            handle.setAttribute("aria-orientation", options.ort ? "vertical" : "horizontal");
            if (handleNumber === 0) {
                addClass(handle, options.cssClasses.handleLower);
            }
            else if (handleNumber === options.handles - 1) {
                addClass(handle, options.cssClasses.handleUpper);
            }
            return origin;
        }
        // Insert nodes for connect elements
        function addConnect(base, add) {
            if (!add) {
                return false;
            }
            return addNodeTo(base, options.cssClasses.connect);
        }
        // Add handles to the slider base.
        function addElements(connectOptions, base) {
            var connectBase = addNodeTo(base, options.cssClasses.connects);
            scope_Handles = [];
            scope_Connects = [];
            scope_Connects.push(addConnect(connectBase, connectOptions[0]));
            // [::::O====O====O====]
            // connectOptions = [0, 1, 1, 1]
            for (var i = 0; i < options.handles; i++) {
                // Keep a list of all added handles.
                scope_Handles.push(addOrigin(base, i));
                scope_HandleNumbers[i] = i;
                scope_Connects.push(addConnect(connectBase, connectOptions[i + 1]));
            }
        }
        // Initialize a single slider.
        function addSlider(addTarget) {
            // Apply classes and data to the target.
            addClass(addTarget, options.cssClasses.target);
            if (options.dir === 0) {
                addClass(addTarget, options.cssClasses.ltr);
            }
            else {
                addClass(addTarget, options.cssClasses.rtl);
            }
            if (options.ort === 0) {
                addClass(addTarget, options.cssClasses.horizontal);
            }
            else {
                addClass(addTarget, options.cssClasses.vertical);
            }
            var textDirection = getComputedStyle(addTarget).direction;
            if (textDirection === "rtl") {
                addClass(addTarget, options.cssClasses.textDirectionRtl);
            }
            else {
                addClass(addTarget, options.cssClasses.textDirectionLtr);
            }
            return addNodeTo(addTarget, options.cssClasses.base);
        }
        function addTooltip(handle, handleNumber) {
            if (!options.tooltips || !options.tooltips[handleNumber]) {
                return false;
            }
            return addNodeTo(handle.firstChild, options.cssClasses.tooltip);
        }
        function isSliderDisabled() {
            return scope_Target.hasAttribute("disabled");
        }
        // Disable the slider dragging if any handle is disabled
        function isHandleDisabled(handleNumber) {
            var handleOrigin = scope_Handles[handleNumber];
            return handleOrigin.hasAttribute("disabled");
        }
        function removeTooltips() {
            if (scope_Tooltips) {
                removeEvent("update" + INTERNAL_EVENT_NS.tooltips);
                scope_Tooltips.forEach(function (tooltip) {
                    if (tooltip) {
                        removeElement(tooltip);
                    }
                });
                scope_Tooltips = null;
            }
        }
        // The tooltips option is a shorthand for using the 'update' event.
        function tooltips() {
            removeTooltips();
            // Tooltips are added with options.tooltips in original order.
            scope_Tooltips = scope_Handles.map(addTooltip);
            bindEvent("update" + INTERNAL_EVENT_NS.tooltips, function (values, handleNumber, unencoded) {
                if (!scope_Tooltips || !options.tooltips) {
                    return;
                }
                if (scope_Tooltips[handleNumber] === false) {
                    return;
                }
                var formattedValue = values[handleNumber];
                if (options.tooltips[handleNumber] !== true) {
                    formattedValue = options.tooltips[handleNumber].to(unencoded[handleNumber]);
                }
                scope_Tooltips[handleNumber].innerHTML = formattedValue;
            });
        }
        function aria() {
            removeEvent("update" + INTERNAL_EVENT_NS.aria);
            bindEvent("update" + INTERNAL_EVENT_NS.aria, function (values, handleNumber, unencoded, tap, positions) {
                // Update Aria Values for all handles, as a change in one changes min and max values for the next.
                scope_HandleNumbers.forEach(function (index) {
                    var handle = scope_Handles[index];
                    var min = checkHandlePosition(scope_Locations, index, 0, true, true, true);
                    var max = checkHandlePosition(scope_Locations, index, 100, true, true, true);
                    var now = positions[index];
                    // Formatted value for display
                    var text = String(options.ariaFormat.to(unencoded[index]));
                    // Map to slider range values
                    min = scope_Spectrum.fromStepping(min).toFixed(1);
                    max = scope_Spectrum.fromStepping(max).toFixed(1);
                    now = scope_Spectrum.fromStepping(now).toFixed(1);
                    handle.children[0].setAttribute("aria-valuemin", min);
                    handle.children[0].setAttribute("aria-valuemax", max);
                    handle.children[0].setAttribute("aria-valuenow", now);
                    handle.children[0].setAttribute("aria-valuetext", text);
                });
            });
        }
        function getGroup(pips) {
            // Use the range.
            if (pips.mode === exports.PipsMode.Range || pips.mode === exports.PipsMode.Steps) {
                return scope_Spectrum.xVal;
            }
            if (pips.mode === exports.PipsMode.Count) {
                if (pips.values < 2) {
                    throw new Error("noUiSlider: 'values' (>= 2) required for mode 'count'.");
                }
                // Divide 0 - 100 in 'count' parts.
                var interval = pips.values - 1;
                var spread = 100 / interval;
                var values = [];
                // List these parts and have them handled as 'positions'.
                while (interval--) {
                    values[interval] = interval * spread;
                }
                values.push(100);
                return mapToRange(values, pips.stepped);
            }
            if (pips.mode === exports.PipsMode.Positions) {
                // Map all percentages to on-range values.
                return mapToRange(pips.values, pips.stepped);
            }
            if (pips.mode === exports.PipsMode.Values) {
                // If the value must be stepped, it needs to be converted to a percentage first.
                if (pips.stepped) {
                    return pips.values.map(function (value) {
                        // Convert to percentage, apply step, return to value.
                        return scope_Spectrum.fromStepping(scope_Spectrum.getStep(scope_Spectrum.toStepping(value)));
                    });
                }
                // Otherwise, we can simply use the values.
                return pips.values;
            }
            return []; // pips.mode = never
        }
        function mapToRange(values, stepped) {
            return values.map(function (value) {
                return scope_Spectrum.fromStepping(stepped ? scope_Spectrum.getStep(value) : value);
            });
        }
        function generateSpread(pips) {
            function safeIncrement(value, increment) {
                // Avoid floating point variance by dropping the smallest decimal places.
                return Number((value + increment).toFixed(7));
            }
            var group = getGroup(pips);
            var indexes = {};
            var firstInRange = scope_Spectrum.xVal[0];
            var lastInRange = scope_Spectrum.xVal[scope_Spectrum.xVal.length - 1];
            var ignoreFirst = false;
            var ignoreLast = false;
            var prevPct = 0;
            // Create a copy of the group, sort it and filter away all duplicates.
            group = unique(group.slice().sort(function (a, b) {
                return a - b;
            }));
            // Make sure the range starts with the first element.
            if (group[0] !== firstInRange) {
                group.unshift(firstInRange);
                ignoreFirst = true;
            }
            // Likewise for the last one.
            if (group[group.length - 1] !== lastInRange) {
                group.push(lastInRange);
                ignoreLast = true;
            }
            group.forEach(function (current, index) {
                // Get the current step and the lower + upper positions.
                var step;
                var i;
                var q;
                var low = current;
                var high = group[index + 1];
                var newPct;
                var pctDifference;
                var pctPos;
                var type;
                var steps;
                var realSteps;
                var stepSize;
                var isSteps = pips.mode === exports.PipsMode.Steps;
                // When using 'steps' mode, use the provided steps.
                // Otherwise, we'll step on to the next subrange.
                if (isSteps) {
                    step = scope_Spectrum.xNumSteps[index];
                }
                // Default to a 'full' step.
                if (!step) {
                    step = high - low;
                }
                // If high is undefined we are at the last subrange. Make sure it iterates once (#1088)
                if (high === undefined) {
                    high = low;
                }
                // Make sure step isn't 0, which would cause an infinite loop (#654)
                step = Math.max(step, 0.0000001);
                // Find all steps in the subrange.
                for (i = low; i <= high; i = safeIncrement(i, step)) {
                    // Get the percentage value for the current step,
                    // calculate the size for the subrange.
                    newPct = scope_Spectrum.toStepping(i);
                    pctDifference = newPct - prevPct;
                    steps = pctDifference / (pips.density || 1);
                    realSteps = Math.round(steps);
                    // This ratio represents the amount of percentage-space a point indicates.
                    // For a density 1 the points/percentage = 1. For density 2, that percentage needs to be re-divided.
                    // Round the percentage offset to an even number, then divide by two
                    // to spread the offset on both sides of the range.
                    stepSize = pctDifference / realSteps;
                    // Divide all points evenly, adding the correct number to this subrange.
                    // Run up to <= so that 100% gets a point, event if ignoreLast is set.
                    for (q = 1; q <= realSteps; q += 1) {
                        // The ratio between the rounded value and the actual size might be ~1% off.
                        // Correct the percentage offset by the number of points
                        // per subrange. density = 1 will result in 100 points on the
                        // full range, 2 for 50, 4 for 25, etc.
                        pctPos = prevPct + q * stepSize;
                        indexes[pctPos.toFixed(5)] = [scope_Spectrum.fromStepping(pctPos), 0];
                    }
                    // Determine the point type.
                    type = group.indexOf(i) > -1 ? exports.PipsType.LargeValue : isSteps ? exports.PipsType.SmallValue : exports.PipsType.NoValue;
                    // Enforce the 'ignoreFirst' option by overwriting the type for 0.
                    if (!index && ignoreFirst && i !== high) {
                        type = 0;
                    }
                    if (!(i === high && ignoreLast)) {
                        // Mark the 'type' of this point. 0 = plain, 1 = real value, 2 = step value.
                        indexes[newPct.toFixed(5)] = [i, type];
                    }
                    // Update the percentage count.
                    prevPct = newPct;
                }
            });
            return indexes;
        }
        function addMarking(spread, filterFunc, formatter) {
            var _a, _b;
            var element = scope_Document.createElement("div");
            var valueSizeClasses = (_a = {},
                _a[exports.PipsType.None] = "",
                _a[exports.PipsType.NoValue] = options.cssClasses.valueNormal,
                _a[exports.PipsType.LargeValue] = options.cssClasses.valueLarge,
                _a[exports.PipsType.SmallValue] = options.cssClasses.valueSub,
                _a);
            var markerSizeClasses = (_b = {},
                _b[exports.PipsType.None] = "",
                _b[exports.PipsType.NoValue] = options.cssClasses.markerNormal,
                _b[exports.PipsType.LargeValue] = options.cssClasses.markerLarge,
                _b[exports.PipsType.SmallValue] = options.cssClasses.markerSub,
                _b);
            var valueOrientationClasses = [options.cssClasses.valueHorizontal, options.cssClasses.valueVertical];
            var markerOrientationClasses = [options.cssClasses.markerHorizontal, options.cssClasses.markerVertical];
            addClass(element, options.cssClasses.pips);
            addClass(element, options.ort === 0 ? options.cssClasses.pipsHorizontal : options.cssClasses.pipsVertical);
            function getClasses(type, source) {
                var a = source === options.cssClasses.value;
                var orientationClasses = a ? valueOrientationClasses : markerOrientationClasses;
                var sizeClasses = a ? valueSizeClasses : markerSizeClasses;
                return source + " " + orientationClasses[options.ort] + " " + sizeClasses[type];
            }
            function addSpread(offset, value, type) {
                // Apply the filter function, if it is set.
                type = filterFunc ? filterFunc(value, type) : type;
                if (type === exports.PipsType.None) {
                    return;
                }
                // Add a marker for every point
                var node = addNodeTo(element, false);
                node.className = getClasses(type, options.cssClasses.marker);
                node.style[options.style] = offset + "%";
                // Values are only appended for points marked '1' or '2'.
                if (type > exports.PipsType.NoValue) {
                    node = addNodeTo(element, false);
                    node.className = getClasses(type, options.cssClasses.value);
                    node.setAttribute("data-value", String(value));
                    node.style[options.style] = offset + "%";
                    node.innerHTML = String(formatter.to(value));
                }
            }
            // Append all points.
            Object.keys(spread).forEach(function (offset) {
                addSpread(offset, spread[offset][0], spread[offset][1]);
            });
            return element;
        }
        function removePips() {
            if (scope_Pips) {
                removeElement(scope_Pips);
                scope_Pips = null;
            }
        }
        function pips(pips) {
            // Fix #669
            removePips();
            var spread = generateSpread(pips);
            var filter = pips.filter;
            var format = pips.format || {
                to: function (value) {
                    return String(Math.round(value));
                },
            };
            scope_Pips = scope_Target.appendChild(addMarking(spread, filter, format));
            return scope_Pips;
        }
        // Shorthand for base dimensions.
        function baseSize() {
            var rect = scope_Base.getBoundingClientRect();
            var alt = ("offset" + ["Width", "Height"][options.ort]);
            return options.ort === 0 ? rect.width || scope_Base[alt] : rect.height || scope_Base[alt];
        }
        // Handler for attaching events trough a proxy.
        function attachEvent(events, element, callback, data) {
            // This function can be used to 'filter' events to the slider.
            // element is a node, not a nodeList
            var method = function (event) {
                var e = fixEvent(event, data.pageOffset, data.target || element);
                // fixEvent returns false if this event has a different target
                // when handling (multi-) touch events;
                if (!e) {
                    return false;
                }
                // doNotReject is passed by all end events to make sure released touches
                // are not rejected, leaving the slider "stuck" to the cursor;
                if (isSliderDisabled() && !data.doNotReject) {
                    return false;
                }
                // Stop if an active 'tap' transition is taking place.
                if (hasClass(scope_Target, options.cssClasses.tap) && !data.doNotReject) {
                    return false;
                }
                // Ignore right or middle clicks on start #454
                if (events === actions.start && e.buttons !== undefined && e.buttons > 1) {
                    return false;
                }
                // Ignore right or middle clicks on start #454
                if (data.hover && e.buttons) {
                    return false;
                }
                // 'supportsPassive' is only true if a browser also supports touch-action: none in CSS.
                // iOS safari does not, so it doesn't get to benefit from passive scrolling. iOS does support
                // touch-action: manipulation, but that allows panning, which breaks
                // sliders after zooming/on non-responsive pages.
                // See: https://bugs.webkit.org/show_bug.cgi?id=133112
                if (!supportsPassive) {
                    e.preventDefault();
                }
                e.calcPoint = e.points[options.ort];
                // Call the event handler with the event [ and additional data ].
                callback(e, data);
                return;
            };
            var methods = [];
            // Bind a closure on the target for every event type.
            events.split(" ").forEach(function (eventName) {
                element.addEventListener(eventName, method, supportsPassive ? { passive: true } : false);
                methods.push([eventName, method]);
            });
            return methods;
        }
        // Provide a clean event with standardized offset values.
        function fixEvent(e, pageOffset, eventTarget) {
            // Filter the event to register the type, which can be
            // touch, mouse or pointer. Offset changes need to be
            // made on an event specific basis.
            var touch = e.type.indexOf("touch") === 0;
            var mouse = e.type.indexOf("mouse") === 0;
            var pointer = e.type.indexOf("pointer") === 0;
            var x = 0;
            var y = 0;
            // IE10 implemented pointer events with a prefix;
            if (e.type.indexOf("MSPointer") === 0) {
                pointer = true;
            }
            // Erroneous events seem to be passed in occasionally on iOS/iPadOS after user finishes interacting with
            // the slider. They appear to be of type MouseEvent, yet they don't have usual properties set. Ignore
            // events that have no touches or buttons associated with them. (#1057, #1079, #1095)
            if (e.type === "mousedown" && !e.buttons && !e.touches) {
                return false;
            }
            // The only thing one handle should be concerned about is the touches that originated on top of it.
            if (touch) {
                // Returns true if a touch originated on the target.
                var isTouchOnTarget = function (checkTouch) {
                    var target = checkTouch.target;
                    return (target === eventTarget ||
                        eventTarget.contains(target) ||
                        (e.composed && e.composedPath().shift() === eventTarget));
                };
                // In the case of touchstart events, we need to make sure there is still no more than one
                // touch on the target so we look amongst all touches.
                if (e.type === "touchstart") {
                    var targetTouches = Array.prototype.filter.call(e.touches, isTouchOnTarget);
                    // Do not support more than one touch per handle.
                    if (targetTouches.length > 1) {
                        return false;
                    }
                    x = targetTouches[0].pageX;
                    y = targetTouches[0].pageY;
                }
                else {
                    // In the other cases, find on changedTouches is enough.
                    var targetTouch = Array.prototype.find.call(e.changedTouches, isTouchOnTarget);
                    // Cancel if the target touch has not moved.
                    if (!targetTouch) {
                        return false;
                    }
                    x = targetTouch.pageX;
                    y = targetTouch.pageY;
                }
            }
            pageOffset = pageOffset || getPageOffset(scope_Document);
            if (mouse || pointer) {
                x = e.clientX + pageOffset.x;
                y = e.clientY + pageOffset.y;
            }
            e.pageOffset = pageOffset;
            e.points = [x, y];
            e.cursor = mouse || pointer; // Fix #435
            return e;
        }
        // Translate a coordinate in the document to a percentage on the slider
        function calcPointToPercentage(calcPoint) {
            var location = calcPoint - offset(scope_Base, options.ort);
            var proposal = (location * 100) / baseSize();
            // Clamp proposal between 0% and 100%
            // Out-of-bound coordinates may occur when .noUi-base pseudo-elements
            // are used (e.g. contained handles feature)
            proposal = limit(proposal);
            return options.dir ? 100 - proposal : proposal;
        }
        // Find handle closest to a certain percentage on the slider
        function getClosestHandle(clickedPosition) {
            var smallestDifference = 100;
            var handleNumber = false;
            scope_Handles.forEach(function (handle, index) {
                // Disabled handles are ignored
                if (isHandleDisabled(index)) {
                    return;
                }
                var handlePosition = scope_Locations[index];
                var differenceWithThisHandle = Math.abs(handlePosition - clickedPosition);
                // Initial state
                var clickAtEdge = differenceWithThisHandle === 100 && smallestDifference === 100;
                // Difference with this handle is smaller than the previously checked handle
                var isCloser = differenceWithThisHandle < smallestDifference;
                var isCloserAfter = differenceWithThisHandle <= smallestDifference && clickedPosition > handlePosition;
                if (isCloser || isCloserAfter || clickAtEdge) {
                    handleNumber = index;
                    smallestDifference = differenceWithThisHandle;
                }
            });
            return handleNumber;
        }
        // Fire 'end' when a mouse or pen leaves the document.
        function documentLeave(event, data) {
            if (event.type === "mouseout" &&
                event.target.nodeName === "HTML" &&
                event.relatedTarget === null) {
                eventEnd(event, data);
            }
        }
        // Handle movement on document for handle and range drag.
        function eventMove(event, data) {
            // Fix #498
            // Check value of .buttons in 'start' to work around a bug in IE10 mobile (data.buttonsProperty).
            // https://connect.microsoft.com/IE/feedback/details/927005/mobile-ie10-windows-phone-buttons-property-of-pointermove-event-always-zero
            // IE9 has .buttons and .which zero on mousemove.
            // Firefox breaks the spec MDN defines.
            if (navigator.appVersion.indexOf("MSIE 9") === -1 && event.buttons === 0 && data.buttonsProperty !== 0) {
                return eventEnd(event, data);
            }
            // Check if we are moving up or down
            var movement = (options.dir ? -1 : 1) * (event.calcPoint - data.startCalcPoint);
            // Convert the movement into a percentage of the slider width/height
            var proposal = (movement * 100) / data.baseSize;
            moveHandles(movement > 0, proposal, data.locations, data.handleNumbers, data.connect);
        }
        // Unbind move events on document, call callbacks.
        function eventEnd(event, data) {
            // The handle is no longer active, so remove the class.
            if (data.handle) {
                removeClass(data.handle, options.cssClasses.active);
                scope_ActiveHandlesCount -= 1;
            }
            // Unbind the move and end events, which are added on 'start'.
            data.listeners.forEach(function (c) {
                scope_DocumentElement.removeEventListener(c[0], c[1]);
            });
            if (scope_ActiveHandlesCount === 0) {
                // Remove dragging class.
                removeClass(scope_Target, options.cssClasses.drag);
                setZindex();
                // Remove cursor styles and text-selection events bound to the body.
                if (event.cursor) {
                    scope_Body.style.cursor = "";
                    scope_Body.removeEventListener("selectstart", preventDefault);
                }
            }
            data.handleNumbers.forEach(function (handleNumber) {
                fireEvent("change", handleNumber);
                fireEvent("set", handleNumber);
                fireEvent("end", handleNumber);
            });
        }
        // Bind move events on document.
        function eventStart(event, data) {
            // Ignore event if any handle is disabled
            if (data.handleNumbers.some(isHandleDisabled)) {
                return;
            }
            var handle;
            if (data.handleNumbers.length === 1) {
                var handleOrigin = scope_Handles[data.handleNumbers[0]];
                handle = handleOrigin.children[0];
                scope_ActiveHandlesCount += 1;
                // Mark the handle as 'active' so it can be styled.
                addClass(handle, options.cssClasses.active);
            }
            // A drag should never propagate up to the 'tap' event.
            event.stopPropagation();
            // Record the event listeners.
            var listeners = [];
            // Attach the move and end events.
            var moveEvent = attachEvent(actions.move, scope_DocumentElement, eventMove, {
                // The event target has changed so we need to propagate the original one so that we keep
                // relying on it to extract target touches.
                target: event.target,
                handle: handle,
                connect: data.connect,
                listeners: listeners,
                startCalcPoint: event.calcPoint,
                baseSize: baseSize(),
                pageOffset: event.pageOffset,
                handleNumbers: data.handleNumbers,
                buttonsProperty: event.buttons,
                locations: scope_Locations.slice(),
            });
            var endEvent = attachEvent(actions.end, scope_DocumentElement, eventEnd, {
                target: event.target,
                handle: handle,
                listeners: listeners,
                doNotReject: true,
                handleNumbers: data.handleNumbers,
            });
            var outEvent = attachEvent("mouseout", scope_DocumentElement, documentLeave, {
                target: event.target,
                handle: handle,
                listeners: listeners,
                doNotReject: true,
                handleNumbers: data.handleNumbers,
            });
            // We want to make sure we pushed the listeners in the listener list rather than creating
            // a new one as it has already been passed to the event handlers.
            listeners.push.apply(listeners, moveEvent.concat(endEvent, outEvent));
            // Text selection isn't an issue on touch devices,
            // so adding cursor styles can be skipped.
            if (event.cursor) {
                // Prevent the 'I' cursor and extend the range-drag cursor.
                scope_Body.style.cursor = getComputedStyle(event.target).cursor;
                // Mark the target with a dragging state.
                if (scope_Handles.length > 1) {
                    addClass(scope_Target, options.cssClasses.drag);
                }
                // Prevent text selection when dragging the handles.
                // In noUiSlider <= 9.2.0, this was handled by calling preventDefault on mouse/touch start/move,
                // which is scroll blocking. The selectstart event is supported by FireFox starting from version 52,
                // meaning the only holdout is iOS Safari. This doesn't matter: text selection isn't triggered there.
                // The 'cursor' flag is false.
                // See: http://caniuse.com/#search=selectstart
                scope_Body.addEventListener("selectstart", preventDefault, false);
            }
            data.handleNumbers.forEach(function (handleNumber) {
                fireEvent("start", handleNumber);
            });
        }
        // Move closest handle to tapped location.
        function eventTap(event) {
            // The tap event shouldn't propagate up
            event.stopPropagation();
            var proposal = calcPointToPercentage(event.calcPoint);
            var handleNumber = getClosestHandle(proposal);
            // Tackle the case that all handles are 'disabled'.
            if (handleNumber === false) {
                return;
            }
            // Flag the slider as it is now in a transitional state.
            // Transition takes a configurable amount of ms (default 300). Re-enable the slider after that.
            if (!options.events.snap) {
                addClassFor(scope_Target, options.cssClasses.tap, options.animationDuration);
            }
            setHandle(handleNumber, proposal, true, true);
            setZindex();
            fireEvent("slide", handleNumber, true);
            fireEvent("update", handleNumber, true);
            if (!options.events.snap) {
                fireEvent("change", handleNumber, true);
                fireEvent("set", handleNumber, true);
            }
            else {
                eventStart(event, { handleNumbers: [handleNumber] });
            }
        }
        // Fires a 'hover' event for a hovered mouse/pen position.
        function eventHover(event) {
            var proposal = calcPointToPercentage(event.calcPoint);
            var to = scope_Spectrum.getStep(proposal);
            var value = scope_Spectrum.fromStepping(to);
            Object.keys(scope_Events).forEach(function (targetEvent) {
                if ("hover" === targetEvent.split(".")[0]) {
                    scope_Events[targetEvent].forEach(function (callback) {
                        callback.call(scope_Self, value);
                    });
                }
            });
        }
        // Handles keydown on focused handles
        // Don't move the document when pressing arrow keys on focused handles
        function eventKeydown(event, handleNumber) {
            if (isSliderDisabled() || isHandleDisabled(handleNumber)) {
                return false;
            }
            var horizontalKeys = ["Left", "Right"];
            var verticalKeys = ["Down", "Up"];
            var largeStepKeys = ["PageDown", "PageUp"];
            var edgeKeys = ["Home", "End"];
            if (options.dir && !options.ort) {
                // On an right-to-left slider, the left and right keys act inverted
                horizontalKeys.reverse();
            }
            else if (options.ort && !options.dir) {
                // On a top-to-bottom slider, the up and down keys act inverted
                verticalKeys.reverse();
                largeStepKeys.reverse();
            }
            // Strip "Arrow" for IE compatibility. https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/key
            var key = event.key.replace("Arrow", "");
            var isLargeDown = key === largeStepKeys[0];
            var isLargeUp = key === largeStepKeys[1];
            var isDown = key === verticalKeys[0] || key === horizontalKeys[0] || isLargeDown;
            var isUp = key === verticalKeys[1] || key === horizontalKeys[1] || isLargeUp;
            var isMin = key === edgeKeys[0];
            var isMax = key === edgeKeys[1];
            if (!isDown && !isUp && !isMin && !isMax) {
                return true;
            }
            event.preventDefault();
            var to;
            if (isUp || isDown) {
                var direction = isDown ? 0 : 1;
                var steps = getNextStepsForHandle(handleNumber);
                var step = steps[direction];
                // At the edge of a slider, do nothing
                if (step === null) {
                    return false;
                }
                // No step set, use the default of 10% of the sub-range
                if (step === false) {
                    step = scope_Spectrum.getDefaultStep(scope_Locations[handleNumber], isDown, options.keyboardDefaultStep);
                }
                if (isLargeUp || isLargeDown) {
                    step *= options.keyboardPageMultiplier;
                }
                else {
                    step *= options.keyboardMultiplier;
                }
                // Step over zero-length ranges (#948);
                step = Math.max(step, 0.0000001);
                // Decrement for down steps
                step = (isDown ? -1 : 1) * step;
                to = scope_Values[handleNumber] + step;
            }
            else if (isMax) {
                // End key
                to = options.spectrum.xVal[options.spectrum.xVal.length - 1];
            }
            else {
                // Home key
                to = options.spectrum.xVal[0];
            }
            setHandle(handleNumber, scope_Spectrum.toStepping(to), true, true);
            fireEvent("slide", handleNumber);
            fireEvent("update", handleNumber);
            fireEvent("change", handleNumber);
            fireEvent("set", handleNumber);
            return false;
        }
        // Attach events to several slider parts.
        function bindSliderEvents(behaviour) {
            // Attach the standard drag event to the handles.
            if (!behaviour.fixed) {
                scope_Handles.forEach(function (handle, index) {
                    // These events are only bound to the visual handle
                    // element, not the 'real' origin element.
                    attachEvent(actions.start, handle.children[0], eventStart, {
                        handleNumbers: [index],
                    });
                });
            }
            // Attach the tap event to the slider base.
            if (behaviour.tap) {
                attachEvent(actions.start, scope_Base, eventTap, {});
            }
            // Fire hover events
            if (behaviour.hover) {
                attachEvent(actions.move, scope_Base, eventHover, {
                    hover: true,
                });
            }
            // Make the range draggable.
            if (behaviour.drag) {
                scope_Connects.forEach(function (connect, index) {
                    if (connect === false || index === 0 || index === scope_Connects.length - 1) {
                        return;
                    }
                    var handleBefore = scope_Handles[index - 1];
                    var handleAfter = scope_Handles[index];
                    var eventHolders = [connect];
                    var handlesToDrag = [handleBefore, handleAfter];
                    var handleNumbersToDrag = [index - 1, index];
                    addClass(connect, options.cssClasses.draggable);
                    // When the range is fixed, the entire range can
                    // be dragged by the handles. The handle in the first
                    // origin will propagate the start event upward,
                    // but it needs to be bound manually on the other.
                    if (behaviour.fixed) {
                        eventHolders.push(handleBefore.children[0]);
                        eventHolders.push(handleAfter.children[0]);
                    }
                    if (behaviour.dragAll) {
                        handlesToDrag = scope_Handles;
                        handleNumbersToDrag = scope_HandleNumbers;
                    }
                    eventHolders.forEach(function (eventHolder) {
                        attachEvent(actions.start, eventHolder, eventStart, {
                            handles: handlesToDrag,
                            handleNumbers: handleNumbersToDrag,
                            connect: connect,
                        });
                    });
                });
            }
        }
        // Attach an event to this slider, possibly including a namespace
        function bindEvent(namespacedEvent, callback) {
            scope_Events[namespacedEvent] = scope_Events[namespacedEvent] || [];
            scope_Events[namespacedEvent].push(callback);
            // If the event bound is 'update,' fire it immediately for all handles.
            if (namespacedEvent.split(".")[0] === "update") {
                scope_Handles.forEach(function (a, index) {
                    fireEvent("update", index);
                });
            }
        }
        function isInternalNamespace(namespace) {
            return namespace === INTERNAL_EVENT_NS.aria || namespace === INTERNAL_EVENT_NS.tooltips;
        }
        // Undo attachment of event
        function removeEvent(namespacedEvent) {
            var event = namespacedEvent && namespacedEvent.split(".")[0];
            var namespace = event ? namespacedEvent.substring(event.length) : namespacedEvent;
            Object.keys(scope_Events).forEach(function (bind) {
                var tEvent = bind.split(".")[0];
                var tNamespace = bind.substring(tEvent.length);
                if ((!event || event === tEvent) && (!namespace || namespace === tNamespace)) {
                    // only delete protected internal event if intentional
                    if (!isInternalNamespace(tNamespace) || namespace === tNamespace) {
                        delete scope_Events[bind];
                    }
                }
            });
        }
        // External event handling
        function fireEvent(eventName, handleNumber, tap) {
            Object.keys(scope_Events).forEach(function (targetEvent) {
                var eventType = targetEvent.split(".")[0];
                if (eventName === eventType) {
                    scope_Events[targetEvent].forEach(function (callback) {
                        callback.call(
                        // Use the slider public API as the scope ('this')
                        scope_Self,
                        // Return values as array, so arg_1[arg_2] is always valid.
                        scope_Values.map(options.format.to),
                        // Handle index, 0 or 1
                        handleNumber,
                        // Un-formatted slider values
                        scope_Values.slice(),
                        // Event is fired by tap, true or false
                        tap || false,
                        // Left offset of the handle, in relation to the slider
                        scope_Locations.slice(),
                        // add the slider public API to an accessible parameter when this is unavailable
                        scope_Self);
                    });
                }
            });
        }
        // Split out the handle positioning logic so the Move event can use it, too
        function checkHandlePosition(reference, handleNumber, to, lookBackward, lookForward, getValue) {
            var distance;
            // For sliders with multiple handles, limit movement to the other handle.
            // Apply the margin option by adding it to the handle positions.
            if (scope_Handles.length > 1 && !options.events.unconstrained) {
                if (lookBackward && handleNumber > 0) {
                    distance = scope_Spectrum.getAbsoluteDistance(reference[handleNumber - 1], options.margin, false);
                    to = Math.max(to, distance);
                }
                if (lookForward && handleNumber < scope_Handles.length - 1) {
                    distance = scope_Spectrum.getAbsoluteDistance(reference[handleNumber + 1], options.margin, true);
                    to = Math.min(to, distance);
                }
            }
            // The limit option has the opposite effect, limiting handles to a
            // maximum distance from another. Limit must be > 0, as otherwise
            // handles would be unmovable.
            if (scope_Handles.length > 1 && options.limit) {
                if (lookBackward && handleNumber > 0) {
                    distance = scope_Spectrum.getAbsoluteDistance(reference[handleNumber - 1], options.limit, false);
                    to = Math.min(to, distance);
                }
                if (lookForward && handleNumber < scope_Handles.length - 1) {
                    distance = scope_Spectrum.getAbsoluteDistance(reference[handleNumber + 1], options.limit, true);
                    to = Math.max(to, distance);
                }
            }
            // The padding option keeps the handles a certain distance from the
            // edges of the slider. Padding must be > 0.
            if (options.padding) {
                if (handleNumber === 0) {
                    distance = scope_Spectrum.getAbsoluteDistance(0, options.padding[0], false);
                    to = Math.max(to, distance);
                }
                if (handleNumber === scope_Handles.length - 1) {
                    distance = scope_Spectrum.getAbsoluteDistance(100, options.padding[1], true);
                    to = Math.min(to, distance);
                }
            }
            to = scope_Spectrum.getStep(to);
            // Limit percentage to the 0 - 100 range
            to = limit(to);
            // Return false if handle can't move
            if (to === reference[handleNumber] && !getValue) {
                return false;
            }
            return to;
        }
        // Uses slider orientation to create CSS rules. a = base value;
        function inRuleOrder(v, a) {
            var o = options.ort;
            return (o ? a : v) + ", " + (o ? v : a);
        }
        // Moves handle(s) by a percentage
        // (bool, % to move, [% where handle started, ...], [index in scope_Handles, ...])
        function moveHandles(upward, proposal, locations, handleNumbers, connect) {
            var proposals = locations.slice();
            // Store first handle now, so we still have it in case handleNumbers is reversed
            var firstHandle = handleNumbers[0];
            var b = [!upward, upward];
            var f = [upward, !upward];
            // Copy handleNumbers so we don't change the dataset
            handleNumbers = handleNumbers.slice();
            // Check to see which handle is 'leading'.
            // If that one can't move the second can't either.
            if (upward) {
                handleNumbers.reverse();
            }
            // Step 1: get the maximum percentage that any of the handles can move
            if (handleNumbers.length > 1) {
                handleNumbers.forEach(function (handleNumber, o) {
                    var to = checkHandlePosition(proposals, handleNumber, proposals[handleNumber] + proposal, b[o], f[o], false);
                    // Stop if one of the handles can't move.
                    if (to === false) {
                        proposal = 0;
                    }
                    else {
                        proposal = to - proposals[handleNumber];
                        proposals[handleNumber] = to;
                    }
                });
            }
            // If using one handle, check backward AND forward
            else {
                b = f = [true];
            }
            var state = false;
            // Step 2: Try to set the handles with the found percentage
            handleNumbers.forEach(function (handleNumber, o) {
                state = setHandle(handleNumber, locations[handleNumber] + proposal, b[o], f[o]) || state;
            });
            // Step 3: If a handle moved, fire events
            if (state) {
                handleNumbers.forEach(function (handleNumber) {
                    fireEvent("update", handleNumber);
                    fireEvent("slide", handleNumber);
                });
                // If target is a connect, then fire drag event
                if (connect != undefined) {
                    fireEvent("drag", firstHandle);
                }
            }
        }
        // Takes a base value and an offset. This offset is used for the connect bar size.
        // In the initial design for this feature, the origin element was 1% wide.
        // Unfortunately, a rounding bug in Chrome makes it impossible to implement this feature
        // in this manner: https://bugs.chromium.org/p/chromium/issues/detail?id=798223
        function transformDirection(a, b) {
            return options.dir ? 100 - a - b : a;
        }
        // Updates scope_Locations and scope_Values, updates visual state
        function updateHandlePosition(handleNumber, to) {
            // Update locations.
            scope_Locations[handleNumber] = to;
            // Convert the value to the slider stepping/range.
            scope_Values[handleNumber] = scope_Spectrum.fromStepping(to);
            var translation = transformDirection(to, 0) - scope_DirOffset;
            var translateRule = "translate(" + inRuleOrder(translation + "%", "0") + ")";
            scope_Handles[handleNumber].style[options.transformRule] = translateRule;
            updateConnect(handleNumber);
            updateConnect(handleNumber + 1);
        }
        // Handles before the slider middle are stacked later = higher,
        // Handles after the middle later is lower
        // [[7] [8] .......... | .......... [5] [4]
        function setZindex() {
            scope_HandleNumbers.forEach(function (handleNumber) {
                var dir = scope_Locations[handleNumber] > 50 ? -1 : 1;
                var zIndex = 3 + (scope_Handles.length + dir * handleNumber);
                scope_Handles[handleNumber].style.zIndex = String(zIndex);
            });
        }
        // Test suggested values and apply margin, step.
        // if exactInput is true, don't run checkHandlePosition, then the handle can be placed in between steps (#436)
        function setHandle(handleNumber, to, lookBackward, lookForward, exactInput) {
            if (!exactInput) {
                to = checkHandlePosition(scope_Locations, handleNumber, to, lookBackward, lookForward, false);
            }
            if (to === false) {
                return false;
            }
            updateHandlePosition(handleNumber, to);
            return true;
        }
        // Updates style attribute for connect nodes
        function updateConnect(index) {
            // Skip connects set to false
            if (!scope_Connects[index]) {
                return;
            }
            var l = 0;
            var h = 100;
            if (index !== 0) {
                l = scope_Locations[index - 1];
            }
            if (index !== scope_Connects.length - 1) {
                h = scope_Locations[index];
            }
            // We use two rules:
            // 'translate' to change the left/top offset;
            // 'scale' to change the width of the element;
            // As the element has a width of 100%, a translation of 100% is equal to 100% of the parent (.noUi-base)
            var connectWidth = h - l;
            var translateRule = "translate(" + inRuleOrder(transformDirection(l, connectWidth) + "%", "0") + ")";
            var scaleRule = "scale(" + inRuleOrder(connectWidth / 100, "1") + ")";
            scope_Connects[index].style[options.transformRule] =
                translateRule + " " + scaleRule;
        }
        // Parses value passed to .set method. Returns current value if not parse-able.
        function resolveToValue(to, handleNumber) {
            // Setting with null indicates an 'ignore'.
            // Inputting 'false' is invalid.
            if (to === null || to === false || to === undefined) {
                return scope_Locations[handleNumber];
            }
            // If a formatted number was passed, attempt to decode it.
            if (typeof to === "number") {
                to = String(to);
            }
            to = options.format.from(to);
            if (to !== false) {
                to = scope_Spectrum.toStepping(to);
            }
            // If parsing the number failed, use the current value.
            if (to === false || isNaN(to)) {
                return scope_Locations[handleNumber];
            }
            return to;
        }
        // Set the slider value.
        function valueSet(input, fireSetEvent, exactInput) {
            var values = asArray(input);
            var isInit = scope_Locations[0] === undefined;
            // Event fires by default
            fireSetEvent = fireSetEvent === undefined ? true : fireSetEvent;
            // Animation is optional.
            // Make sure the initial values were set before using animated placement.
            if (options.animate && !isInit) {
                addClassFor(scope_Target, options.cssClasses.tap, options.animationDuration);
            }
            // First pass, without lookAhead but with lookBackward. Values are set from left to right.
            scope_HandleNumbers.forEach(function (handleNumber) {
                setHandle(handleNumber, resolveToValue(values[handleNumber], handleNumber), true, false, exactInput);
            });
            var i = scope_HandleNumbers.length === 1 ? 0 : 1;
            // Spread handles evenly across the slider if the range has no size (min=max)
            if (isInit && scope_Spectrum.hasNoSize()) {
                exactInput = true;
                scope_Locations[0] = 0;
                if (scope_HandleNumbers.length > 1) {
                    var space_1 = 100 / (scope_HandleNumbers.length - 1);
                    scope_HandleNumbers.forEach(function (handleNumber) {
                        scope_Locations[handleNumber] = handleNumber * space_1;
                    });
                }
            }
            // Secondary passes. Now that all base values are set, apply constraints.
            // Iterate all handles to ensure constraints are applied for the entire slider (Issue #1009)
            for (; i < scope_HandleNumbers.length; ++i) {
                scope_HandleNumbers.forEach(function (handleNumber) {
                    setHandle(handleNumber, scope_Locations[handleNumber], true, true, exactInput);
                });
            }
            setZindex();
            scope_HandleNumbers.forEach(function (handleNumber) {
                fireEvent("update", handleNumber);
                // Fire the event only for handles that received a new value, as per #579
                if (values[handleNumber] !== null && fireSetEvent) {
                    fireEvent("set", handleNumber);
                }
            });
        }
        // Reset slider to initial values
        function valueReset(fireSetEvent) {
            valueSet(options.start, fireSetEvent);
        }
        // Set value for a single handle
        function valueSetHandle(handleNumber, value, fireSetEvent, exactInput) {
            // Ensure numeric input
            handleNumber = Number(handleNumber);
            if (!(handleNumber >= 0 && handleNumber < scope_HandleNumbers.length)) {
                throw new Error("noUiSlider: invalid handle number, got: " + handleNumber);
            }
            // Look both backward and forward, since we don't want this handle to "push" other handles (#960);
            // The exactInput argument can be used to ignore slider stepping (#436)
            setHandle(handleNumber, resolveToValue(value, handleNumber), true, true, exactInput);
            fireEvent("update", handleNumber);
            if (fireSetEvent) {
                fireEvent("set", handleNumber);
            }
        }
        // Get the slider value.
        function valueGet(unencoded) {
            if (unencoded === void 0) { unencoded = false; }
            if (unencoded) {
                // return a copy of the raw values
                return scope_Values.length === 1 ? scope_Values[0] : scope_Values.slice(0);
            }
            var values = scope_Values.map(options.format.to);
            // If only one handle is used, return a single value.
            if (values.length === 1) {
                return values[0];
            }
            return values;
        }
        // Removes classes from the root and empties it.
        function destroy() {
            // remove protected internal listeners
            removeEvent(INTERNAL_EVENT_NS.aria);
            removeEvent(INTERNAL_EVENT_NS.tooltips);
            Object.keys(options.cssClasses).forEach(function (key) {
                removeClass(scope_Target, options.cssClasses[key]);
            });
            while (scope_Target.firstChild) {
                scope_Target.removeChild(scope_Target.firstChild);
            }
            delete scope_Target.noUiSlider;
        }
        function getNextStepsForHandle(handleNumber) {
            var location = scope_Locations[handleNumber];
            var nearbySteps = scope_Spectrum.getNearbySteps(location);
            var value = scope_Values[handleNumber];
            var increment = nearbySteps.thisStep.step;
            var decrement = null;
            // If snapped, directly use defined step value
            if (options.snap) {
                return [
                    value - nearbySteps.stepBefore.startValue || null,
                    nearbySteps.stepAfter.startValue - value || null,
                ];
            }
            // If the next value in this step moves into the next step,
            // the increment is the start of the next step - the current value
            if (increment !== false) {
                if (value + increment > nearbySteps.stepAfter.startValue) {
                    increment = nearbySteps.stepAfter.startValue - value;
                }
            }
            // If the value is beyond the starting point
            if (value > nearbySteps.thisStep.startValue) {
                decrement = nearbySteps.thisStep.step;
            }
            else if (nearbySteps.stepBefore.step === false) {
                decrement = false;
            }
            // If a handle is at the start of a step, it always steps back into the previous step first
            else {
                decrement = value - nearbySteps.stepBefore.highestStep;
            }
            // Now, if at the slider edges, there is no in/decrement
            if (location === 100) {
                increment = null;
            }
            else if (location === 0) {
                decrement = null;
            }
            // As per #391, the comparison for the decrement step can have some rounding issues.
            var stepDecimals = scope_Spectrum.countStepDecimals();
            // Round per #391
            if (increment !== null && increment !== false) {
                increment = Number(increment.toFixed(stepDecimals));
            }
            if (decrement !== null && decrement !== false) {
                decrement = Number(decrement.toFixed(stepDecimals));
            }
            return [decrement, increment];
        }
        // Get the current step size for the slider.
        function getNextSteps() {
            return scope_HandleNumbers.map(getNextStepsForHandle);
        }
        // Updatable: margin, limit, padding, step, range, animate, snap
        function updateOptions(optionsToUpdate, fireSetEvent) {
            // Spectrum is created using the range, snap, direction and step options.
            // 'snap' and 'step' can be updated.
            // If 'snap' and 'step' are not passed, they should remain unchanged.
            var v = valueGet();
            var updateAble = [
                "margin",
                "limit",
                "padding",
                "range",
                "animate",
                "snap",
                "step",
                "format",
                "pips",
                "tooltips",
            ];
            // Only change options that we're actually passed to update.
            updateAble.forEach(function (name) {
                // Check for undefined. null removes the value.
                if (optionsToUpdate[name] !== undefined) {
                    originalOptions[name] = optionsToUpdate[name];
                }
            });
            var newOptions = testOptions(originalOptions);
            // Load new options into the slider state
            updateAble.forEach(function (name) {
                if (optionsToUpdate[name] !== undefined) {
                    options[name] = newOptions[name];
                }
            });
            scope_Spectrum = newOptions.spectrum;
            // Limit, margin and padding depend on the spectrum but are stored outside of it. (#677)
            options.margin = newOptions.margin;
            options.limit = newOptions.limit;
            options.padding = newOptions.padding;
            // Update pips, removes existing.
            if (options.pips) {
                pips(options.pips);
            }
            else {
                removePips();
            }
            // Update tooltips, removes existing.
            if (options.tooltips) {
                tooltips();
            }
            else {
                removeTooltips();
            }
            // Invalidate the current positioning so valueSet forces an update.
            scope_Locations = [];
            valueSet(isSet(optionsToUpdate.start) ? optionsToUpdate.start : v, fireSetEvent);
        }
        // Initialization steps
        function setupSlider() {
            // Create the base element, initialize HTML and set classes.
            // Add handles and connect elements.
            scope_Base = addSlider(scope_Target);
            addElements(options.connect, scope_Base);
            // Attach user events.
            bindSliderEvents(options.events);
            // Use the public value method to set the start values.
            valueSet(options.start);
            if (options.pips) {
                pips(options.pips);
            }
            if (options.tooltips) {
                tooltips();
            }
            aria();
        }
        setupSlider();
        var scope_Self = {
            destroy: destroy,
            steps: getNextSteps,
            on: bindEvent,
            off: removeEvent,
            get: valueGet,
            set: valueSet,
            setHandle: valueSetHandle,
            reset: valueReset,
            // Exposed for unit testing, don't use this in your application.
            __moveHandles: function (upward, proposal, handleNumbers) {
                moveHandles(upward, proposal, scope_Locations, handleNumbers);
            },
            options: originalOptions,
            updateOptions: updateOptions,
            target: scope_Target,
            removePips: removePips,
            removeTooltips: removeTooltips,
            getPositions: function () {
                return scope_Locations.slice();
            },
            getTooltips: function () {
                return scope_Tooltips;
            },
            getOrigins: function () {
                return scope_Handles;
            },
            pips: pips, // Issue #594
        };
        return scope_Self;
    }
    // Run the standard initializer
    function initialize(target, originalOptions) {
        if (!target || !target.nodeName) {
            throw new Error("noUiSlider: create requires a single element, got: " + target);
        }
        // Throw an error if the slider was already initialized.
        if (target.noUiSlider) {
            throw new Error("noUiSlider: Slider was already initialized.");
        }
        // Test the options and create the slider environment;
        var options = testOptions(originalOptions);
        var api = scope(target, options, originalOptions);
        target.noUiSlider = api;
        return api;
    }
    var nouislider = {
        // Exposed for unit testing, don't use this in your application.
        __spectrum: Spectrum,
        // A reference to the default classes, allows global changes.
        // Use the cssClasses option for changes to one slider.
        cssClasses: cssClasses,
        create: initialize,
    };

    exports.create = initialize;
    exports.cssClasses = cssClasses;
    exports["default"] = nouislider;

    Object.defineProperty(exports, '__esModule', { value: true });

}));


/***/ }),
/* 142 */
/***/ (function(module, exports) {

var g;

// This works in non-strict mode
g = (function() {
    return this;
})();

try {
    // This works if eval is allowed (see CSP)
    g = g || new Function("return this")();
} catch (e) {
    // This works if the window reference is available
    if (typeof window === "object") g = window;
}

// g can still be undefined, but nothing to do about it...
// We return undefined, instead of nothing here, so it's
// easier to handle this case. if(!global) { ...}

module.exports = g;


/***/ }),
/* 143 */
/***/ (function(module, exports, __webpack_require__) {

var getBuiltIn = __webpack_require__(20);

module.exports = getBuiltIn('navigator', 'userAgent') || '';


/***/ }),
/* 144 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var call = __webpack_require__(7);
var isCallable = __webpack_require__(5);
var isObject = __webpack_require__(11);

var TypeError = global.TypeError;

// `OrdinaryToPrimitive` abstract operation
// https://tc39.es/ecma262/#sec-ordinarytoprimitive
module.exports = function (input, pref) {
  var fn, val;
  if (pref === 'string' && isCallable(fn = input.toString) && !isObject(val = call(fn, input))) return val;
  if (isCallable(fn = input.valueOf) && !isObject(val = call(fn, input))) return val;
  if (pref !== 'string' && isCallable(fn = input.toString) && !isObject(val = call(fn, input))) return val;
  throw TypeError("Can't convert object to primitive value");
};


/***/ }),
/* 145 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var isCallable = __webpack_require__(5);
var inspectSource = __webpack_require__(84);

var WeakMap = global.WeakMap;

module.exports = isCallable(WeakMap) && /native code/.test(inspectSource(WeakMap));


/***/ }),
/* 146 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var TO_STRING_TAG_SUPPORT = __webpack_require__(79);
var classof = __webpack_require__(61);

// `Object.prototype.toString` method implementation
// https://tc39.es/ecma262/#sec-object.prototype.tostring
module.exports = TO_STRING_TAG_SUPPORT ? {}.toString : function toString() {
  return '[object ' + classof(this) + ']';
};


/***/ }),
/* 147 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $forEach = __webpack_require__(62).forEach;
var arrayMethodIsStrict = __webpack_require__(106);

var STRICT_METHOD = arrayMethodIsStrict('forEach');

// `Array.prototype.forEach` method implementation
// https://tc39.es/ecma262/#sec-array.prototype.foreach
module.exports = !STRICT_METHOD ? function forEach(callbackfn /* , thisArg */) {
  return $forEach(this, callbackfn, arguments.length > 1 ? arguments[1] : undefined);
// eslint-disable-next-line es-x/no-array-prototype-foreach -- safe
} : [].forEach;


/***/ }),
/* 148 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var isArray = __webpack_require__(66);
var isConstructor = __webpack_require__(67);
var isObject = __webpack_require__(11);
var wellKnownSymbol = __webpack_require__(4);

var SPECIES = wellKnownSymbol('species');
var Array = global.Array;

// a part of `ArraySpeciesCreate` abstract operation
// https://tc39.es/ecma262/#sec-arrayspeciescreate
module.exports = function (originalArray) {
  var C;
  if (isArray(originalArray)) {
    C = originalArray.constructor;
    // cross-realm fallback
    if (isConstructor(C) && (C === Array || isArray(C.prototype))) C = undefined;
    else if (isObject(C)) {
      C = C[SPECIES];
      if (C === null) C = undefined;
    }
  } return C === undefined ? Array : C;
};


/***/ }),
/* 149 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var global = __webpack_require__(0);
var call = __webpack_require__(7);
var uncurryThis = __webpack_require__(1);
var IS_PURE = __webpack_require__(33);
var DESCRIPTORS = __webpack_require__(10);
var NATIVE_SYMBOL = __webpack_require__(40);
var fails = __webpack_require__(2);
var hasOwn = __webpack_require__(8);
var isPrototypeOf = __webpack_require__(35);
var anObject = __webpack_require__(6);
var toIndexedObject = __webpack_require__(18);
var toPropertyKey = __webpack_require__(58);
var $toString = __webpack_require__(13);
var createPropertyDescriptor = __webpack_require__(49);
var nativeObjectCreate = __webpack_require__(38);
var objectKeys = __webpack_require__(71);
var getOwnPropertyNamesModule = __webpack_require__(53);
var getOwnPropertyNamesExternal = __webpack_require__(112);
var getOwnPropertySymbolsModule = __webpack_require__(69);
var getOwnPropertyDescriptorModule = __webpack_require__(28);
var definePropertyModule = __webpack_require__(12);
var definePropertiesModule = __webpack_require__(111);
var propertyIsEnumerableModule = __webpack_require__(85);
var redefine = __webpack_require__(17);
var shared = __webpack_require__(39);
var sharedKey = __webpack_require__(60);
var hiddenKeys = __webpack_require__(50);
var uid = __webpack_require__(57);
var wellKnownSymbol = __webpack_require__(4);
var wrappedWellKnownSymbolModule = __webpack_require__(114);
var defineWellKnownSymbol = __webpack_require__(115);
var defineSymbolToPrimitive = __webpack_require__(152);
var setToStringTag = __webpack_require__(72);
var InternalStateModule = __webpack_require__(41);
var $forEach = __webpack_require__(62).forEach;

var HIDDEN = sharedKey('hidden');
var SYMBOL = 'Symbol';
var PROTOTYPE = 'prototype';

var setInternalState = InternalStateModule.set;
var getInternalState = InternalStateModule.getterFor(SYMBOL);

var ObjectPrototype = Object[PROTOTYPE];
var $Symbol = global.Symbol;
var SymbolPrototype = $Symbol && $Symbol[PROTOTYPE];
var TypeError = global.TypeError;
var QObject = global.QObject;
var nativeGetOwnPropertyDescriptor = getOwnPropertyDescriptorModule.f;
var nativeDefineProperty = definePropertyModule.f;
var nativeGetOwnPropertyNames = getOwnPropertyNamesExternal.f;
var nativePropertyIsEnumerable = propertyIsEnumerableModule.f;
var push = uncurryThis([].push);

var AllSymbols = shared('symbols');
var ObjectPrototypeSymbols = shared('op-symbols');
var WellKnownSymbolsStore = shared('wks');

// Don't use setters in Qt Script, https://github.com/zloirock/core-js/issues/173
var USE_SETTER = !QObject || !QObject[PROTOTYPE] || !QObject[PROTOTYPE].findChild;

// fallback for old Android, https://code.google.com/p/v8/issues/detail?id=687
var setSymbolDescriptor = DESCRIPTORS && fails(function () {
  return nativeObjectCreate(nativeDefineProperty({}, 'a', {
    get: function () { return nativeDefineProperty(this, 'a', { value: 7 }).a; }
  })).a != 7;
}) ? function (O, P, Attributes) {
  var ObjectPrototypeDescriptor = nativeGetOwnPropertyDescriptor(ObjectPrototype, P);
  if (ObjectPrototypeDescriptor) delete ObjectPrototype[P];
  nativeDefineProperty(O, P, Attributes);
  if (ObjectPrototypeDescriptor && O !== ObjectPrototype) {
    nativeDefineProperty(ObjectPrototype, P, ObjectPrototypeDescriptor);
  }
} : nativeDefineProperty;

var wrap = function (tag, description) {
  var symbol = AllSymbols[tag] = nativeObjectCreate(SymbolPrototype);
  setInternalState(symbol, {
    type: SYMBOL,
    tag: tag,
    description: description
  });
  if (!DESCRIPTORS) symbol.description = description;
  return symbol;
};

var $defineProperty = function defineProperty(O, P, Attributes) {
  if (O === ObjectPrototype) $defineProperty(ObjectPrototypeSymbols, P, Attributes);
  anObject(O);
  var key = toPropertyKey(P);
  anObject(Attributes);
  if (hasOwn(AllSymbols, key)) {
    if (!Attributes.enumerable) {
      if (!hasOwn(O, HIDDEN)) nativeDefineProperty(O, HIDDEN, createPropertyDescriptor(1, {}));
      O[HIDDEN][key] = true;
    } else {
      if (hasOwn(O, HIDDEN) && O[HIDDEN][key]) O[HIDDEN][key] = false;
      Attributes = nativeObjectCreate(Attributes, { enumerable: createPropertyDescriptor(0, false) });
    } return setSymbolDescriptor(O, key, Attributes);
  } return nativeDefineProperty(O, key, Attributes);
};

var $defineProperties = function defineProperties(O, Properties) {
  anObject(O);
  var properties = toIndexedObject(Properties);
  var keys = objectKeys(properties).concat($getOwnPropertySymbols(properties));
  $forEach(keys, function (key) {
    if (!DESCRIPTORS || call($propertyIsEnumerable, properties, key)) $defineProperty(O, key, properties[key]);
  });
  return O;
};

var $create = function create(O, Properties) {
  return Properties === undefined ? nativeObjectCreate(O) : $defineProperties(nativeObjectCreate(O), Properties);
};

var $propertyIsEnumerable = function propertyIsEnumerable(V) {
  var P = toPropertyKey(V);
  var enumerable = call(nativePropertyIsEnumerable, this, P);
  if (this === ObjectPrototype && hasOwn(AllSymbols, P) && !hasOwn(ObjectPrototypeSymbols, P)) return false;
  return enumerable || !hasOwn(this, P) || !hasOwn(AllSymbols, P) || hasOwn(this, HIDDEN) && this[HIDDEN][P]
    ? enumerable : true;
};

var $getOwnPropertyDescriptor = function getOwnPropertyDescriptor(O, P) {
  var it = toIndexedObject(O);
  var key = toPropertyKey(P);
  if (it === ObjectPrototype && hasOwn(AllSymbols, key) && !hasOwn(ObjectPrototypeSymbols, key)) return;
  var descriptor = nativeGetOwnPropertyDescriptor(it, key);
  if (descriptor && hasOwn(AllSymbols, key) && !(hasOwn(it, HIDDEN) && it[HIDDEN][key])) {
    descriptor.enumerable = true;
  }
  return descriptor;
};

var $getOwnPropertyNames = function getOwnPropertyNames(O) {
  var names = nativeGetOwnPropertyNames(toIndexedObject(O));
  var result = [];
  $forEach(names, function (key) {
    if (!hasOwn(AllSymbols, key) && !hasOwn(hiddenKeys, key)) push(result, key);
  });
  return result;
};

var $getOwnPropertySymbols = function (O) {
  var IS_OBJECT_PROTOTYPE = O === ObjectPrototype;
  var names = nativeGetOwnPropertyNames(IS_OBJECT_PROTOTYPE ? ObjectPrototypeSymbols : toIndexedObject(O));
  var result = [];
  $forEach(names, function (key) {
    if (hasOwn(AllSymbols, key) && (!IS_OBJECT_PROTOTYPE || hasOwn(ObjectPrototype, key))) {
      push(result, AllSymbols[key]);
    }
  });
  return result;
};

// `Symbol` constructor
// https://tc39.es/ecma262/#sec-symbol-constructor
if (!NATIVE_SYMBOL) {
  $Symbol = function Symbol() {
    if (isPrototypeOf(SymbolPrototype, this)) throw TypeError('Symbol is not a constructor');
    var description = !arguments.length || arguments[0] === undefined ? undefined : $toString(arguments[0]);
    var tag = uid(description);
    var setter = function (value) {
      if (this === ObjectPrototype) call(setter, ObjectPrototypeSymbols, value);
      if (hasOwn(this, HIDDEN) && hasOwn(this[HIDDEN], tag)) this[HIDDEN][tag] = false;
      setSymbolDescriptor(this, tag, createPropertyDescriptor(1, value));
    };
    if (DESCRIPTORS && USE_SETTER) setSymbolDescriptor(ObjectPrototype, tag, { configurable: true, set: setter });
    return wrap(tag, description);
  };

  SymbolPrototype = $Symbol[PROTOTYPE];

  redefine(SymbolPrototype, 'toString', function toString() {
    return getInternalState(this).tag;
  });

  redefine($Symbol, 'withoutSetter', function (description) {
    return wrap(uid(description), description);
  });

  propertyIsEnumerableModule.f = $propertyIsEnumerable;
  definePropertyModule.f = $defineProperty;
  definePropertiesModule.f = $defineProperties;
  getOwnPropertyDescriptorModule.f = $getOwnPropertyDescriptor;
  getOwnPropertyNamesModule.f = getOwnPropertyNamesExternal.f = $getOwnPropertyNames;
  getOwnPropertySymbolsModule.f = $getOwnPropertySymbols;

  wrappedWellKnownSymbolModule.f = function (name) {
    return wrap(wellKnownSymbol(name), name);
  };

  if (DESCRIPTORS) {
    // https://github.com/tc39/proposal-Symbol-description
    nativeDefineProperty(SymbolPrototype, 'description', {
      configurable: true,
      get: function description() {
        return getInternalState(this).description;
      }
    });
    if (!IS_PURE) {
      redefine(ObjectPrototype, 'propertyIsEnumerable', $propertyIsEnumerable, { unsafe: true });
    }
  }
}

$({ global: true, wrap: true, forced: !NATIVE_SYMBOL, sham: !NATIVE_SYMBOL }, {
  Symbol: $Symbol
});

$forEach(objectKeys(WellKnownSymbolsStore), function (name) {
  defineWellKnownSymbol(name);
});

$({ target: SYMBOL, stat: true, forced: !NATIVE_SYMBOL }, {
  useSetter: function () { USE_SETTER = true; },
  useSimple: function () { USE_SETTER = false; }
});

$({ target: 'Object', stat: true, forced: !NATIVE_SYMBOL, sham: !DESCRIPTORS }, {
  // `Object.create` method
  // https://tc39.es/ecma262/#sec-object.create
  create: $create,
  // `Object.defineProperty` method
  // https://tc39.es/ecma262/#sec-object.defineproperty
  defineProperty: $defineProperty,
  // `Object.defineProperties` method
  // https://tc39.es/ecma262/#sec-object.defineproperties
  defineProperties: $defineProperties,
  // `Object.getOwnPropertyDescriptor` method
  // https://tc39.es/ecma262/#sec-object.getownpropertydescriptors
  getOwnPropertyDescriptor: $getOwnPropertyDescriptor
});

$({ target: 'Object', stat: true, forced: !NATIVE_SYMBOL }, {
  // `Object.getOwnPropertyNames` method
  // https://tc39.es/ecma262/#sec-object.getownpropertynames
  getOwnPropertyNames: $getOwnPropertyNames
});

// `Symbol.prototype[@@toPrimitive]` method
// https://tc39.es/ecma262/#sec-symbol.prototype-@@toprimitive
defineSymbolToPrimitive();

// `Symbol.prototype[@@toStringTag]` property
// https://tc39.es/ecma262/#sec-symbol.prototype-@@tostringtag
setToStringTag($Symbol, SYMBOL);

hiddenKeys[HIDDEN] = true;


/***/ }),
/* 150 */
/***/ (function(module, exports, __webpack_require__) {

var getBuiltIn = __webpack_require__(20);

module.exports = getBuiltIn('document', 'documentElement');


/***/ }),
/* 151 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);

module.exports = global;


/***/ }),
/* 152 */
/***/ (function(module, exports, __webpack_require__) {

var call = __webpack_require__(7);
var getBuiltIn = __webpack_require__(20);
var wellKnownSymbol = __webpack_require__(4);
var redefine = __webpack_require__(17);

module.exports = function () {
  var Symbol = getBuiltIn('Symbol');
  var SymbolPrototype = Symbol && Symbol.prototype;
  var valueOf = SymbolPrototype && SymbolPrototype.valueOf;
  var TO_PRIMITIVE = wellKnownSymbol('toPrimitive');

  if (SymbolPrototype && !SymbolPrototype[TO_PRIMITIVE]) {
    // `Symbol.prototype[@@toPrimitive]` method
    // https://tc39.es/ecma262/#sec-symbol.prototype-@@toprimitive
    // eslint-disable-next-line no-unused-vars -- required for .length
    redefine(SymbolPrototype, TO_PRIMITIVE, function (hint) {
      return call(valueOf, this);
    });
  }
};


/***/ }),
/* 153 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var getBuiltIn = __webpack_require__(20);
var hasOwn = __webpack_require__(8);
var toString = __webpack_require__(13);
var shared = __webpack_require__(39);
var NATIVE_SYMBOL_REGISTRY = __webpack_require__(116);

var StringToSymbolRegistry = shared('string-to-symbol-registry');
var SymbolToStringRegistry = shared('symbol-to-string-registry');

// `Symbol.for` method
// https://tc39.es/ecma262/#sec-symbol.for
$({ target: 'Symbol', stat: true, forced: !NATIVE_SYMBOL_REGISTRY }, {
  'for': function (key) {
    var string = toString(key);
    if (hasOwn(StringToSymbolRegistry, string)) return StringToSymbolRegistry[string];
    var symbol = getBuiltIn('Symbol')(string);
    StringToSymbolRegistry[string] = symbol;
    SymbolToStringRegistry[symbol] = string;
    return symbol;
  }
});


/***/ }),
/* 154 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var hasOwn = __webpack_require__(8);
var isSymbol = __webpack_require__(47);
var tryToString = __webpack_require__(48);
var shared = __webpack_require__(39);
var NATIVE_SYMBOL_REGISTRY = __webpack_require__(116);

var SymbolToStringRegistry = shared('symbol-to-string-registry');

// `Symbol.keyFor` method
// https://tc39.es/ecma262/#sec-symbol.keyfor
$({ target: 'Symbol', stat: true, forced: !NATIVE_SYMBOL_REGISTRY }, {
  keyFor: function keyFor(sym) {
    if (!isSymbol(sym)) throw TypeError(tryToString(sym) + ' is not a symbol');
    if (hasOwn(SymbolToStringRegistry, sym)) return SymbolToStringRegistry[sym];
  }
});


/***/ }),
/* 155 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var getBuiltIn = __webpack_require__(20);
var apply = __webpack_require__(73);
var call = __webpack_require__(7);
var uncurryThis = __webpack_require__(1);
var fails = __webpack_require__(2);
var isArray = __webpack_require__(66);
var isCallable = __webpack_require__(5);
var isObject = __webpack_require__(11);
var isSymbol = __webpack_require__(47);
var arraySlice = __webpack_require__(88);
var NATIVE_SYMBOL = __webpack_require__(40);

var $stringify = getBuiltIn('JSON', 'stringify');
var exec = uncurryThis(/./.exec);
var charAt = uncurryThis(''.charAt);
var charCodeAt = uncurryThis(''.charCodeAt);
var replace = uncurryThis(''.replace);
var numberToString = uncurryThis(1.0.toString);

var tester = /[\uD800-\uDFFF]/g;
var low = /^[\uD800-\uDBFF]$/;
var hi = /^[\uDC00-\uDFFF]$/;

var WRONG_SYMBOLS_CONVERSION = !NATIVE_SYMBOL || fails(function () {
  var symbol = getBuiltIn('Symbol')();
  // MS Edge converts symbol values to JSON as {}
  return $stringify([symbol]) != '[null]'
    // WebKit converts symbol values to JSON as null
    || $stringify({ a: symbol }) != '{}'
    // V8 throws on boxed symbols
    || $stringify(Object(symbol)) != '{}';
});

// https://github.com/tc39/proposal-well-formed-stringify
var ILL_FORMED_UNICODE = fails(function () {
  return $stringify('\uDF06\uD834') !== '"\\udf06\\ud834"'
    || $stringify('\uDEAD') !== '"\\udead"';
});

var stringifyWithSymbolsFix = function (it, replacer) {
  var args = arraySlice(arguments);
  var $replacer = replacer;
  if (!isObject(replacer) && it === undefined || isSymbol(it)) return; // IE8 returns string on undefined
  if (!isArray(replacer)) replacer = function (key, value) {
    if (isCallable($replacer)) value = call($replacer, this, key, value);
    if (!isSymbol(value)) return value;
  };
  args[1] = replacer;
  return apply($stringify, null, args);
};

var fixIllFormed = function (match, offset, string) {
  var prev = charAt(string, offset - 1);
  var next = charAt(string, offset + 1);
  if ((exec(low, match) && !exec(hi, next)) || (exec(hi, match) && !exec(low, prev))) {
    return '\\u' + numberToString(charCodeAt(match, 0), 16);
  } return match;
};

if ($stringify) {
  // `JSON.stringify` method
  // https://tc39.es/ecma262/#sec-json.stringify
  $({ target: 'JSON', stat: true, forced: WRONG_SYMBOLS_CONVERSION || ILL_FORMED_UNICODE }, {
    // eslint-disable-next-line no-unused-vars -- required for `.length`
    stringify: function stringify(it, replacer, space) {
      var args = arraySlice(arguments);
      var result = apply(WRONG_SYMBOLS_CONVERSION ? stringifyWithSymbolsFix : $stringify, null, args);
      return ILL_FORMED_UNICODE && typeof result == 'string' ? replace(result, tester, fixIllFormed) : result;
    }
  });
}


/***/ }),
/* 156 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var NATIVE_SYMBOL = __webpack_require__(40);
var fails = __webpack_require__(2);
var getOwnPropertySymbolsModule = __webpack_require__(69);
var toObject = __webpack_require__(16);

// V8 ~ Chrome 38 and 39 `Object.getOwnPropertySymbols` fails on primitives
// https://bugs.chromium.org/p/v8/issues/detail?id=3443
var FORCED = !NATIVE_SYMBOL || fails(function () { getOwnPropertySymbolsModule.f(1); });

// `Object.getOwnPropertySymbols` method
// https://tc39.es/ecma262/#sec-object.getownpropertysymbols
$({ target: 'Object', stat: true, forced: FORCED }, {
  getOwnPropertySymbols: function getOwnPropertySymbols(it) {
    var $getOwnPropertySymbols = getOwnPropertySymbolsModule.f;
    return $getOwnPropertySymbols ? $getOwnPropertySymbols(toObject(it)) : [];
  }
});


/***/ }),
/* 157 */
/***/ (function(module, exports) {

if (typeof Element !== "undefined") {
    if (!Element.prototype.matches) {
        Element.prototype.matches = Element.prototype.msMatchesSelector || Element.prototype.webkitMatchesSelector;
    }

    if (!Element.prototype.closest) {
        Element.prototype.closest = function (s) {
            var el = this;

            do {
                if (el.matches(s)) return el;
                el = el.parentElement || el.parentNode;
            } while (el !== null && el.nodeType === 1);

            return null;
        };
    }
}


/***/ }),
/* 158 */
/***/ (function(module, exports, __webpack_require__) {

var fails = __webpack_require__(2);
var global = __webpack_require__(0);

// babel-minify and Closure Compiler transpiles RegExp('.', 's') -> /./s and it causes SyntaxError
var $RegExp = global.RegExp;

module.exports = fails(function () {
  var re = $RegExp('.', 's');
  return !(re.dotAll && re.exec('\n') && re.flags === 's');
});


/***/ }),
/* 159 */
/***/ (function(module, exports, __webpack_require__) {

var fails = __webpack_require__(2);
var global = __webpack_require__(0);

// babel-minify and Closure Compiler transpiles RegExp('(?<a>b)', 'g') -> /(?<a>b)/g and it causes SyntaxError
var $RegExp = global.RegExp;

module.exports = fails(function () {
  var re = $RegExp('(?<a>b)', 'g');
  return re.exec('b').groups.a !== 'b' ||
    'b'.replace(re, '$<a>c') !== 'bc';
});


/***/ }),
/* 160 */
/***/ (function(module, exports, __webpack_require__) {

var anObject = __webpack_require__(6);
var aConstructor = __webpack_require__(118);
var wellKnownSymbol = __webpack_require__(4);

var SPECIES = wellKnownSymbol('species');

// `SpeciesConstructor` abstract operation
// https://tc39.es/ecma262/#sec-speciesconstructor
module.exports = function (O, defaultConstructor) {
  var C = anObject(O).constructor;
  var S;
  return C === undefined || (S = anObject(C)[SPECIES]) == undefined ? defaultConstructor : aConstructor(S);
};


/***/ }),
/* 161 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var IteratorPrototype = __webpack_require__(120).IteratorPrototype;
var create = __webpack_require__(38);
var createPropertyDescriptor = __webpack_require__(49);
var setToStringTag = __webpack_require__(72);
var Iterators = __webpack_require__(56);

var returnThis = function () { return this; };

module.exports = function (IteratorConstructor, NAME, next, ENUMERABLE_NEXT) {
  var TO_STRING_TAG = NAME + ' Iterator';
  IteratorConstructor.prototype = create(IteratorPrototype, { next: createPropertyDescriptor(+!ENUMERABLE_NEXT, next) });
  setToStringTag(IteratorConstructor, TO_STRING_TAG, false, true);
  Iterators[TO_STRING_TAG] = returnThis;
  return IteratorConstructor;
};


/***/ }),
/* 162 */
/***/ (function(module, exports, __webpack_require__) {

var global = __webpack_require__(0);
var isCallable = __webpack_require__(5);

var String = global.String;
var TypeError = global.TypeError;

module.exports = function (argument) {
  if (typeof argument == 'object' || isCallable(argument)) return argument;
  throw TypeError("Can't set " + String(argument) + ' as a prototype');
};


/***/ }),
/* 163 */
/***/ (function(module, exports, __webpack_require__) {

// TODO: Remove this module from `core-js@4` since it's replaced to module below
__webpack_require__(164);


/***/ }),
/* 164 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var collection = __webpack_require__(123);
var collectionStrong = __webpack_require__(132);

// `Set` constructor
// https://tc39.es/ecma262/#sec-set-objects
collection('Set', function (init) {
  return function Set() { return init(this, arguments.length ? arguments[0] : undefined); };
}, collectionStrong);


/***/ }),
/* 165 */
/***/ (function(module, exports, __webpack_require__) {

var fails = __webpack_require__(2);
var isObject = __webpack_require__(11);
var classof = __webpack_require__(37);
var ARRAY_BUFFER_NON_EXTENSIBLE = __webpack_require__(166);

// eslint-disable-next-line es-x/no-object-isextensible -- safe
var $isExtensible = Object.isExtensible;
var FAILS_ON_PRIMITIVES = fails(function () { $isExtensible(1); });

// `Object.isExtensible` method
// https://tc39.es/ecma262/#sec-object.isextensible
module.exports = (FAILS_ON_PRIMITIVES || ARRAY_BUFFER_NON_EXTENSIBLE) ? function isExtensible(it) {
  if (!isObject(it)) return false;
  if (ARRAY_BUFFER_NON_EXTENSIBLE && classof(it) == 'ArrayBuffer') return false;
  return $isExtensible ? $isExtensible(it) : true;
} : $isExtensible;


/***/ }),
/* 166 */
/***/ (function(module, exports, __webpack_require__) {

// FF26- bug: ArrayBuffers are non-extensible, but Object.isExtensible does not report it
var fails = __webpack_require__(2);

module.exports = fails(function () {
  if (typeof ArrayBuffer == 'function') {
    var buffer = new ArrayBuffer(8);
    // eslint-disable-next-line es-x/no-object-isextensible, es-x/no-object-defineproperty -- safe
    if (Object.isExtensible(buffer)) Object.defineProperty(buffer, 'a', { value: 8 });
  }
});


/***/ }),
/* 167 */
/***/ (function(module, exports, __webpack_require__) {

var fails = __webpack_require__(2);

module.exports = !fails(function () {
  // eslint-disable-next-line es-x/no-object-isextensible, es-x/no-object-preventextensions -- required for testing
  return Object.isExtensible(Object.preventExtensions({}));
});


/***/ }),
/* 168 */
/***/ (function(module, exports, __webpack_require__) {

var redefine = __webpack_require__(17);

module.exports = function (target, src, options) {
  for (var key in src) redefine(target, key, src[key], options);
  return target;
};


/***/ }),
/* 169 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var getBuiltIn = __webpack_require__(20);
var definePropertyModule = __webpack_require__(12);
var wellKnownSymbol = __webpack_require__(4);
var DESCRIPTORS = __webpack_require__(10);

var SPECIES = wellKnownSymbol('species');

module.exports = function (CONSTRUCTOR_NAME) {
  var Constructor = getBuiltIn(CONSTRUCTOR_NAME);
  var defineProperty = definePropertyModule.f;

  if (DESCRIPTORS && Constructor && !Constructor[SPECIES]) {
    defineProperty(Constructor, SPECIES, {
      configurable: true,
      get: function () { return this; }
    });
  }
};


/***/ }),
/* 170 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var $includes = __webpack_require__(110).includes;
var addToUnscopables = __webpack_require__(94);

// `Array.prototype.includes` method
// https://tc39.es/ecma262/#sec-array.prototype.includes
$({ target: 'Array', proto: true }, {
  includes: function includes(el /* , fromIndex = 0 */) {
    return $includes(this, el, arguments.length > 1 ? arguments[1] : undefined);
  }
});

// https://tc39.es/ecma262/#sec-array.prototype-@@unscopables
addToUnscopables('includes');


/***/ }),
/* 171 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var uncurryThis = __webpack_require__(1);
var notARegExp = __webpack_require__(134);
var requireObjectCoercible = __webpack_require__(19);
var toString = __webpack_require__(13);
var correctIsRegExpLogic = __webpack_require__(135);

var stringIndexOf = uncurryThis(''.indexOf);

// `String.prototype.includes` method
// https://tc39.es/ecma262/#sec-string.prototype.includes
$({ target: 'String', proto: true, forced: !correctIsRegExpLogic('includes') }, {
  includes: function includes(searchString /* , position = 0 */) {
    return !!~stringIndexOf(
      toString(requireObjectCoercible(this)),
      toString(notARegExp(searchString)),
      arguments.length > 1 ? arguments[1] : undefined
    );
  }
});


/***/ }),
/* 172 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var uncurryThis = __webpack_require__(1);
var getOwnPropertyDescriptor = __webpack_require__(28).f;
var toLength = __webpack_require__(52);
var toString = __webpack_require__(13);
var notARegExp = __webpack_require__(134);
var requireObjectCoercible = __webpack_require__(19);
var correctIsRegExpLogic = __webpack_require__(135);
var IS_PURE = __webpack_require__(33);

// eslint-disable-next-line es-x/no-string-prototype-startswith -- safe
var un$StartsWith = uncurryThis(''.startsWith);
var stringSlice = uncurryThis(''.slice);
var min = Math.min;

var CORRECT_IS_REGEXP_LOGIC = correctIsRegExpLogic('startsWith');
// https://github.com/zloirock/core-js/pull/702
var MDN_POLYFILL_BUG = !IS_PURE && !CORRECT_IS_REGEXP_LOGIC && !!function () {
  var descriptor = getOwnPropertyDescriptor(String.prototype, 'startsWith');
  return descriptor && !descriptor.writable;
}();

// `String.prototype.startsWith` method
// https://tc39.es/ecma262/#sec-string.prototype.startswith
$({ target: 'String', proto: true, forced: !MDN_POLYFILL_BUG && !CORRECT_IS_REGEXP_LOGIC }, {
  startsWith: function startsWith(searchString /* , position = 0 */) {
    var that = toString(requireObjectCoercible(this));
    notARegExp(searchString);
    var index = toLength(min(arguments.length > 1 ? arguments[1] : undefined, that.length));
    var search = toString(searchString);
    return un$StartsWith
      ? un$StartsWith(that, search, index)
      : stringSlice(that, index, index + search.length) === search;
  }
});


/***/ }),
/* 173 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var global = __webpack_require__(0);
var bind = __webpack_require__(63);
var call = __webpack_require__(7);
var toObject = __webpack_require__(16);
var callWithSafeIterationClosing = __webpack_require__(174);
var isArrayIteratorMethod = __webpack_require__(126);
var isConstructor = __webpack_require__(67);
var lengthOfArrayLike = __webpack_require__(27);
var createProperty = __webpack_require__(54);
var getIterator = __webpack_require__(127);
var getIteratorMethod = __webpack_require__(96);

var Array = global.Array;

// `Array.from` method implementation
// https://tc39.es/ecma262/#sec-array.from
module.exports = function from(arrayLike /* , mapfn = undefined, thisArg = undefined */) {
  var O = toObject(arrayLike);
  var IS_CONSTRUCTOR = isConstructor(this);
  var argumentsLength = arguments.length;
  var mapfn = argumentsLength > 1 ? arguments[1] : undefined;
  var mapping = mapfn !== undefined;
  if (mapping) mapfn = bind(mapfn, argumentsLength > 2 ? arguments[2] : undefined);
  var iteratorMethod = getIteratorMethod(O);
  var index = 0;
  var length, result, step, iterator, next, value;
  // if the target is not iterable or it's an array with the default iterator - use a simple case
  if (iteratorMethod && !(this == Array && isArrayIteratorMethod(iteratorMethod))) {
    iterator = getIterator(O, iteratorMethod);
    next = iterator.next;
    result = IS_CONSTRUCTOR ? new this() : [];
    for (;!(step = call(next, iterator)).done; index++) {
      value = mapping ? callWithSafeIterationClosing(iterator, mapfn, [step.value, index], true) : step.value;
      createProperty(result, index, value);
    }
  } else {
    length = lengthOfArrayLike(O);
    result = IS_CONSTRUCTOR ? new this(length) : Array(length);
    for (;length > index; index++) {
      value = mapping ? mapfn(O[index], index) : O[index];
      createProperty(result, index, value);
    }
  }
  result.length = index;
  return result;
};


/***/ }),
/* 174 */
/***/ (function(module, exports, __webpack_require__) {

var anObject = __webpack_require__(6);
var iteratorClose = __webpack_require__(128);

// call something on iterator step with safe closing on error
module.exports = function (iterator, fn, value, ENTRIES) {
  try {
    return ENTRIES ? fn(anObject(value)[0], value[1]) : fn(value);
  } catch (error) {
    iteratorClose(iterator, 'throw', error);
  }
};


/***/ }),
/* 175 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var $trim = __webpack_require__(136).trim;
var forcedStringTrimMethod = __webpack_require__(176);

// `String.prototype.trim` method
// https://tc39.es/ecma262/#sec-string.prototype.trim
$({ target: 'String', proto: true, forced: forcedStringTrimMethod('trim') }, {
  trim: function trim() {
    return $trim(this);
  }
});


/***/ }),
/* 176 */
/***/ (function(module, exports, __webpack_require__) {

var PROPER_FUNCTION_NAME = __webpack_require__(51).PROPER;
var fails = __webpack_require__(2);
var whitespaces = __webpack_require__(137);

var non = '\u200B\u0085\u180E';

// check that a method works with the correct list
// of whitespaces and has a correct name
module.exports = function (METHOD_NAME) {
  return fails(function () {
    return !!whitespaces[METHOD_NAME]()
      || non[METHOD_NAME]() !== non
      || (PROPER_FUNCTION_NAME && whitespaces[METHOD_NAME].name !== METHOD_NAME);
  });
};


/***/ }),
/* 177 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var uncurryThis = __webpack_require__(1);
var IndexedObject = __webpack_require__(64);
var toIndexedObject = __webpack_require__(18);
var arrayMethodIsStrict = __webpack_require__(106);

var un$Join = uncurryThis([].join);

var ES3_STRINGS = IndexedObject != Object;
var STRICT_METHOD = arrayMethodIsStrict('join', ',');

// `Array.prototype.join` method
// https://tc39.es/ecma262/#sec-array.prototype.join
$({ target: 'Array', proto: true, forced: ES3_STRINGS || !STRICT_METHOD }, {
  join: function join(separator) {
    return un$Join(toIndexedObject(this), separator === undefined ? ',' : separator);
  }
});


/***/ }),
/* 178 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var $map = __webpack_require__(62).map;
var arrayMethodHasSpeciesSupport = __webpack_require__(70);

var HAS_SPECIES_SUPPORT = arrayMethodHasSpeciesSupport('map');

// `Array.prototype.map` method
// https://tc39.es/ecma262/#sec-array.prototype.map
// with adding support of @@species
$({ target: 'Array', proto: true, forced: !HAS_SPECIES_SUPPORT }, {
  map: function map(callbackfn /* , thisArg */) {
    return $map(this, callbackfn, arguments.length > 1 ? arguments[1] : undefined);
  }
});


/***/ }),
/* 179 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var assign = __webpack_require__(180);

// `Object.assign` method
// https://tc39.es/ecma262/#sec-object.assign
// eslint-disable-next-line es-x/no-object-assign -- required for testing
$({ target: 'Object', stat: true, forced: Object.assign !== assign }, {
  assign: assign
});


/***/ }),
/* 180 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var DESCRIPTORS = __webpack_require__(10);
var uncurryThis = __webpack_require__(1);
var call = __webpack_require__(7);
var fails = __webpack_require__(2);
var objectKeys = __webpack_require__(71);
var getOwnPropertySymbolsModule = __webpack_require__(69);
var propertyIsEnumerableModule = __webpack_require__(85);
var toObject = __webpack_require__(16);
var IndexedObject = __webpack_require__(64);

// eslint-disable-next-line es-x/no-object-assign -- safe
var $assign = Object.assign;
// eslint-disable-next-line es-x/no-object-defineproperty -- required for testing
var defineProperty = Object.defineProperty;
var concat = uncurryThis([].concat);

// `Object.assign` method
// https://tc39.es/ecma262/#sec-object.assign
module.exports = !$assign || fails(function () {
  // should have correct order of operations (Edge bug)
  if (DESCRIPTORS && $assign({ b: 1 }, $assign(defineProperty({}, 'a', {
    enumerable: true,
    get: function () {
      defineProperty(this, 'b', {
        value: 3,
        enumerable: false
      });
    }
  }), { b: 2 })).b !== 1) return true;
  // should work with symbols and should have deterministic property order (V8 bug)
  var A = {};
  var B = {};
  // eslint-disable-next-line es-x/no-symbol -- safe
  var symbol = Symbol();
  var alphabet = 'abcdefghijklmnopqrst';
  A[symbol] = 7;
  alphabet.split('').forEach(function (chr) { B[chr] = chr; });
  return $assign({}, A)[symbol] != 7 || objectKeys($assign({}, B)).join('') != alphabet;
}) ? function assign(target, source) { // eslint-disable-line no-unused-vars -- required for `.length`
  var T = toObject(target);
  var argumentsLength = arguments.length;
  var index = 1;
  var getOwnPropertySymbols = getOwnPropertySymbolsModule.f;
  var propertyIsEnumerable = propertyIsEnumerableModule.f;
  while (argumentsLength > index) {
    var S = IndexedObject(arguments[index++]);
    var keys = getOwnPropertySymbols ? concat(objectKeys(S), getOwnPropertySymbols(S)) : objectKeys(S);
    var length = keys.length;
    var j = 0;
    var key;
    while (length > j) {
      key = keys[j++];
      if (!DESCRIPTORS || call(propertyIsEnumerable, S, key)) T[key] = S[key];
    }
  } return T;
} : $assign;


/***/ }),
/* 181 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var global = __webpack_require__(0);
var uncurryThis = __webpack_require__(1);
var aCallable = __webpack_require__(59);
var isObject = __webpack_require__(11);
var hasOwn = __webpack_require__(8);
var arraySlice = __webpack_require__(88);
var NATIVE_BIND = __webpack_require__(46);

var Function = global.Function;
var concat = uncurryThis([].concat);
var join = uncurryThis([].join);
var factories = {};

var construct = function (C, argsLength, args) {
  if (!hasOwn(factories, argsLength)) {
    for (var list = [], i = 0; i < argsLength; i++) list[i] = 'a[' + i + ']';
    factories[argsLength] = Function('C,a', 'return new C(' + join(list, ',') + ')');
  } return factories[argsLength](C, args);
};

// `Function.prototype.bind` method implementation
// https://tc39.es/ecma262/#sec-function.prototype.bind
module.exports = NATIVE_BIND ? Function.bind : function bind(that /* , ...args */) {
  var F = aCallable(this);
  var Prototype = F.prototype;
  var partArgs = arraySlice(arguments, 1);
  var boundFunction = function bound(/* args... */) {
    var args = concat(partArgs, arraySlice(arguments));
    return this instanceof boundFunction ? construct(F, args.length, args) : F.apply(that, args);
  };
  if (isObject(Prototype)) boundFunction.prototype = Prototype;
  return boundFunction;
};


/***/ }),
/* 182 */
/***/ (function(module, exports, __webpack_require__) {

var hasOwn = __webpack_require__(8);

module.exports = function (descriptor) {
  return descriptor !== undefined && (hasOwn(descriptor, 'value') || hasOwn(descriptor, 'writable'));
};


/***/ }),
/* 183 */
/***/ (function(module, exports, __webpack_require__) {

// TODO: Remove this module from `core-js@4` since it's replaced to module below
__webpack_require__(184);


/***/ }),
/* 184 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var collection = __webpack_require__(123);
var collectionStrong = __webpack_require__(132);

// `Map` constructor
// https://tc39.es/ecma262/#sec-map-objects
collection('Map', function (init) {
  return function Map() { return init(this, arguments.length ? arguments[0] : undefined); };
}, collectionStrong);


/***/ }),
/* 185 */
/***/ (function(module, exports, __webpack_require__) {

// TODO: Remove from `core-js@4`
__webpack_require__(186);


/***/ }),
/* 186 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var $ = __webpack_require__(3);
var global = __webpack_require__(0);
var call = __webpack_require__(7);
var uncurryThis = __webpack_require__(1);
var requireObjectCoercible = __webpack_require__(19);
var isCallable = __webpack_require__(5);
var isRegExp = __webpack_require__(91);
var toString = __webpack_require__(13);
var getMethod = __webpack_require__(36);
var regExpFlags = __webpack_require__(89);
var getSubstitution = __webpack_require__(133);
var wellKnownSymbol = __webpack_require__(4);
var IS_PURE = __webpack_require__(33);

var REPLACE = wellKnownSymbol('replace');
var RegExpPrototype = RegExp.prototype;
var TypeError = global.TypeError;
var getFlags = uncurryThis(regExpFlags);
var indexOf = uncurryThis(''.indexOf);
var replace = uncurryThis(''.replace);
var stringSlice = uncurryThis(''.slice);
var max = Math.max;

var stringIndexOf = function (string, searchValue, fromIndex) {
  if (fromIndex > string.length) return -1;
  if (searchValue === '') return fromIndex;
  return indexOf(string, searchValue, fromIndex);
};

// `String.prototype.replaceAll` method
// https://tc39.es/ecma262/#sec-string.prototype.replaceall
$({ target: 'String', proto: true }, {
  replaceAll: function replaceAll(searchValue, replaceValue) {
    var O = requireObjectCoercible(this);
    var IS_REG_EXP, flags, replacer, string, searchString, functionalReplace, searchLength, advanceBy, replacement;
    var position = 0;
    var endOfLastMatch = 0;
    var result = '';
    if (searchValue != null) {
      IS_REG_EXP = isRegExp(searchValue);
      if (IS_REG_EXP) {
        flags = toString(requireObjectCoercible('flags' in RegExpPrototype
          ? searchValue.flags
          : getFlags(searchValue)
        ));
        if (!~indexOf(flags, 'g')) throw TypeError('`.replaceAll` does not allow non-global regexes');
      }
      replacer = getMethod(searchValue, REPLACE);
      if (replacer) {
        return call(replacer, searchValue, O, replaceValue);
      } else if (IS_PURE && IS_REG_EXP) {
        return replace(toString(O), searchValue, replaceValue);
      }
    }
    string = toString(O);
    searchString = toString(searchValue);
    functionalReplace = isCallable(replaceValue);
    if (!functionalReplace) replaceValue = toString(replaceValue);
    searchLength = searchString.length;
    advanceBy = max(1, searchLength);
    position = stringIndexOf(string, searchString, 0);
    while (position !== -1) {
      replacement = functionalReplace
        ? toString(replaceValue(searchString, position, string))
        : getSubstitution(searchString, string, position, [], undefined, replaceValue);
      result += stringSlice(string, endOfLastMatch, position) + replacement;
      endOfLastMatch = position + searchLength;
      position = stringIndexOf(string, searchString, position + advanceBy);
    }
    if (endOfLastMatch < string.length) {
      result += stringSlice(string, endOfLastMatch);
    }
    return result;
  }
});


/***/ }),
/* 187 */
/***/ (function(module, exports, __webpack_require__) {

var uncurryThis = __webpack_require__(1);

// `thisNumberValue` abstract operation
// https://tc39.es/ecma262/#sec-thisnumbervalue
module.exports = uncurryThis(1.0.valueOf);


/***/ }),
/* 188 */
/***/ (function(module, exports) {

(function () {
  if (typeof window.CustomEvent === 'function') return false; //If not IE

  function CustomEvent(event, params) {
    params = params || {
      bubbles: false,
      cancelable: false,
      detail: undefined
    };
    var evt = document.createEvent('CustomEvent');
    evt.initCustomEvent(event, params.bubbles, params.cancelable, params.detail);
    return evt;
  }

  CustomEvent.prototype = window.Event.prototype;
  window.CustomEvent = CustomEvent;
})();

/***/ }),
/* 189 */
/***/ (function(module, exports, __webpack_require__) {

var $ = __webpack_require__(3);
var fill = __webpack_require__(190);
var addToUnscopables = __webpack_require__(94);

// `Array.prototype.fill` method
// https://tc39.es/ecma262/#sec-array.prototype.fill
$({ target: 'Array', proto: true }, {
  fill: fill
});

// https://tc39.es/ecma262/#sec-array.prototype-@@unscopables
addToUnscopables('fill');


/***/ }),
/* 190 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";

var toObject = __webpack_require__(16);
var toAbsoluteIndex = __webpack_require__(68);
var lengthOfArrayLike = __webpack_require__(27);

// `Array.prototype.fill` method implementation
// https://tc39.es/ecma262/#sec-array.prototype.fill
module.exports = function fill(value /* , start = 0, end = @length */) {
  var O = toObject(this);
  var length = lengthOfArrayLike(O);
  var argumentsLength = arguments.length;
  var index = toAbsoluteIndex(argumentsLength > 1 ? arguments[1] : undefined, length);
  var end = argumentsLength > 2 ? arguments[2] : undefined;
  var endPos = end === undefined ? length : toAbsoluteIndex(end, length);
  while (endPos > index) O[index++] = value;
  return O;
};


/***/ }),
/* 191 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
// ESM COMPAT FLAG
__webpack_require__.r(__webpack_exports__);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.object.to-string.js
var es_object_to_string = __webpack_require__(9);

// EXTERNAL MODULE: ./node_modules/core-js/modules/web.dom-collections.for-each.js
var web_dom_collections_for_each = __webpack_require__(14);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.array.concat.js
var es_array_concat = __webpack_require__(42);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.object.keys.js
var es_object_keys = __webpack_require__(21);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.symbol.js
var es_symbol = __webpack_require__(22);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.array.filter.js
var es_array_filter = __webpack_require__(24);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.object.get-own-property-descriptor.js
var es_object_get_own_property_descriptor = __webpack_require__(29);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.object.get-own-property-descriptors.js
var es_object_get_own_property_descriptors = __webpack_require__(30);

// EXTERNAL MODULE: ./node_modules/element-closest-polyfill/index.js
var element_closest_polyfill = __webpack_require__(157);

// EXTERNAL MODULE: ./node_modules/smoothscroll-polyfill/dist/smoothscroll.js
var smoothscroll = __webpack_require__(140);
var smoothscroll_default = /*#__PURE__*/__webpack_require__.n(smoothscroll);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.regexp.exec.js
var es_regexp_exec = __webpack_require__(15);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.string.split.js
var es_string_split = __webpack_require__(55);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.array.iterator.js
var es_array_iterator = __webpack_require__(23);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.set.js
var es_set = __webpack_require__(163);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.string.iterator.js
var es_string_iterator = __webpack_require__(25);

// EXTERNAL MODULE: ./node_modules/core-js/modules/web.dom-collections.iterator.js
var web_dom_collections_iterator = __webpack_require__(26);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.string.replace.js
var es_string_replace = __webpack_require__(76);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.array.includes.js
var es_array_includes = __webpack_require__(170);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.string.includes.js
var es_string_includes = __webpack_require__(171);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.string.starts-with.js
var es_string_starts_with = __webpack_require__(172);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.array.slice.js
var es_array_slice = __webpack_require__(97);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.symbol.description.js
var es_symbol_description = __webpack_require__(31);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.symbol.iterator.js
var es_symbol_iterator = __webpack_require__(32);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.function.name.js
var es_function_name = __webpack_require__(77);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.array.from.js
var es_array_from = __webpack_require__(78);

// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/_dependencyvendor/event-handler.js
function _slicedToArray(arr, i) { return _arrayWithHoles(arr) || _iterableToArrayLimit(arr, i) || _unsupportedIterableToArray(arr, i) || _nonIterableRest(); }

function _nonIterableRest() { throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }

function _unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return _arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen); }

function _arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) { arr2[i] = arr[i]; } return arr2; }

function _iterableToArrayLimit(arr, i) { var _i = arr == null ? null : typeof Symbol !== "undefined" && arr[Symbol.iterator] || arr["@@iterator"]; if (_i == null) return; var _arr = []; var _n = true; var _d = false; var _s, _e; try { for (_i = _i.call(arr); !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i["return"] != null) _i["return"](); } finally { if (_d) throw _e; } } return _arr; }

function _arrayWithHoles(arr) { if (Array.isArray(arr)) return arr; }




















/**
 * --------------------------------------------------------------------------
 * Bootstrap (v5.1.3): dom/event-handler.js
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/main/LICENSE)
 * --------------------------------------------------------------------------
 */
//import { getjQuery } from '../util/index';

/**
 * Constants
 */
var getjQuery = function getjQuery() {
  var _window = window,
      jQuery = _window.jQuery;

  if (jQuery && !document.body.hasAttribute('data-bs-no-jquery')) {
    return jQuery;
  }

  return null;
};

var namespaceRegex = /[^.]*(?=\..*)\.|.*/;
var stripNameRegex = /\..*/;
var stripUidRegex = /::\d+$/;
var eventRegistry = {}; // Events storage

var uidEvent = 1;
var customEvents = {
  mouseenter: 'mouseover',
  mouseleave: 'mouseout'
};
var customEventsRegex = /^(mouseenter|mouseleave)/i;
var nativeEvents = new Set(['click', 'dblclick', 'mouseup', 'mousedown', 'contextmenu', 'mousewheel', 'DOMMouseScroll', 'input', 'mouseover', 'mouseout', 'mousemove', 'selectstart', 'selectend', 'keydown', 'keypress', 'keyup', 'orientationchange', 'touchstart', 'touchmove', 'touchend', 'touchcancel', 'pointerdown', 'pointermove', 'pointerup', 'pointerleave', 'pointercancel', 'gesturestart', 'gesturechange', 'gestureend', 'focus', 'blur', 'change', 'reset', 'select', 'submit', 'focusin', 'focusout', 'load', 'unload', 'beforeunload', 'resize', 'move', 'DOMContentLoaded', 'readystatechange', 'error', 'abort', 'scroll']);
/**
 * Private methods
 */

function getUidEvent(element, uid) {
  return uid && "".concat(uid, "::").concat(uidEvent++) || element.uidEvent || uidEvent++;
}

function getEvent(element) {
  var uid = getUidEvent(element);
  element.uidEvent = uid;
  eventRegistry[uid] = eventRegistry[uid] || {};
  return eventRegistry[uid];
}

function bootstrapHandler(element, fn) {
  return function handler(event) {
    event.delegateTarget = element;

    if (handler.oneOff) {
      EventHandler.off(element, event.type, fn);
    }

    return fn.apply(element, [event]);
  };
}

function bootstrapDelegationHandler(element, selector, fn) {
  return function handler(event) {
    var domElements = element.querySelectorAll(selector);

    for (var target = event.target; target && target !== this; target = target.parentNode) {
      for (var i = domElements.length; i--;) {
        if (domElements[i] === target) {
          event.delegateTarget = target;

          if (handler.oneOff) {
            EventHandler.off(element, event.type, selector, fn);
          }

          return fn.apply(target, [event]);
        }
      }
    } // To please ESLint


    return null;
  };
}

function findHandler(events, handler) {
  var delegationSelector = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
  var uidEventList = Object.keys(events);

  for (var _i = 0, _uidEventList = uidEventList; _i < _uidEventList.length; _i++) {
    var _uidEvent = _uidEventList[_i];
    var event = events[_uidEvent];

    if (event.originalHandler === handler && event.delegationSelector === delegationSelector) {
      return event;
    }
  }

  return null;
}

function normalizeParams(originalTypeEvent, handler, delegationFn) {
  var delegation = typeof handler === 'string';
  var originalHandler = delegation ? delegationFn : handler;
  var typeEvent = getTypeEvent(originalTypeEvent);
  var isNative = nativeEvents.has(typeEvent);

  if (!isNative) {
    typeEvent = originalTypeEvent;
  }

  return [delegation, originalHandler, typeEvent];
}

function addHandler(element, originalTypeEvent, handler, delegationFn, oneOff) {
  if (typeof originalTypeEvent !== 'string' || !element) {
    return;
  }

  if (!handler) {
    handler = delegationFn;
    delegationFn = null;
  } // in case of mouseenter or mouseleave wrap the handler within a function that checks for its DOM position
  // this prevents the handler from being dispatched the same way as mouseover or mouseout does


  if (customEventsRegex.test(originalTypeEvent)) {
    var wrapFn = function wrapFn(fn) {
      return function (event) {
        if (!event.relatedTarget || event.relatedTarget !== event.delegateTarget && !event.delegateTarget.contains(event.relatedTarget)) {
          return fn.call(this, event);
        }
      };
    };

    if (delegationFn) {
      delegationFn = wrapFn(delegationFn);
    } else {
      handler = wrapFn(handler);
    }
  }

  var _normalizeParams = normalizeParams(originalTypeEvent, handler, delegationFn),
      _normalizeParams2 = _slicedToArray(_normalizeParams, 3),
      delegation = _normalizeParams2[0],
      originalHandler = _normalizeParams2[1],
      typeEvent = _normalizeParams2[2];

  var events = getEvent(element);
  var handlers = events[typeEvent] || (events[typeEvent] = {});
  var previousFn = findHandler(handlers, originalHandler, delegation ? handler : null);

  if (previousFn) {
    previousFn.oneOff = previousFn.oneOff && oneOff;
    return;
  }

  var uid = getUidEvent(originalHandler, originalTypeEvent.replace(namespaceRegex, ''));
  var fn = delegation ? bootstrapDelegationHandler(element, handler, delegationFn) : bootstrapHandler(element, handler);
  fn.delegationSelector = delegation ? handler : null;
  fn.originalHandler = originalHandler;
  fn.oneOff = oneOff;
  fn.uidEvent = uid;
  handlers[uid] = fn;
  element.addEventListener(typeEvent, fn, delegation);
}

function removeHandler(element, events, typeEvent, handler, delegationSelector) {
  var fn = findHandler(events[typeEvent], handler, delegationSelector);

  if (!fn) {
    return;
  }

  element.removeEventListener(typeEvent, fn, Boolean(delegationSelector));
  delete events[typeEvent][fn.uidEvent];
}

function removeNamespacedHandlers(element, events, typeEvent, namespace) {
  var storeElementEvent = events[typeEvent] || {};

  for (var _i2 = 0, _Object$keys = Object.keys(storeElementEvent); _i2 < _Object$keys.length; _i2++) {
    var handlerKey = _Object$keys[_i2];

    if (handlerKey.includes(namespace)) {
      var event = storeElementEvent[handlerKey];
      removeHandler(element, events, typeEvent, event.originalHandler, event.delegationSelector);
    }
  }
}

function getTypeEvent(event) {
  // allow to get the native events from namespaced events ('click.bs.button' --> 'click')
  event = event.replace(stripNameRegex, '');
  return customEvents[event] || event;
}

var EventHandler = {
  on: function on(element, event, handler, delegationFn) {
    addHandler(element, event, handler, delegationFn, false);
  },
  one: function one(element, event, handler, delegationFn) {
    addHandler(element, event, handler, delegationFn, true);
  },
  off: function off(element, originalTypeEvent, handler, delegationFn) {
    if (typeof originalTypeEvent !== 'string' || !element) {
      return;
    }

    var _normalizeParams3 = normalizeParams(originalTypeEvent, handler, delegationFn),
        _normalizeParams4 = _slicedToArray(_normalizeParams3, 3),
        delegation = _normalizeParams4[0],
        originalHandler = _normalizeParams4[1],
        typeEvent = _normalizeParams4[2];

    var inNamespace = typeEvent !== originalTypeEvent;
    var events = getEvent(element);
    var isNamespace = originalTypeEvent.startsWith('.');

    if (typeof originalHandler !== 'undefined') {
      // Simplest case: handler is passed, remove that listener ONLY.
      if (!events || !events[typeEvent]) {
        return;
      }

      removeHandler(element, events, typeEvent, originalHandler, delegation ? handler : null);
      return;
    }

    if (isNamespace) {
      for (var _i3 = 0, _Object$keys2 = Object.keys(events); _i3 < _Object$keys2.length; _i3++) {
        var elementEvent = _Object$keys2[_i3];
        removeNamespacedHandlers(element, events, elementEvent, originalTypeEvent.slice(1));
      }
    }

    var storeElementEvent = events[typeEvent] || {};

    for (var _i4 = 0, _Object$keys3 = Object.keys(storeElementEvent); _i4 < _Object$keys3.length; _i4++) {
      var keyHandlers = _Object$keys3[_i4];
      var handlerKey = keyHandlers.replace(stripUidRegex, '');

      if (!inNamespace || originalTypeEvent.includes(handlerKey)) {
        var event = storeElementEvent[keyHandlers];
        removeHandler(element, events, typeEvent, event.originalHandler, event.delegationSelector);
      }
    }
  },
  trigger: function trigger(element, event, args) {
    if (typeof event !== 'string' || !element) {
      return null;
    }

    var $ = getjQuery();
    var typeEvent = getTypeEvent(event);
    var inNamespace = event !== typeEvent;
    var isNative = nativeEvents.has(typeEvent);
    var jQueryEvent;
    var bubbles = true;
    var nativeDispatch = true;
    var defaultPrevented = false;
    var evt = null;

    if (inNamespace && $) {
      jQueryEvent = $.Event(event, args);
      $(element).trigger(jQueryEvent);
      bubbles = !jQueryEvent.isPropagationStopped();
      nativeDispatch = !jQueryEvent.isImmediatePropagationStopped();
      defaultPrevented = jQueryEvent.isDefaultPrevented();
    }

    if (isNative) {
      evt = document.createEvent('HTMLEvents');
      evt.initEvent(typeEvent, bubbles, true);
    } else {
      evt = new CustomEvent(event, {
        bubbles: bubbles,
        cancelable: true
      });
    } // merge custom information in our event


    if (typeof args !== 'undefined') {
      var _loop = function _loop() {
        var key = _Object$keys4[_i5];
        Object.defineProperty(evt, key, {
          get: function get() {
            return args[key];
          }
        });
      };

      for (var _i5 = 0, _Object$keys4 = Object.keys(args); _i5 < _Object$keys4.length; _i5++) {
        _loop();
      }
    }

    if (defaultPrevented) {
      evt.preventDefault();
    }

    if (nativeDispatch) {
      element.dispatchEvent(evt);
    }

    if (evt.defaultPrevented && typeof jQueryEvent !== 'undefined') {
      jQueryEvent.preventDefault();
    }

    return evt;
  }
};
/* harmony default export */ var event_handler = (EventHandler);
// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/util/data-helper.js





/**
 * 펼치지 헬퍼 함수
 * data-expand="클래스 이름"
 * data-expand-target="펼칠 컨텐츠 셀렉터"
 */

var data_helper_expandHelper = function expandHelper() {
  var elements = document.querySelectorAll('[data-expand]');
  elements.forEach(function (element) {
    if (!element.getAttribute('data-expand-initialize')) {
      element.setAttribute('data-expand-initialize', 'true');
      var className = element.getAttribute('data-expand');
      var contentName = element.getAttribute('data-expand-target');
      var content = document.querySelector(contentName);
      event_handler.on(element, 'click', function (e) {
        if (element.classList.contains(className)) {
          element.classList.remove(className); // eslint-disable-next-line no-unused-vars

          var reflow = content.offsetHeight;
          content.style.height = "";
          content.classList.remove(className);
        } else {
          element.classList.add(className);
          content.classList.add(className);
          content.style.height = "".concat(content.scrollHeight, "px");
        }

        e.preventDefault();
      });
    }
  });
};
/**
 * 토글 클래스 헬퍼 함수
 * data-toggle-class="클래스 이름"
 * data-toggle-target="컨텐츠 셀렉터"
 * 컨텐츠를 선언하면 컨텐츠에도 함께 토글 클래스가 생성 됨
 */

var data_helper_toggleHelper = function toggleHelper() {
  var elements = document.querySelectorAll('[data-toggle-class]');
  elements.forEach(function (element) {
    if (!element.getAttribute('data-toggle-initialize')) {
      element.setAttribute('data-toggle-initialize', 'true');
      var toggleClassName = element.getAttribute('data-toggle-class');
      var contentName = element.getAttribute('data-toggle-target');
      var contents = []; // 컨텐츠가 여러개 라면..

      if (contentName.indexOf('|')) {
        var contentNames = contentName.split("|");
        contentNames.forEach(function (c) {
          var content = document.querySelector(c);

          if (!content) {
            throw Error("content\uB97C \uCC3E\uC744 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.: ".concat(c));
          }

          contents.push(content);
        });
      } else {
        var content = document.querySelector(contentName);

        if (!content) {
          throw Error("content\uB97C \uCC3E\uC744 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.: ".concat(c));
        }

        contents.push(content);
      }

      event_handler.on(element, 'click', function (e) {
        e.preventDefault();
        var target = e.currentTarget;

        if (target.classList.contains(toggleClassName)) {
          if (contents.length > 0) {
            contents.forEach(function (c) {
              c.classList.remove(toggleClassName);
            });
          }

          target.classList.remove(toggleClassName);
        } else {
          target.classList.add(toggleClassName);

          if (contents.length > 0) {
            contents.forEach(function (c) {
              c.classList.add(toggleClassName);
            });
          }
        }
      });
    }
  });
};
// EXTERNAL MODULE: ./node_modules/core-js/modules/es.string.trim.js
var es_string_trim = __webpack_require__(175);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.array.join.js
var es_array_join = __webpack_require__(177);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.array.map.js
var es_array_map = __webpack_require__(178);

// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/util/dom-util.js
function _toConsumableArray(arr) { return _arrayWithoutHoles(arr) || _iterableToArray(arr) || dom_util_unsupportedIterableToArray(arr) || _nonIterableSpread(); }

function _nonIterableSpread() { throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }

function dom_util_unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return dom_util_arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return dom_util_arrayLikeToArray(o, minLen); }

function _iterableToArray(iter) { if (typeof Symbol !== "undefined" && iter[Symbol.iterator] != null || iter["@@iterator"] != null) return Array.from(iter); }

function _arrayWithoutHoles(arr) { if (Array.isArray(arr)) return dom_util_arrayLikeToArray(arr); }

function dom_util_arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) { arr2[i] = arr[i]; } return arr2; }

function _typeof(obj) { "@babel/helpers - typeof"; return _typeof = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (obj) { return typeof obj; } : function (obj) { return obj && "function" == typeof Symbol && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }, _typeof(obj); }



















/**
 * HTML스트링을 HTML형태로 반환
 * @param {String} htmlString
 * @return {Element}} 엘리먼트
 */
var toHTML = function toHTML(htmlString) {
  var div = document.createElement('div');
  div.innerHTML = htmlString.trim();
  return div.firstChild;
};
/**
 * @param {String} 셀렉터 이름을 반환
 * @returns
 */

var getSelectorName = function getSelectorName(element) {
  var names = element.className.split(' ');
  return ".".concat(names.join('.'));
};
/**
 *
 * @param {*} element
 * @returns
 */

var getIndex = function getIndex(element) {
  if (!element) {
    return -1;
  }

  var currentElement = element;
  var index = 0;

  while (currentElement.previousElementSibling) {
    index += 1;
    currentElement = currentElement.previousElementSibling;
  }

  return index;
};
/**
 * element visible check
 * @param element
 */

var isVisible = function isVisible(element) {
  return element.clientWidth !== 0 && element.clientHeight !== 0 && element.style.opacity !== '0' && element.style.visibility !== 'hidden'; // if (!isElement(element) || element.getClientRects().length === 0) {
  //   return false;
  // }
};
/**
 * element disable check
 * @param {*} element
 * @returns
 */

var isDisabled = function isDisabled(element) {
  if (!element || element.nodeType !== Node.ELEMENT_NODE) {
    return true;
  }

  if (element.classList.contains('disabled')) {
    return true;
  }

  if (typeof element.disabled !== 'undefined') {
    return element.disabled;
  }

  return element.hasAttribute('disabled') && element.getAttribute('disabled') !== 'false';
};
/**
 * 엘리먼트에 선언되어있는 data attribute를 오브젝트 형태로 반환
 * UI 컴포넌트에서 data attribute로 속성 값 전달할 떄 사용
 * @param {*} element
 * @returns
 */

var dataSetToObject = function dataSetToObject(element, dataAttrConfig) {
  var prefix = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : '';
  var config = {};

  for (var key in dataAttrConfig) {
    if (Object.prototype.hasOwnProperty.call(dataAttrConfig, key)) {
      var attrvalue = element.dataset["".concat(prefix).concat(key.charAt(0).toUpperCase() + key.slice(1))];

      if (attrvalue) {
        config[key] = attrvalue;
      }
    }
  }

  return config;
};
/**
 * target으로 받은 엘리먼트를 알아서 반환한다.
 * select, string 판단하여 적절하게..
 * @param {*} target
 */

var getElement = function getElement(target) {
  if (typeof target === 'string') {
    return document.querySelector(target);
  }

  if (_typeof(target) === 'object') {
    return target;
  }
};
/**
 * 포커스 가능한 엘리먼트 반환
 * @param {*} element
 * @returns
 */

var focusableElements = function focusableElements(element) {
  var _ref;

  var focusableElementSelectors = ['a', 'button', 'input', 'textarea', 'select', 'details', '[tabindex]'];
  return (_ref = []).concat.apply(_ref, _toConsumableArray(Element.prototype.querySelectorAll.call(element, focusableElementSelectors.map(function (selector) {
    return "".concat(selector, ":not([tabindex^=\"-\"])");
  }).join(', ')))).filter(function (el) {
    return !isDisabled(el) && isVisible(el);
  });
};
// EXTERNAL MODULE: ./node_modules/core-js/modules/es.regexp.to-string.js
var es_regexp_to_string = __webpack_require__(138);

// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/util/string-util.js


var getRandomID = function getRandomID() {
  return "id_".concat(Math.random().toString(36).substr(2, 9));
};
// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/util/util.js


var optimizeCaller = function optimizeCaller(callback) {
  var tick = false;
  return function () {
    if (tick) return;
    tick = true;
    return window.requestAnimationFrame(function () {
      tick = false;
      return callback();
    });
  };
};
var imageLoadCheck = function imageLoadCheck(container, callback) {
  var imgs = container.querySelectorAll('img');

  if (imgs.length === 0) {
    callback();
    return;
  }

  var count = 0;

  var complateCheck = function complateCheck() {
    count++;
    console.log(count, imgs.length);

    if (count >= imgs.length) {
      callback();
    }
  };

  imgs.forEach(function (_img) {
    var img = new Image(_img);
    img.src = _img.getAttribute('src');

    img.onload = function () {
      complateCheck();
    };

    img.onerror = function () {
      complateCheck();
    };

    img.onAbort = function () {
      complateCheck();
    };
  });
};
// EXTERNAL MODULE: ./node_modules/core-js/modules/es.object.assign.js
var es_object_assign = __webpack_require__(179);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.string.match.js
var es_string_match = __webpack_require__(98);

// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/util/a11y.js





/* eslint-disable max-len */



var a11y_confineTab = function confineTab(el) {
  var beforeFocused = document.hasFocus() && document.activeElement !== document.body && document.activeElement !== document.documentElement && document.activeElement || null;
  return {
    init: function init() {
      event_handler.off(el, 'focusin.keydown');
      event_handler.on(el, 'focusin.keydown', function (event) {
        var focusEl = focusableElements(el);
        focusEl.forEach(function (el) {
          event_handler.off(el, 'keydown.tab');
        });
        var first = focusEl[0];
        var last = focusEl[focusEl.length - 1];

        if (last === event.target) {
          event_handler.on(last, 'keydown.tab', function (event) {
            var keyCode = event.keyCode || event.which;

            if (keyCode === 9) {
              if (!event.shiftKey) {
                first.focus();
                event.preventDefault();
              }
            }
          });
        }

        if (first === event.target) {
          event_handler.on(first, 'keydown.tab', function (event) {
            var keyCode = event.keyCode || event.which;

            if (keyCode === 9) {
              if (event.shiftKey) {
                last.focus();
                event.preventDefault();
              }
            }
          });
        }
      });
      return focusableElements(el);
    },
    destroy: function destroy() {
      var autoComeback = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : true;
      event_handler.off(el, 'focusin.keydown');
      if (beforeFocused && autoComeback) beforeFocused.focus();
    }
  };
};

var tabIndexble = function tabIndexble(el) {
  var isDisabled = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : true;
  el.querySelectorAll('*').forEach(function (el, i) {
    if (el.tagName.match(/^A$|AREA|INPUT|TEXTAREA|SELECT|BUTTON/gim)) {
      if (isDisabled) {
        el.setAttribute('tabindex', -1);
      } else {
        el.removeAttribute('tabindex');
      }
    }

    if (el.getAttribute('data-tabindex')) {
      if (isDisabled) {
        el.setAttribute('tabindex', -1);
      } else {
        var beforeIndex = el.getAttribute('data-tabindex');
        el.setAttribute('tabindex', beforeIndex);
      }
    } // tabindex가 선언되어있던


    if (el.getAttribute('tabIndex') !== null && parseInt(el.getAttribute('tabIndex')) >= 0 && el.getAttribute('tabIndex', 2) !== 32768) {
      if (!el.getAttribute('data-tabindex')) {
        el.setAttribute('data-tabindex', el.getAttribute('tabIndex'));
      }

      if (isDisabled) {
        el.setAttribute('tabindex', -1);
      } else {
        var _beforeIndex = el.getAttribute('data-tabindex');

        el.setAttribute('tabindex', _beforeIndex);
      }
    }
  });
};


// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/common.js










var initializeCheck = function initializeCheck(el) {
  return el.getAttribute('data-function-initialized') !== 'initialized';
};

var setInitialize = function setInitialize(el) {
  return el.setAttribute('data-function-initialized', 'initialized');
};

var flatpickrKor = {
  weekdays: {
    shorthand: ['일', '월', '화', '수', '목', '금', '토'],
    longhand: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일']
  },
  months: {
    shorthand: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    longhand: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
  },
  ordinal: function ordinal() {
    return '일';
  },
  rangeSeparator: ' ~ ',
  amPM: ['오전', '오후']
};

var datepickerValueBinder = function datepickerValueBinder() {
  if (window.flatpickr && !flatpickr.l10ns.ko) {
    flatpickr.l10ns.ko = flatpickrKor;
    flatpickr.localize(flatpickr.l10ns.ko);
  }
}; // 인풋 포커스 활성화 시 레이블 애니메이션 처리


var formInteraction = function formInteraction() {
  var forms = document.querySelectorAll('.c-form__input');
  forms.forEach(function (form) {
    if (initializeCheck(form)) {
      setInitialize(form);
      var input = form.querySelector('input');
      var inputValue = input.value;

      if (inputValue.length > 0) {
        form.classList.add('has-value');
      } else {
        form.classList.remove('has-value');
      }

      input.addEventListener('focusout', function (event) {
        inputValue = input.value;

        if (inputValue.length > 0) {
          form.classList.add('has-value');
        } else {
          form.classList.remove('has-value');
        }
      });
    }
  });
}; // 셀렉트 폼에서 값 선택 후 레이블 및 값 유지 처리


var selectInteraction = function selectInteraction() {
  var selects = document.querySelectorAll('.c-form__select');

  if (selects.length > 0) {
    selects.forEach(function (eachSelect) {
      if (initializeCheck(eachSelect)) {
        setInitialize(eachSelect);
        var select = eachSelect.querySelector('select');
        var selectValue = select.value;

        if (selectValue.length > 0) {
          eachSelect.classList.add('has-value');
        } else {
          eachSelect.classList.remove('has-value');
        }

        eachSelect.addEventListener('focusout', function (event) {
          selectValue = select.value;

          if (selectValue.length > 0) {
            eachSelect.classList.add('has-value');
          } else {
            eachSelect.classList.remove('has-value');
          }
        });
      }
    });
  }
}; // 인풋 그룹 포커스 활설화 시 아웃라인 처리


var inputGroupInteraction = function inputGroupInteraction() {
  var forms = document.querySelectorAll('.c-form__input-group input');
  [].forEach.call(forms, function (input) {
    if (initializeCheck(input)) {
      setInitialize(input);
      input.addEventListener('focusin', function (e) {
        if (input.getAttribute('readonly') !== null) {
          return;
        }

        e.target.parentElement.parentElement.classList.add('is-focused');
      });
      input.addEventListener('focusout', function (e) {
        if (input.getAttribute('readonly') !== null) {
          return;
        }

        e.target.parentElement.parentElement.classList.remove('is-focused');
      });
    }
  });
}; // 검색 인풋 버튼 활성화


var common_searchBoxAction = function searchBoxAction() {
  var searchInputs = document.querySelectorAll('.c-form--search');

  if (searchInputs.length > 0) {
    searchInputs.forEach(function (search) {
      if (initializeCheck(search)) {
        setInitialize(search);
        var inputTarget = search.querySelector('.c-input');
        var resetBtn = search.querySelector('.c-button--reset');
        var searchBtn = search.querySelector('.c-button-round--sm');
        event_handler.on(inputTarget, 'keyup', function (e) {
          if (e.currentTarget.value !== '') {
            if (!resetBtn.classList.contains('is-active')) {
              searchBtn.classList.add('is-focus');
              resetBtn.classList.add('is-active');
              event_handler.one(resetBtn, 'click', function () {
                inputTarget.value = '';
                searchBtn.classList.remove('is-focus');
                resetBtn.classList.remove('is-active');
                inputTarget.focus();
              });
            }
          } else {
            searchBtn.classList.remove('is-focus');
            resetBtn.classList.remove('is-active');
            event_handler.off(resetBtn, 'click');
          }
        });
      }
    });
  }
};
/**
 * 스크롤 영역 변경
 */


var common_scrollChange = function scrollChange() {
  var changeScrollBoxs = document.querySelectorAll('.c-filter--accordion');

  if (changeScrollBoxs.length) {
    changeScrollBoxs.forEach(function (box) {
      if (initializeCheck(box)) {
        setInitialize(box);
        var expandBtn = box.querySelector('.c-filter--accordion__button');
        event_handler.on(expandBtn, 'click', function (e) {
          e.preventDefault();
          e.target.parentNode.classList.toggle('is-expanded');

          if (e.target.parentNode.classList.contains('is-expanded')) {
            e.target.setAttribute('aria-expanded', 'true');
            expandBtn.innerHTML = '<span class="c-hidden">접기</span>';
          } else {
            e.target.setAttribute('aria-expanded', 'false');
            expandBtn.innerHTML = '<span class="c-hidden">펼치기</span>';
          }
        });
      }
    });
  }
}; // 카운터 작동


var common_counter = function counter() {
  var counters = document.querySelectorAll('.c-counter');

  if (counters.length) {
    [].forEach.call(counters, function (el) {
      var btnPlus = el.querySelector('.plus');
      var btnMinus = el.querySelector('.minus');
      var valueBox = el.querySelector('input');
      var min = parseInt(valueBox.getAttribute('min') || -1);
      var max = parseInt(valueBox.getAttribute('max') || -1);
      var step = parseInt(valueBox.getAttribute('step') || 1);
      var initialValue = parseInt(valueBox.value) || min;
      event_handler.off(btnPlus, 'click');
      event_handler.off(btnMinus, 'click');

      var updateValue = function updateValue() {
        valueBox.value = initialValue;
      };

      valueBox.change = function () {
        var rValue = parseInt(valueBox.value);
        initialValue = rValue >= max ? max : rValue <= min ? min : rValue;
        updateValue();
      };

      event_handler.on(btnPlus, 'click', function () {
        if (initialValue + step <= max) {
          initialValue += step;
          updateValue();
        }
      });
      event_handler.on(btnMinus, 'click', function () {
        if (initialValue - step >= min) {
          initialValue -= step;
          updateValue();
        }
      });
      updateValue();
    });
  }
}; // 비교함 바텀시트


var common_bsCompareBottomSheet = function bsCompareBottomSheet() {
  var bsCompareBottomSheet = document.querySelectorAll('.c-bs-compare'); // 어트리뷰트 업데이트

  var updateAttribute = function updateAttribute(el, attrs) {
    if (el) {
      for (var p in attrs) {
        if (attrs.hasOwnProperty(p)) el.setAttribute(p, attrs[p]);
      }
    }
  }; // show, hide element


  var dimControl = function dimControl(isShow, el) {
    if (isShow) {
      document.body.appendChild(el); // eslint-disable-next-line no-unused-vars

      var cache = el.offsetHeight;
      el.style.opacity = 0.7;
    } else {
      el.style.opacity = 0;
      setTimeout(function () {
        return document.body.removeChild(el);
      }, 680);
    }
  };

  if (bsCompareBottomSheet.length > 0) {
    bsCompareBottomSheet.forEach(function (bs) {
      return function () {
        if (bs.getAttribute('data-bs-initilazed') !== 'true') {
          bs.getAttribute('data-bs-initilazed', 'true'); // 상단 배너가 없는경우 no-banner 클래스 부여하여 스타일 처리

          var hasBanner = bs.querySelector('.top-sticky-banner');
          if (!hasBanner) bs.classList.add('no-banner');
          var preview = bs.querySelector('.c-bs-compare__preview');
          var detail = bs.querySelector('.c-bs-compare__detail');
          var container = bs.querySelector('.c-bs-compare__container');
          var inner = bs.querySelector('.c-bs-compare__inner');
          var openButton = bs.querySelector('.c-bs-compare__openner');
          var openButotonText = openButton.querySelector('.sr-only');
          var footer = document.querySelector('.ly-footer');
          var stateOpenText = openButotonText.textContent.split('|');
          var previewHeight = preview ? preview.scrollHeight : 48;
          var detailHeight = detail.scrollHeight;
          var dimEl = toHTML("<div class=\"c-bs-dim\"></div>");
          var confine = a11y_confineTab(bs);
          var isAnimationg = false;
          var limitTimer = null;
          if (preview) preview.setAttribute('tabindex', 0);
          detail.setAttribute('tabindex', -1);
          setTimeout(function () {
            tabIndexble(detail, true);
          }, 500); // top-sticky-banner

          openButotonText.textContent = stateOpenText[0];
          event_handler.on(window, 'resize.bs-with', function () {
            var isOpen = openButton.classList.contains('is-active');
            if (!isOpen) return;

            if (limitTimer) {
              clearTimeout(limitTimer);
              limitTimer = null;
            }

            limitTimer = setTimeout(function () {
              var containerHeight = detailHeight;
              containerHeight = Math.min(window.innerHeight - 150, containerHeight);
              container.style.height = "".concat(containerHeight, "px");
              detail.style.height = "".concat(containerHeight, "px");
              inner.style.transform = "translateY(".concat(previewHeight * -1, "px)");
            }, 100);
          });
          event_handler.on(openButton, 'click', function (e) {
            e.preventDefault();
            if (isAnimationg) return;

            if (!openButton.classList.contains('is-active')) {
              if (Math.abs(detailHeight - detail.scrollHeight) > 3) {
                // 열 때 한번 더 체크(사이즈가 변경되었으면.,)
                detail.style.height = '';
                detailHeight = detail.scrollHeight;
              }
            }

            if (footer && !openButton.classList.contains('is-active')) {
              footer.classList.add('u-hide');
            }

            isAnimationg = true;
            event_handler.one(inner, 'transitionend', function () {
              isAnimationg = false;

              if (openButton.classList.contains('is-active')) {
                confine.init();
                var topScroll = window.pageYOffset || document.documentElement.scrollTop;
                var leftScroll = window.pageXOffset || document.documentElement.scrollLeft;
                event_handler.on(window, 'scroll.bs', function (e) {
                  window.scrollTo(leftScroll, topScroll);
                });
                event_handler.trigger(bs, 'opened');
              } else {
                event_handler.off(window, 'scroll.bs');
                event_handler.trigger(bs, 'closed');
                footer.classList.remove('u-hide');
              }
            });
            var hasClass = openButton.classList.contains('is-active');
            hasClass ? openButton.classList.remove('is-active') : openButton.classList.add('is-active');
            hasClass ? event_handler.trigger(bs, 'close') : event_handler.trigger(bs, 'open'); // 처음부터 보이고 있던 영역

            updateAttribute(preview, {
              'aria-hidden': !hasClass,
              tabindex: hasClass ? 0 : -1
            }); // 열릴 때 보여지게 되는 영역

            updateAttribute(detail, {
              'aria-hidden': hasClass,
              tabindex: hasClass ? -1 : 0
            });
            tabIndexble(detail, hasClass);
            openButotonText.textContent = hasClass ? stateOpenText[0] : stateOpenText[1]; // 바로 삭제처리

            if (hasClass) confine.destroy(); // 컨테이너 사이즈 처리

            var containerHeight = hasClass ? previewHeight : detailHeight;
            containerHeight = Math.min(window.innerHeight - 150, containerHeight);
            container.style.height = "".concat(containerHeight, "px");
            detail.style.height = "".concat(containerHeight, "px");
            inner.style.transform = "translateY(".concat(hasClass ? 0 : previewHeight * -1, "px)"); // 딤 처리

            dimControl(!hasClass, dimEl);
          });
        }
      }();
    });
  }
}; // 사은품 스크롤 영역


var common_presentsRow = function presentsRow() {
  var move = function move(el, posX) {
    if (el.scroll) {
      el.scrollBy({
        left: posX,
        behavior: 'smooth'
      });
    } else {
      // for ie
      el.scrollBy = posX;
    }
  };

  var getOutsideItem = function getOutsideItem(viewPortBox, container, viewport) {
    var isPrev = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : false;
    var items = container.querySelectorAll('.c-chk-wrap');
    var outsideItem = null;

    for (var i = 0; i < items.length; i++) {
      var item = items[i];
      var itemRect = item.getBoundingClientRect();

      if (isPrev) {
        if (itemRect.left < viewPortBox.left && Math.abs(itemRect.left - viewPortBox.left) > itemRect.width / 3) {
          outsideItem = {
            isFirst: i === 0,
            targetX: Math.floor(itemRect.left - viewPortBox.left)
          };
        }
      } else {
        if (itemRect.width + itemRect.left - viewPortBox.left > viewPortBox.width) {
          var diff = itemRect.width + itemRect.left - viewPortBox.left - viewPortBox.width;
          outsideItem = {
            targetX: diff + 2,
            isLast: i === items.length - 1
          };
          break;
        }
      }
    }

    return outsideItem;
  };

  var rows = document.querySelectorAll('.presents-wrap');
  rows.forEach(function (row) {
    var viewport = row.querySelector('.presents-row');

    if (initializeCheck(row)) {
      setInitialize(row);
      var container = row.querySelector('.c-checktype-row');
      var prevButton = row.querySelector('.presents-wrap__button--prev');
      var nextButton = row.querySelector('.presents-wrap__button--next');
      var viewPortBox = viewport.getBoundingClientRect();
      var items = container.querySelectorAll('.c-chk-wrap');
      prevButton.setAttribute('disabled', true);
      nextButton.setAttribute('disabled', true);
      items.forEach(function (item) {
        var itemRect = item.getBoundingClientRect();

        if (itemRect.width + itemRect.left - viewPortBox.left > viewPortBox.width) {
          nextButton.removeAttribute('disabled');
        }
      });
      event_handler.on(prevButton, 'click', function (e) {
        var nextViewItem = getOutsideItem(viewPortBox, container, viewport, 'prev');

        if (nextViewItem) {
          if (nextViewItem.isFirst) {
            prevButton.setAttribute('disabled', true);
          }

          nextButton.removeAttribute('disabled');
          move(viewport, nextViewItem.targetX);
        }
      });
      event_handler.on(nextButton, 'click', function (e) {
        var nextViewItem = getOutsideItem(viewPortBox, container, viewport);

        if (nextViewItem) {
          if (nextViewItem.isLast) {
            nextButton.setAttribute('disabled', true);
          }

          prevButton.removeAttribute('disabled');
          move(viewport, nextViewItem.targetX);
        }
      });
    }
  });
};

var common_headerFixed = function headerFixed() {
  if (document.querySelector('.main')) return;
  var headerFixedButton = document.querySelector('.ly-header__fixed');
  var lyHeader = document.querySelector('.ly-header');
  var lyContent = document.querySelector('.ly-content');
  var isLoginPage = document.querySelector('.fixed-login');

  if (isLoginPage) {
    lyContent.classList.add('login');
  }

  var tabContent = document.querySelector('.c-tabs--type1'); // fixed 기능 방지..
  // header 가 없는 경우에도 처리 방지

  if (tabContent && tabContent.classList.contains('no-fixed') || !lyHeader) {
    return;
  }

  var defaultFixedStart = isLoginPage ? 155 : 85;
  var tabContentY = -1;

  if (tabContent) {
    tabContentY = tabContent.getBoundingClientRect().top + window.pageYOffset;
  }

  if (headerFixedButton) {
    event_handler.on(headerFixedButton, 'click', function (event) {
      event.preventDefault();
      event_handler.trigger(window, 'openAllMenu');
    });
  }

  if (lyContent) {
    window.addEventListener('scroll', optimizeCaller(function () {
      var scTop = window.pageYOffset;
      tabFixed(scTop);

      if (scTop > defaultFixedStart) {
        lyHeader.classList.add('fixed');
        lyContent.classList.add('fixed');
        lyContent.classList.add('fill');
        if (headerFixedButton) headerFixedButton.classList.add('is-active');
      } else {
        lyHeader.classList.remove('fixed');
        lyContent.classList.remove('fixed');
        lyContent.classList.remove('fill');
        if (headerFixedButton) headerFixedButton.classList.remove('is-active');
      }
    }));
  } // eslint-disable-next-line no-unused-vars


  var tabFixed = function tabFixed(scTop) {
    if (!tabContent || tabContent.classList.contains('no-sticky')) {
        lyContent.classList.remove('fixed-tab');
        return;
    }
    if (tabContent) {
      if (scTop > tabContentY) {
        lyContent.classList.add('fixed-tab');
      } else {
        lyContent.classList.remove('fixed-tab');
      }
    }
  };
};

var common_gnb = function gnb() {
  var root = document.querySelector('.ly-header');
  if (!root) return;
  var allMenu = document.querySelector('.ly-allmenu');
  var searchButton = root.querySelector('.ly-header__search-btn');
  var search = document.querySelector('.ly-search');
  var searchClose = search.querySelector('.search__bottom .c-button');
  if (!allMenu) return;
  var allmenuButton = root.querySelector('.ly-header__allmenu');
  var allMenuClose = allMenu.querySelector('.ly-allmenu__close');
  var menuItem = root.querySelectorAll('.nav.nav--depth1 > .nav__item');
  var menuChildren = root.querySelectorAll('.nav.nav--depth2');
  var banner = root.querySelector('.ly-header__banner');
  var bg = root.querySelector('.ly-header__bg');
  var confineTabAllmenu = a11y_confineTab(allMenu);
  var confineTabSearch = a11y_confineTab(search);
  var bannerSwiper = null;
  var outTime = null;
  var currentMenuHeight = 0;
  var isMouseOver = false;

  var updateBanner = function updateBanner() {
    if (banner) {
      if (bannerSwiper === null) {
        bannerSwiper = KTM.swiperA11y('.ly-header__banner .swiper-container', {
          loop: $(".ly-header__banner .swiper-container .swiper-wrapper .swiper-slide").length > 1,  // ly-header__banner 루프 처리
          navigation: {
            nextEl: '.ly-header__banner .swiper-button-next',
            prevEl: '.ly-header__banner .swiper-button-prev'
          },
          spaceBetween: 30,
          autoplay: {
            delay: 3000,
            disableOnInteraction: false
          }
        });
      } else {
        bannerSwiper.update();
      }
    }
  };

  var initTimer = function initTimer() {
    var isStart = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;

    if (outTime) {
      clearTimeout(outTime);
      outTime = null;
    }

    if (isStart) {
      outTime = setTimeout(function () {
        showBg(false);
        menuItem.forEach(function (el) {
          var a = el.querySelector('a');
          a.classList.remove('is-over');
        });
      }, 100);
    }
  }; // 하위메뉴 전체 hide


  var swapChildMenu = function swapChildMenu(activeMenu) {
    menuChildren.forEach(function (el) {
      if (el.classList.contains('show-menu')) {
        el.classList.remove('show-menu');
      }
    });

    if (activeMenu) {
      activeMenu.classList.add('show-menu');
      var rect = activeMenu.getBoundingClientRect();
      currentMenuHeight = rect.height < 245 ? 245 + 95 : rect.height + 120;
      bg.style.height = "".concat(currentMenuHeight, "px");
    }
  };

  var getAllMenuMaxHeight = function getAllMenuMaxHeight() {
    var menus = allMenu.querySelectorAll('.nav--depth2');
    var maxHeight = 0;
    menus.forEach(function (menu) {
      if (maxHeight < menu.offsetHeight) {
        maxHeight = menu.offsetHeight;
      }
    });
    return maxHeight + 160;
  };

  var showSearch = function showSearch() {
    var isOpen = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : true;

    if (isOpen) {
      if (!search.classList.contains('is-active')) {
        search.classList.add('is-active');
        var a = confineTabSearch.init();
        if (a[0]) a[0].focus();
      }
    } else {
      if (search.classList.contains('is-active')) {
        search.classList.remove('is-active');
        confineTabSearch.destroy();
      }
    }
  };

  var showAllMenu = function showAllMenu() {
    var isOpen = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : true;

    if (isOpen === true) {
      allMenu.style.display = 'block';
      showSearch(false);
      var maxHeight = getAllMenuMaxHeight();
      allMenu.style.height = "".concat(maxHeight, "px"); // eslint-disable-next-line no-unused-vars

      var cache = allMenu.offsetHeight;
      allMenu.classList.add('is-active');
      document.body.style.overflowY = 'hidden';
      setTimeout(function () {
        var a = confineTabAllmenu.init();
        if (a[0]) a[0].focus();
      }, 400);
      event_handler.on(window, 'resize.allmenu', function () {
        var windowHeight = window.innerHeight;
        var allmenuHeight = allMenu.offsetHeight;

        if (allmenuHeight > windowHeight) {
          Object.assign(allMenu.style, {
            overflow: 'auto',
            height: "".concat(windowHeight, "px")
          });
        } else {
          Object.assign(allMenu.style, {
            overflow: 'hidden',
            height: "".concat(getAllMenuMaxHeight(), "px")
          });
        }
      });
    } else {
      allMenu.classList.remove('is-active');
      allMenu.style.display = 'none';
      confineTabAllmenu.destroy();
      allMenu.style.height = "";
      event_handler.off(window, 'resize.allmenu');
      document.body.style.overflowY = 'auto';
    }
  }; // navigation bg show


  var showBg = function showBg(isShow) {
    if (isShow) {
      if (!root.classList.contains('show-menu')) {
        showSearch(false);
        root.classList.add('show-menu');
        banner.style.display = 'block';

        if (bannerSwiper) {
          if (banner.querySelector('.swiper-button-pause')) {
            bannerSwiper.autoplay.start();
          }
        }

        updateBanner();
      }
    } else {
      if (root.classList.contains('show-menu')) {
        root.classList.remove('show-menu');
        banner.style.display = 'none';

        if (bannerSwiper) {
          bannerSwiper.autoplay.stop();
        }
      }

      swapChildMenu();
    }
  };

  event_handler.on(root, 'mouseover', function () {
    isMouseOver = true;
    initTimer(false);
  });
  event_handler.on(root, 'mouseout', function () {
    isMouseOver = false;
    initTimer(true);
  }); // 전체메뉴 열기

  event_handler.on(allmenuButton, 'click', function (e) {
    e.preventDefault();
    showAllMenu();
  }); // 통합검색 열기

  event_handler.on(searchButton, 'click', function (e) {
    e.preventDefault();
    var searchEl = document.querySelector('.ly-search');

    if (searchEl.classList.contains('is-active')) {
      showSearch(false);
    } else {
      showSearch();
    }
  }); // 통합검색 닫기

  event_handler.on(searchClose, 'click', function (e) {
    e.preventDefault();
    showSearch(false);
  });
  event_handler.on(window, 'openAllMenu', function () {
    showAllMenu();
  }); // 전체메뉴 닫기

  event_handler.on(allMenuClose, 'click', function (e) {
    e.preventDefault();
    showAllMenu(false);
  });

  var activeCurrentMenu = function activeCurrentMenu(el) {
    var currentA = el.querySelector('a');
    menuItem.forEach(function (el) {
      var a = el.querySelector('a');
      a.classList.remove('is-over');
    });
    swapChildMenu(currentA.nextElementSibling);
    currentA.classList.add('is-over');
    showBg(true);
  };

  menuItem.forEach(function (el) {
    event_handler.on(el, 'mouseover', function (event) {
      activeCurrentMenu(el);
    });
    event_handler.on(el, 'focusin', function (event) {
      activeCurrentMenu(el);
    });
  }); // 접근성 처리

  event_handler.on(root, 'focusin', function (e) {
    initTimer(false);
  });
  event_handler.on(root, 'focusout', function (e) {
    if (isMouseOver === true) return;
    initTimer(true);
  });
};

var common_clickToScroll = function clickToScroll() {
  var ellist = document.querySelectorAll('[data-click-to-scroll]');
  ellist.forEach(function (el) {
    if (initializeCheck(el)) {
      setInitialize(el);
      event_handler.on(el, 'click.ck-to-scroll', function (e) {
        var targetSelector = e.currentTarget.getAttribute('data-click-to-scroll');
        var target = document.querySelector(targetSelector);

        if (target) {
          e.preventDefault();
          var targetScrollPos = target.getBoundingClientRect().top + window.pageYOffset;
          event_handler.on(window, 'scroll.ck-to-scroll', function (e) {
            if (Math.abs(window.pageYOffset - targetScrollPos) < 1) {
              event_handler.off(window, 'scroll.ck-to-scroll');
              var focusTarget = target.querySelector('.c-tabs__list .is-active');
              if (focusTarget) focusTarget.focus();
            }
          });
          scrollTo({
            top: targetScrollPos,
            behavior: 'smooth'
          });
        }
      });
    }
  });
}; // Help fab action



var common_gotoScrollY = function gotoScrollY() {
      var scTopTrigger = document.querySelectorAll('[data-scroll-top]');
      scTopTrigger.forEach(function (target) {
        if (initializeCheck(target)) {
          setInitialize(target);
          event_handler.off('click');
          event_handler.on(target, 'click', function (e) {
            var scTargetString = target.getAttribute('data-scroll-top');
            var scTargetStringArr = scTargetString.split('|');
            var scTargetName = scTargetStringArr[0];
            var diffNum = scTargetStringArr[1] || 0;
            var scTarget = document.querySelector(scTargetName);

            if (scTarget) {
              e.preventDefault();
              scrollTo({
                top: scTarget.getBoundingClientRect().top + (window.pageYOffset - parseInt(diffNum)),
                behavior: 'smooth'
              });
            }
          });
        }
      });
    };


var common_a11yFab = function a11yFab() {
  var container = document.querySelector('.c-fab');
  var containerTop = document.querySelector('.c-top');
  var checkTime = null;

  if (container) {
    var openBtn = container.querySelector('.c-fab__open');
    var closeBtn = container.querySelector('.c-fab__close'); // 웹 접근성 탭 처리

    var gabTabs = a11y_confineTab(container);
    event_handler.on(openBtn, 'click.fab.open', function () {
      openFab();
    });
    event_handler.on(closeBtn, 'click.fab.close', function () {
      closeFab();
    });

    var openFab = function openFab() {
      container.classList.add('is-opened');
      var t = gabTabs.init();
      if (t[0]) t[0].focus();
      event_handler.on(window, 'click.fab', function (e) {
        if (!e.target.closest('.c-fab')) {
          closeFab();
        }
      });
    };

    var closeFab = function closeFab(e) {
      gabTabs.destroy();
      container.classList.remove('is-opened');
      openBtn.focus();
      event_handler.off(window, 'click.fab');
    };

    window.addEventListener('scroll', function (e) {
      if (container.classList.contains('is-opened') === true) return;

      if (checkTime) {
        clearTimeout(checkTime);
        checkTime = null;
      }

      if (container.classList.contains('is-active') && containerTop.classList.contains('is-active')) {
        container.style.display = '';
        containerTop.style.display = '';
        container.classList.remove('is-active');
        containerTop.classList.remove('is-active');
      }
      checkTime = setTimeout(function () {
        if (!container.classList.contains('is-active') && !containerTop.classList.contains('is-active')) {
          container.style.display = 'block'; // eslint-disable-next-line no-unused-vars
          var reflow = container.offsetHeight;
          var reflow = containerTop.offsetHeight;
          container.classList.add('is-active');
          containerTop.classList.add('is-active');
        }
      }, 300);

    });
  }
};

var selectedCurrentMenu = function selectedCurrentMenu(container) {
  var activeMenu = function activeMenu(el) {
    if (!el) return;

    if (el.tagName.toUpperCase() === 'A' || el.tagName.toUpperCase() === 'STRONG') {
      el.classList.add('is-active');
    } else {
      activeMenu(el.children[0]);
    }
  };

  var codeInput = document.querySelector('#gnbMCode');
  if (!codeInput) return;
  var curentPageCode = codeInput.getAttribute('value');
  if (!curentPageCode) return;
  var nav = document.querySelector(container);
  if (!nav) return;
  var selectedAll = nav.querySelectorAll('.is-active');
  selectedAll.forEach(function (el) {
    if (el.classList) el.classList.remove('is-active');
  });
  var linkCodes = nav.querySelectorAll('[data-code]');
  linkCodes.forEach(function (a) {
    var codeName = a.getAttribute('data-code');

    if (codeName === curentPageCode) {
      activeMenu(a.closest('.nav.nav--depth1 > .nav__item'));
      activeMenu(a.closest('.nav.nav--depth2 > .nav__item'));
      activeMenu(a);
    }
  });
};

var common_tabChangeToScroll = function tabChangeToScroll() {
  var elements = document.querySelectorAll('[data-changed-scroll]');
  elements.forEach(function (el) {
    if (initializeCheck(el)) {
      setInitialize(el);
      event_handler.on(el, 'ui.tab.change', function (event) {
        var diffSpacing = 65 + 65;
        var currentContentRect = event.current.content.getBoundingClientRect();
        var resultY = currentContentRect.top + window.pageYOffset - diffSpacing;

        if (diffSpacing < window.pageYOffset) {
          window.scrollTo({
            top: resultY
          });
        }
      });
    }
  });
};
/**
 * 메인페이지 헤더 고정 시나리오
 * 메인 띠 배너가 없을경우에는 처음부터 fixed를 유지한다.
 *
 * 메인 띠 배너가 생겼을 경우에는
 * 메인 띠 배너 초기화될 때 window.__mainEventActive() 함수를 호출하고
 * window.__mainEventActive 함수가 호출되면 scroll event가 등록되며 특정 위치에서 fixed 되도록 처리가 된다.
 * 메인 띠 배너를 닫을 때 window.__headerFixedCheckerRemove() 함수를 호출하여
 * scroll event를 삭제하고 고정된 상태를 유지시킨다.
 */


var common_mainHeaderFixed = function mainHeaderFixed() {
  var fixedY = 0; // ly-header

  var wrap = document.querySelector('.ly-wrap');
  if (!wrap) return;
  var headerEl = document.querySelector('.ly-wrap.main .ly-header');
  wrap.classList.add('fixed');

  window.__mainEventActive = function () {
    fixedY = 60;

    if (headerEl) {
      event_handler.on(window, 'scroll.header', optimizeCaller(function () {
        if (window.pageYOffset > fixedY) {
          wrap.classList.add('fixed');
        } else {
          wrap.classList.remove('fixed');
        }
      }));
      event_handler.trigger(window, 'scroll.header');
    }

    window.__mainEventActive = null;
  };

  window.__headerFixedCheckerRemove = function () {
    event_handler.off(window, 'scroll.header');

    if (wrap) {
      wrap.classList.add('fixed');
    }

    window.__headerFixedCheckerRemove = null;
  };
};
/**
 * 요금제 비교함 뱃지 아이콘
 */


var badgeOpenner = function badgeOpenner() {
  var badge = document.querySelector('.c-modal__content .c-side-badge');

  if (badge) {
    var modalBody = document.querySelector('.c-modal__body');
    var timer;
    modalBody.addEventListener('scroll', function () {
      var lastScrollTop = 0;
      var st = window.pageYOffset;

      if (st < lastScrollTop) {
        badge.classList.remove('is-active');
      } else {
        badge.classList.add('is-active');
      }

      lastScrollTop = st <= 0 ? 0 : st;

      if (timer) {
        clearTimeout(timer);
      }

      timer = setTimeout(function () {
        badge.classList.remove('is-active');
      }, 700);
    });
  }
};
/**
 * 카드 선택 시 스크롤 고정
 */


var common_modalInRadioScrollBlock = function modalInRadioScrollBlock() {
  event_handler.on(document, 'click.radio-block', function (e) {
    // 모달팝업 속에있는 카드만
    if (!e.target.classList.contains('c-card__label')) return;
    var inModalBody = e.target.closest('.c-modal__body .c-card');

    if (inModalBody) {
      var chkRadio = inModalBody.querySelector('.c-radio');

      if (chkRadio) {
        var cModalPosition = document.querySelector('.c-modal__body').getBoundingClientRect();
        var chkRadioHeight = chkRadio.getBoundingClientRect().height; // * 2;

        var spacing = 2;
        var cardYPosition = inModalBody.getBoundingClientRect().top;

        if (cModalPosition.top >= cardYPosition) {
          var diffY = cModalPosition.top - cardYPosition + spacing;
          chkRadio.style.top = "".concat(diffY, "px");
        } else if (Math.floor(cardYPosition + chkRadioHeight) >= Math.floor(cModalPosition.bottom)) {
          chkRadio.style.top = "".concat(0, "px");
        } else {
          chkRadio.style.top = '';
        }
      }
    }
  });
};
/**
 * p5 플러그인이 document에 wheel 이벤트를 등록한다.
 * 무슨이유인지는 모르겠지만 이 이벤트때문에 input number 에서 wheel 작동 시
 * 화면 스크롤이 block되고 인풋의 숫자가 카운팅된다.
 * p5 에서 등록한 이벤트를 삭제할 수 없어서 (혹시 내가 모르고 있을지도..)
 * number 인풋을 찾아 wheel 이벤트 발생 시 포커스 아웃 되도록 처리한다.
 * 이 증상은 p5 플러그인 사용한 페이지(게임)에서 생겨나지만
 * 게임 팝업이 어떤 페이지에서부터 생성되어있는지 알 수 없기에 전역으로 처리한다.
 */


var common_blockWheel = function blockWheel() {
  var elements = document.querySelectorAll('input[type="number"]');
  elements.forEach(function (el) {
    if (initializeCheck(el)) {
      setInitialize(el);
      event_handler.on(el, 'wheel', function (event) {
        document.activeElement.blur();
      });
    }
  });
}; // 이곳에서....


var initFunc = function initFunc() {
  datepickerValueBinder(); // bottom sheet

  common_bsCompareBottomSheet(); // 공통 헤더 플로팅 처리

  common_headerFixed(); // gnb

  common_gnb(); // fab (접근성 추가)

  common_a11yFab(); // active menu

  selectedCurrentMenu('.ly-header__nav');
  selectedCurrentMenu('.ly-allmenu__wrap'); // 메인 쪽 헤더 고정

  common_mainHeaderFixed(); // 모달내 요금제 비교함 뱃지 처리

  badgeOpenner(); // 모달 스크롤 컨테이너 속 라디오 선택 시 스크롤 막기

  common_modalInRadioScrollBlock();

};

var initMenu = function initMenu () {
  selectedCurrentMenu('.ly-header__nav');
  selectedCurrentMenu('.ly-allmenu__wrap'); // 메인 쪽 헤더 고정
}
/**
 * initialize 함수를 통해서 재초기화 필요가 있는 함수들
 * 이 함수에 추가되는 함수들은 이벤트리스너 중복으로 등록되는 사항에 주의
 */


var common_initialize = function initialize() {
  common_clickToScroll(); // 사은품 스크롤 영역

  common_presentsRow(); // 인풋 포커스 활성화 시 레이블 애니메이션 처리

  formInteraction(); // 셀렉트 폼에서 값 선택 후 레이블 및 값 유지 처리

  selectInteraction(); // 인풋 그룹 포커스 활설화 시 아웃라인 처리

  inputGroupInteraction(); // filter 스크롤 영역 변경

  common_scrollChange(); // 검색 인풋 활성화 시 버튼 노출

  common_searchBoxAction(); // 탭 변경 시 스크롤 초기화

  common_tabChangeToScroll(); // wheel block -> number input

  common_blockWheel(); // counter

  common_counter();

  common_gotoScrollY();
};

var commonFunc = {
  initFunc: initFunc,
  initialize: common_initialize,
  datepickerValueBinder: datepickerValueBinder
};
/* harmony default export */ var common = (commonFunc);
// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/ui/loading-spinner.js
function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function _createClass(Constructor, protoProps, staticProps) { if (protoProps) _defineProperties(Constructor.prototype, protoProps); if (staticProps) _defineProperties(Constructor, staticProps); Object.defineProperty(Constructor, "prototype", { writable: false }); return Constructor; }

 // eslint-disable-next-line no-unused-vars

var VERSION = '1.0.0';
var NAME = 'ui.loading-spinner';

var loading_spinner_LoadingSpinner = /*#__PURE__*/function () {
  function LoadingSpinner() {
    _classCallCheck(this, LoadingSpinner);

    this._showCount = 0;
    this._template = toHTML("\n    <div class=\"c-loader--wrap\">\n      <div class=\"c-loader\">\n        <div class=\"c-loader--circle\"></div>\n      </div>\n    </div>\n    ");
  }

  _createClass(LoadingSpinner, [{
    key: "show",
    value: function show() {
      if (this._showCount <= 0) {
        document.body.appendChild(this._template); // eslint-disable-next-line no-unused-vars
        var cache = this._template.offsetHeight;

        this._template.classList.add('is-active');
      }

      this._showCount++;
    }
  }, {
    key: "hide",
    value: function hide() {
      var isForceHide = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;
      var hasParent = this._template.parentNode;

      if (isForceHide) {
        if (hasParent) {
          this._template.classList.remove('is-active');

          document.body.removeChild(this._template);
          this._showCount = 0;
        }
      } else {
        this._showCount = Math.max(this._showCount - 1, 0);

        if (this._showCount < 1) {
          if (hasParent) {
            this._template.classList.remove('is-active');

            document.body.removeChild(this._template);
          }
        }
      }
    }
  }], [{
    key: "NAME",
    get: function get() {
      return NAME;
    }
  }]);

  return LoadingSpinner;
}();



//로딩 2번째 샘플
var loading_spinner_LoadingSpinner2 = /*#__PURE__*/function () {
      function LoadingSpinner2() {
        _classCallCheck(this, LoadingSpinner2);

        this._showCount = 0;
        this._template = toHTML("\n    <div class=\"c-loader--wrap--type2\">\n    <div class=\"c-loader\">\n    <div class=\"c-loader--circle\"></div>\n    </div>\n    <div class=\"c-loader-text\">\n    <strong>개통이 진행 중 입니다.</strong>\n    <p>잠시만 기다려 주세요.</p>\n    </div>\n    </div>\n");
      }

      _createClass(LoadingSpinner2, [{
        key: "show",
        value: function show() {
          if (this._showCount <= 0) {
            document.body.appendChild(this._template); // eslint-disable-next-line no-unused-vars

            var cache = this._template.offsetHeight;

            this._template.classList.add('is-active');
          }

          this._showCount++;
        }
      }, {
        key: "hide",
        value: function hide() {
          var isForceHide = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;
          var hasParent = this._template.parentNode;

          if (isForceHide) {
            if (hasParent) {
              this._template.classList.remove('is-active');

              document.body.removeChild(this._template);
              this._showCount = 0;
            }
          } else {
            this._showCount = Math.max(this._showCount - 1, 0);

            if (this._showCount < 1) {
              if (hasParent) {
                this._template.classList.remove('is-active');

                document.body.removeChild(this._template);
              }
            }
          }
        }
      }], [{
        key: "NAME",
        get: function get() {
          return NAME;
        }
      }]);

      return LoadingSpinner2;
    }();
//로딩 2번째 샘플 끝


/* harmony default export */
var loading_spinner = (new loading_spinner_LoadingSpinner());
//로딩 2번재 샘플
var loading_spinner2 = (new loading_spinner_LoadingSpinner2());
// EXTERNAL MODULE: ./node_modules/core-js/modules/es.object.get-prototype-of.js
var es_object_get_prototype_of = __webpack_require__(43);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.reflect.construct.js
var es_reflect_construct = __webpack_require__(44);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.reflect.get.js
var es_reflect_get = __webpack_require__(45);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.map.js
var es_map = __webpack_require__(183);

// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/_dependencyvendor/data.js








/**
 * --------------------------------------------------------------------------
 * Bootstrap (v5.1.3): dom/data.js
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/main/LICENSE)
 * --------------------------------------------------------------------------
 */

/**
 * Constants
 */
var elementMap = new Map();
/* harmony default export */ var data = ({
  set: function set(element, key, instance) {
    if (!elementMap.has(element)) {
      elementMap.set(element, new Map());
    }

    var instanceMap = elementMap.get(element); // make it clear we only want one instance per element
    // can be removed later when multiple key/instances are fine to be used

    if (!instanceMap.has(key) && instanceMap.size !== 0) {
      // eslint-disable-next-line no-console
      console.error("Bootstrap doesn't allow more than one instance per element. Bound instance: ".concat(Array.from(instanceMap.keys())[0], "."));
      return;
    }

    instanceMap.set(key, instance);
  },
  get: function get(element, key) {
    if (elementMap.has(element)) {
      return elementMap.get(element).get(key) || null;
    }

    return null;
  },
  getAll: function getAll(key) {
    var instances = [];
    elementMap.forEach(function (value, _key) {
      value.forEach(function (value2, _key2) {
        if (_key2 === key) {
          instances.push(value2);
        }
      });
    });
    return instances;
  },
  remove: function remove(element, key) {
    if (!elementMap.has(element)) {
      return;
    }

    var instanceMap = elementMap.get(element);
    instanceMap.delete(key); // free up element references if there are no instances left for an element

    if (instanceMap.size === 0) {
      elementMap.delete(element);
    }
  }
});
// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/ui/ui-base.js




function ui_base_classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function ui_base_defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function ui_base_createClass(Constructor, protoProps, staticProps) { if (protoProps) ui_base_defineProperties(Constructor.prototype, protoProps); if (staticProps) ui_base_defineProperties(Constructor, staticProps); Object.defineProperty(Constructor, "prototype", { writable: false }); return Constructor; }



var ui_base_UI = /*#__PURE__*/function () {
  function UI(element, config) {
    ui_base_classCallCheck(this, UI);

    if (!element) {
      this._throwError('option.wrapper is required.');
    }

    this._id = '';
    this._element = element.jquery ? element[0] : element;

    this._genID(config.id);

    data.set(this._element, this.constructor.NAME, this);
  }

  ui_base_createClass(UI, [{
    key: "init",
    value:
    /**
     * 컴포넌트 초기화
     */
    function init() {// override
    }
    /**
     * 컴포넌트 업데이트
     */

  }, {
    key: "update",
    value: function update() {// override
    }
    /**
     * 컴포넌트 삭제
     */

  }, {
    key: "destroy",
    value: function destroy() {
      // override
      data.remove(this._element, this.constructor.NAME);
      this._element = null;
    }
    /**
     * 현재 UI의 id 반환
     * @returns id
     */

  }, {
    key: "getID",
    value: function getID() {
      // 부모 클래스에서 구현
      return this._id;
    }
    /**
     * UI의 아이디를 생성한다.
     * @param {string} name 컴포넌트 이름
     * @param {string} id 컴포넌트 아이디
     */

  }, {
    key: "_genID",
    value: function _genID(id) {
      this._id = id || "".concat(this.constructor.NAME, "-").concat(Math.random().toString(36).substr(2, 9));
    }
  }, {
    key: "_getRandomSerial",
    value: function _getRandomSerial() {
      return "id_".concat(Math.random().toString(36).substr(2, 9));
    }
  }, {
    key: "_eventName",
    value: function _eventName(eventName) {
      return "".concat(eventName, ".").concat(this.constructor.NAME);
    }
  }, {
    key: "_throwError",
    value: function _throwError(message) {
      throw new Error("say! --> ".concat(this.constructor.NAME, ": ").concat(message));
    }
  }, {
    key: "_warn",
    value: function _warn(message) {
      console.warn("".concat(this.constructor.NAME, ": ").concat(message));
    }
  }], [{
    key: "getInstance",
    value: function getInstance(element) {
      return data.get(element, this.NAME);
    }
  }, {
    key: "getInstances",
    value: function getInstances() {
      return data.getAll(this.NAME);
    }
  }]);

  return UI;
}();

/* harmony default export */ var ui_base = (ui_base_UI);
// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/ui/tab.js
function tab_typeof(obj) { "@babel/helpers - typeof"; return tab_typeof = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (obj) { return typeof obj; } : function (obj) { return obj && "function" == typeof Symbol && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }, tab_typeof(obj); }



















function tab_classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function tab_defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function tab_createClass(Constructor, protoProps, staticProps) { if (protoProps) tab_defineProperties(Constructor.prototype, protoProps); if (staticProps) tab_defineProperties(Constructor, staticProps); Object.defineProperty(Constructor, "prototype", { writable: false }); return Constructor; }

function _get() { if (typeof Reflect !== "undefined" && Reflect.get) { _get = Reflect.get; } else { _get = function _get(target, property, receiver) { var base = _superPropBase(target, property); if (!base) return; var desc = Object.getOwnPropertyDescriptor(base, property); if (desc.get) { return desc.get.call(arguments.length < 3 ? target : receiver); } return desc.value; }; } return _get.apply(this, arguments); }

function _superPropBase(object, property) { while (!Object.prototype.hasOwnProperty.call(object, property)) { object = _getPrototypeOf(object); if (object === null) break; } return object; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); Object.defineProperty(subClass, "prototype", { writable: false }); if (superClass) _setPrototypeOf(subClass, superClass); }

function _setPrototypeOf(o, p) { _setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return _setPrototypeOf(o, p); }

function _createSuper(Derived) { var hasNativeReflectConstruct = _isNativeReflectConstruct(); return function _createSuperInternal() { var Super = _getPrototypeOf(Derived), result; if (hasNativeReflectConstruct) { var NewTarget = _getPrototypeOf(this).constructor; result = Reflect.construct(Super, arguments, NewTarget); } else { result = Super.apply(this, arguments); } return _possibleConstructorReturn(this, result); }; }

function _possibleConstructorReturn(self, call) { if (call && (tab_typeof(call) === "object" || typeof call === "function")) { return call; } else if (call !== void 0) { throw new TypeError("Derived constructors may only return object or undefined"); } return _assertThisInitialized(self); }

function _assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return self; }

function _isNativeReflectConstruct() { if (typeof Reflect === "undefined" || !Reflect.construct) return false; if (Reflect.construct.sham) return false; if (typeof Proxy === "function") return true; try { Boolean.prototype.valueOf.call(Reflect.construct(Boolean, [], function () {})); return true; } catch (e) { return false; } }

function _getPrototypeOf(o) { _getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return _getPrototypeOf(o); }

function ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); enumerableOnly && (symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; })), keys.push.apply(keys, symbols); } return keys; }

function _objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = null != arguments[i] ? arguments[i] : {}; i % 2 ? ownKeys(Object(source), !0).forEach(function (key) { _defineProperty(target, key, source[key]); }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)) : ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } return target; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }



 // eslint-disable-next-line no-unused-vars

var tab_VERSION = '2.0.0';
var tab_NAME = 'ui.tab';
var ROLE_TAB_LIST = '[role="tablist"]';
var ROLE_TAB = '[role="tab"]';
var ARIA_CONTROLS = 'aria-controls';
var dataAttrConfig = {
  activeClass: 'active',
  active: 0,
  contentDisplay: null,
  direction: 'horizontal'
};

var defaultConfig = _objectSpread(_objectSpread({}, dataAttrConfig), {}, {
  delegate: {
    selectFilter: function selectFilter(els, el, className) {
      return els[0].closest(className) === el.closest(className);
    }
  }
});

var tab_Tab = /*#__PURE__*/function (_UI) {
  _inherits(Tab, _UI);

  var _super = _createSuper(Tab);

  function Tab(element) {
    var _this;

    var config = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

    tab_classCallCheck(this, Tab);

    _this = _super.call(this, element, config);

    _this._setupConfog(config);

    _this._activeIndex = 0;
    _this._tablist = null;
    _this._current = {
      tab: null,
      content: null
    };
    _this._before = {
      tab: null,
      content: null
    };
    return _this;
  }

  tab_createClass(Tab, [{
    key: "init",
    value: function init() {
      this._initVars();

      this._initEvents();

      this.active(this._config.active);
    }
  }, {
    key: "activeIndex",
    get: function get() {
      return this._activeIndex;
    }
    /**
     * Tab Show
     * @param {number|string|object} target
     */

  }, {
    key: "active",
    value: function active(target) {
      // 인덱스
      if (!isNaN(target)) {
        var tablist = this._element.querySelector(ROLE_TAB_LIST);

        var tabs = tablist.querySelectorAll(ROLE_TAB);

        if (typeof target === 'number') {
            this._activeIndex = target;
            this._current = {
              tab: tabs[target],
              content: this._getContent(tabs[target])
            };
          } else {
            // target이 HTMLElement라면, 그 인덱스를 찾아야 함
            tabs.forEach((element, index) => {
              if (element === target) {
                this._activeIndex = index;
                this._current = {
                  tab: element,
                  content: this._getContent(element)
                };
              }
            });
          }

        this._current = {
          tab: tabs[target],
          content: this._getContent(tabs[target])
        };
      } else {
        // 셀렉터 스트링
        if (typeof target === 'string') {
          var tab = document.querySelector(target);
          this._current = {
            tab: tab,
            content: this._getContent(tab)
          };
        } else {
          // 엘리먼트
          var _tab = target.jquery ? target[0] : target;

          this._current = {
            tab: _tab,
            content: this._getContent(_tab)
          };
        }
      }

      this._active();
    }
  }, {
    key: "destroy",
    value: function destroy() {
      this._removeEvents();

      this._removeVars();
    }
  }, {
    key: "_setupConfog",
    value: function _setupConfog(config) {
      this._config = _objectSpread(_objectSpread(_objectSpread(_objectSpread({}, defaultConfig), Tab.GLOBAL_CONFIG), config), dataSetToObject(this._element, dataAttrConfig, 'tab'));
    }
  }, {
    key: "_initVars",
    value: function _initVars() {
      this._tablist = this._element.querySelector(ROLE_TAB_LIST);
    }
  }, {
    key: "_initEvents",
    value: function _initEvents() {
      var _this2 = this;

      // 클릭 이벤트
      event_handler.on(this._tablist, _get(_getPrototypeOf(Tab.prototype), "_eventName", this).call(this, 'click'), function (event) {
        if (!event.target.tagName.match(/^A$|AREA|INPUT|TEXTAREA|SELECT|BUTTON|LABEL/gim)) {
          event.preventDefault();
        }

        var target = event.target.closest(ROLE_TAB);

        if (target) {
          _this2._current = {
            tab: target,
            content: _this2._getContent(target)
          };

          _this2._active();
        }
      }); // 키보드 방향키 이벤트 바인딩

      event_handler.on(this._tablist, _get(_getPrototypeOf(Tab.prototype), "_eventName", this).call(this, 'keydown'), function (event) {
        var direction = _this2._config.direction;

        switch (event.key) {
          case 'ArrowUp':
          case 'Up':
            {
              if (direction === 'vertical') {
                event.preventDefault();

                _this2._prev();
              }

              break;
            }

          case 'ArrowDown':
          case 'Down':
            {
              if (direction === 'vertical') {
                event.preventDefault();

                _this2._next();
              }

              break;
            }

          case 'ArrowRight':
          case 'Right':
            {
              if (direction === 'horizontal') {
                event.preventDefault();

                _this2._next();
              }

              break;
            }

          case 'ArrowLeft':
          case 'Left':
            {
              if (direction === 'horizontal') {
                event.preventDefault();

                _this2._prev();
              }

              break;
            }

          default:
            {// ...
            }
        }

        _this2.active(_this2._activeIndex);

        _this2._current.tab.focus();
      });
    }
  }, {
    key: "_removeEvents",
    value: function _removeEvents() {
      event_handler.off(this._tablist, _get(_getPrototypeOf(Tab.prototype), "_eventName", this).call(this, 'click'));
      event_handler.off(this._tablist, _get(_getPrototypeOf(Tab.prototype), "_eventName", this).call(this, 'keydown'));
    }
  }, {
    key: "_removeVars",
    value: function _removeVars() {
      this._tablist = null;
      this._active = null;
      this._before = null;
      this._active = -1;
    }
    /**
     * 다음 탭 활성화
     */

  }, {
    key: "_next",
    value: function _next() {
      this._activeIndex++;

      if (this._activeIndex >= this._getTotal()) {
        this._activeIndex = 0;
      }
    }
    /**
     * 이전 탭 활성화
     */

  }, {
    key: "_prev",
    value: function _prev() {
      this._activeIndex--;

      if (this._activeIndex < 0) {
        this._activeIndex = this._getTotal() - 1;
      }
    }
    /**
     * 탭 활성화
     */

  }, {
    key: "_active",
    value: function _active() {
      var _this$_config = this._config,
          activeClass = _this$_config.activeClass,
          contentDisplay = _this$_config.contentDisplay;
      var _this$_current = this._current,
          tab = _this$_current.tab,
          content = _this$_current.content;

      this._deactive();

      tab.classList.add(activeClass);

      if (contentDisplay) {
        content.classList.add(contentDisplay);
      } else {
        content.style.display = 'block';
      }

      this._activeIndex = getIndex(tab);
      event_handler.trigger(this._element, Tab.EVENT.CHANGE, {
        current: this._current,
        before: this._before
      });
      this._before = {
        tab: tab,
        content: content
      };

      this._aria(this._current);
    }
    /**
     * 이전 탭 비활성화
     */

  }, {
    key: "_deactive",
    value: function _deactive() {
      var _this$_config2 = this._config,
          activeClass = _this$_config2.activeClass,
          contentDisplay = _this$_config2.contentDisplay;
      var _this$_before = this._before,
          tab = _this$_before.tab,
          content = _this$_before.content;

      if (tab) {
        tab.classList.remove(activeClass);

        if (contentDisplay) {
          content.classList.remove(contentDisplay);
        } else {
          content.style.display = 'none';
        }

        this._aria(this._before, false);
      }
    }
    /**
     * 웹 접근성 aria 속성 및 tabindex 설정
     * @param {*} target
     * @param {*} isActive
     */

  }, {
    key: "_aria",
    value: function _aria(target) {
      var isActive = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : true;
      var tab = target.tab,
          content = target.content;
      var isSelected = isActive ? true : false;
      var isHidden = isActive ? false : true;
      var tabIndex = isActive ? 0 : -1;
      tab.setAttribute('tabIndex', tabIndex);
      tab.setAttribute('aria-selected', isSelected);
      content.setAttribute('aria-hidden', isHidden); // content.setAttribute('tabIndex', tabIndex);
    }
    /**
     * tab header(aria-controls)에 선언 된 컨텐츠 찾아서 반환
     * @param {*} target
     * @returns
     */

  }, {
    key: "_getContent",
    value: function _getContent(target) {
      if (!target) _get(_getPrototypeOf(Tab.prototype), "_throwError", this).call(this, "[".concat(target, "] not found!"));
      var contentName = target.getAttribute(ARIA_CONTROLS);
      var content = document.querySelector("#".concat(contentName));

      if (!content) {
        _get(_getPrototypeOf(Tab.prototype), "_throwError", this).call(this, "[".concat(contentName, "] does not match any content element! "));
      }

      return content;
    }
    /**
     * 지금 현재 탭의 갯수 반환
     * @returns
     */

  }, {
    key: "_getTotal",
    value: function _getTotal() {
      var tabs = this._tablist.querySelectorAll(ROLE_TAB);

      return tabs.length;
    }
  }], [{
    key: "EVENT",
    get: function get() {
      return {
        CHANGE: "".concat(tab_NAME, ".change")
      };
    }
  }, {
    key: "NAME",
    get: function get() {
      return tab_NAME;
    }
  }]);

  return Tab;
}(ui_base);

_defineProperty(tab_Tab, "GLOBAL_CONFIG", {});

/* harmony default export */ var ui_tab = (tab_Tab);
// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/ui/accordion.js
function accordion_typeof(obj) { "@babel/helpers - typeof"; return accordion_typeof = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (obj) { return typeof obj; } : function (obj) { return obj && "function" == typeof Symbol && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }, accordion_typeof(obj); }

function accordion_toConsumableArray(arr) { return accordion_arrayWithoutHoles(arr) || accordion_iterableToArray(arr) || accordion_unsupportedIterableToArray(arr) || accordion_nonIterableSpread(); }

function accordion_nonIterableSpread() { throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }

function accordion_unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return accordion_arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return accordion_arrayLikeToArray(o, minLen); }

function accordion_iterableToArray(iter) { if (typeof Symbol !== "undefined" && iter[Symbol.iterator] != null || iter["@@iterator"] != null) return Array.from(iter); }

function accordion_arrayWithoutHoles(arr) { if (Array.isArray(arr)) return accordion_arrayLikeToArray(arr); }

function accordion_arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) { arr2[i] = arr[i]; } return arr2; }























function accordion_classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function accordion_defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function accordion_createClass(Constructor, protoProps, staticProps) { if (protoProps) accordion_defineProperties(Constructor.prototype, protoProps); if (staticProps) accordion_defineProperties(Constructor, staticProps); Object.defineProperty(Constructor, "prototype", { writable: false }); return Constructor; }

function accordion_get() { if (typeof Reflect !== "undefined" && Reflect.get) { accordion_get = Reflect.get; } else { accordion_get = function _get(target, property, receiver) { var base = accordion_superPropBase(target, property); if (!base) return; var desc = Object.getOwnPropertyDescriptor(base, property); if (desc.get) { return desc.get.call(arguments.length < 3 ? target : receiver); } return desc.value; }; } return accordion_get.apply(this, arguments); }

function accordion_superPropBase(object, property) { while (!Object.prototype.hasOwnProperty.call(object, property)) { object = accordion_getPrototypeOf(object); if (object === null) break; } return object; }

function accordion_inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); Object.defineProperty(subClass, "prototype", { writable: false }); if (superClass) accordion_setPrototypeOf(subClass, superClass); }

function accordion_setPrototypeOf(o, p) { accordion_setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return accordion_setPrototypeOf(o, p); }

function accordion_createSuper(Derived) { var hasNativeReflectConstruct = accordion_isNativeReflectConstruct(); return function _createSuperInternal() { var Super = accordion_getPrototypeOf(Derived), result; if (hasNativeReflectConstruct) { var NewTarget = accordion_getPrototypeOf(this).constructor; result = Reflect.construct(Super, arguments, NewTarget); } else { result = Super.apply(this, arguments); } return accordion_possibleConstructorReturn(this, result); }; }

function accordion_possibleConstructorReturn(self, call) { if (call && (accordion_typeof(call) === "object" || typeof call === "function")) { return call; } else if (call !== void 0) { throw new TypeError("Derived constructors may only return object or undefined"); } return accordion_assertThisInitialized(self); }

function accordion_assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return self; }

function accordion_isNativeReflectConstruct() { if (typeof Reflect === "undefined" || !Reflect.construct) return false; if (Reflect.construct.sham) return false; if (typeof Proxy === "function") return true; try { Boolean.prototype.valueOf.call(Reflect.construct(Boolean, [], function () {})); return true; } catch (e) { return false; } }

function accordion_getPrototypeOf(o) { accordion_getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return accordion_getPrototypeOf(o); }

function accordion_ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); enumerableOnly && (symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; })), keys.push.apply(keys, symbols); } return keys; }

function accordion_objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = null != arguments[i] ? arguments[i] : {}; i % 2 ? accordion_ownKeys(Object(source), !0).forEach(function (key) { accordion_defineProperty(target, key, source[key]); }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)) : accordion_ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } return target; }

function accordion_defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }


 // eslint-disable-next-line no-unused-vars

 // eslint-disable-next-line no-unused-vars

var accordion_VERSION = '2.0.0';
var accordion_NAME = 'ui.accordion';
var accordion_ARIA_CONTROLS = 'aria-controls';
var accordion_dataAttrConfig = {
  default: -1,
  defaults: -1,
  activeClass: 'active',
  toggle: true,
  onlyOne: false,
  animation: true
};

var accordion_defaultConfig = accordion_objectSpread(accordion_objectSpread({}, accordion_dataAttrConfig), {}, {
  stateClass: {
    expanding: 'expanding',
    expand: 'expand',
    expanded: 'expanded'
  }
});

var accordion_Accordion = /*#__PURE__*/function (_UI) {
  accordion_inherits(Accordion, _UI);

  var _super = accordion_createSuper(Accordion);

  function Accordion(element) {
    var _this;

    var config = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

    accordion_classCallCheck(this, Accordion);

    _this = _super.call(this, element, config);

    _this._setupConfog(config);

    _this._animating = false;
    _this._hasActive = false;
    _this._current = {
      tab: null,
      content: null
    };
    _this._before = {
      tab: null,
      content: null
    };
    return _this;
  }

  accordion_createClass(Accordion, [{
    key: "init",
    value: function init() {
      this._initEvents();

      this._current = null;
      this._before = null;

      this._defaultActive();
    }
  }, {
    key: "_defaultActive",
    value: function _defaultActive() {
      var _this2 = this;

      if (this._config.default !== -1) {
        this.open(this._config.default);
        return;
      }

      if (this._config.defaults !== -1) {
        var headerIndexList = this._config.defaults.split(',');

        accordion_toConsumableArray(headerIndexList).forEach(function (n) {
          _this2.open(n);

          _this2._animating = false;
        });

        return;
      }

      var headers = this._element.querySelectorAll("[".concat(accordion_ARIA_CONTROLS, "]"));

      headers.forEach(function (el) {
        if (el.classList.contains(_this2._config.activeClass)) {
          _this2.open(el);

          _this2._animating = false;
        }
      });
    }
  }, {
    key: "open",
    value: function open(target) {
      this._selectCurrent(target);

      this._open();
    }
  }, {
    key: "openAll",
    value: function openAll() {
      var _this3 = this;

      // todo acc in acc 처리해야 함
      var headers = this._element.querySelectorAll("[".concat(accordion_ARIA_CONTROLS, "]"));

      headers.forEach(function (header) {
        _this3.open(header);

        _this3._animating = false;
      });
    }
  }, {
    key: "close",
    value: function close(target) {
      this._selectCurrent(target);

      this._close();
    }
  }, {
    key: "closeAll",
    value: function closeAll() {
      var _this4 = this;

      // todo acc in acc 처리해야 함
      var headers = this._element.querySelectorAll("[".concat(accordion_ARIA_CONTROLS, "]"));

      headers.forEach(function (header) {
        _this4._close({
          header: header,
          content: _this4._getContent(header)
        });

        _this4._animating = false;
      });
    }
  }, {
    key: "destroy",
    value: function destroy() {
      this._removeEvents();
    }
  }, {
    key: "_setupConfog",
    value: function _setupConfog(config) {
      this._config = accordion_objectSpread(accordion_objectSpread(accordion_objectSpread(accordion_objectSpread({}, accordion_defaultConfig), Accordion.GLOBAL_CONFIG), config), dataSetToObject(this._element, accordion_dataAttrConfig, 'accordion'));
    }
  }, {
    key: "_initEvents",
    value: function _initEvents() {
      var _this5 = this;

      event_handler.on(this._element, accordion_get(accordion_getPrototypeOf(Accordion.prototype), "_eventName", this).call(this, 'click'), function (event) {
        if (!event.target.tagName.match(/^A$|AREA|INPUT|TEXTAREA|SELECT|BUTTON|LABEL/gim)) {
          event.preventDefault();
        }

        var _this5$_config = _this5._config,
            toggle = _this5$_config.toggle,
            activeClass = _this5$_config.activeClass;
        var target = event.target.closest("[".concat(accordion_ARIA_CONTROLS, "]"));

        if (target) {
          _this5._current = {
            header: target,
            content: _this5._getContent(target)
          };

          if (toggle) {
            if (_this5._current.header.classList.contains(activeClass)) {
              _this5._close(_this5._current);
            } else {
              _this5._open();
            }
          } else {
            _this5._open();
          }
        }
      });
    }
  }, {
    key: "_removeEvents",
    value: function _removeEvents() {
      event_handler.off(this._element, accordion_get(accordion_getPrototypeOf(Accordion.prototype), "_eventName", this).call(this, 'click'));
    }
  }, {
    key: "_open",
    value: function _open() {
      var _this6 = this;

      var _this$_config = this._config,
          activeClass = _this$_config.activeClass,
          onlyOne = _this$_config.onlyOne,
          animation = _this$_config.animation,
          stateClass = _this$_config.stateClass;
      var possibleAnimation = isVisible(this._element);
      if (this._animating === true && animation === true) return;
      var _this$_current = this._current,
          header = _this$_current.header,
          content = _this$_current.content;
      header.classList.add(activeClass);

      this._dispatch(Accordion.EVENT.OPEN, {
        current: this._current
      });

      if (animation && possibleAnimation) {
        this._animating = true;
        content.classList.add(stateClass.expanding);
        content.classList.remove(stateClass.expand);
        content.style.height = "".concat(content.scrollHeight, "px");
        event_handler.one(content, 'transitionend', function () {
          content.classList.remove(stateClass.expanding);
          content.classList.add(stateClass.expand);
          content.classList.add(stateClass.expanded);
          content.style.height = '';
          _this6._animating = false;

          _this6._dispatch(Accordion.EVENT.OPEND, {
            current: _this6._current
          });
        });
      } else {
        content.classList.add(stateClass.expanded);
        content.classList.add(stateClass.expand);
        header.classList.add(activeClass);
      }

      if (onlyOne === true) {
        if (this._before && this._before.header !== this._current.header) {
          this._animating = false;

          this._close();
        }
      }

      this._before = {
        header: header,
        content: content
      };

      this._aria(this._current, true);
    }
  }, {
    key: "_close",
    value: function _close(target) {
      var _this7 = this;

      var _this$_config2 = this._config,
          activeClass = _this$_config2.activeClass,
          animation = _this$_config2.animation,
          stateClass = _this$_config2.stateClass;
      if (this._animating === true && animation === true) return;
      var closeTarget = !!target ? target : this._before;
      if (!closeTarget.header) return;
      var header = closeTarget.header,
          content = closeTarget.content;
      header.classList.remove(activeClass);

      this._dispatch(Accordion.EVENT.CLOSE, {
        current: closeTarget
      });

      this._aria(closeTarget, false);

      if (animation) {
        this._animating = true;
        content.style.height = "".concat(content.getBoundingClientRect().height, "px");
        content.heightCache = content.offsetHeight;
        content.style.height = "";
        content.classList.add(stateClass.expanding);
        content.classList.remove(stateClass.expand);
        content.classList.remove(stateClass.expanded);
        event_handler.one(content, 'transitionend', function () {
          content.classList.remove(stateClass.expanding);
          content.classList.add(stateClass.expand);
          _this7._animating = false;

          _this7._dispatch(Accordion.EVENT.CLOSED, {
            current: closeTarget
          });
        });
        return;
      } else {
        content.classList.remove(stateClass.expanding);
        content.classList.add(stateClass.expand);
      }
    }
  }, {
    key: "_selectCurrent",
    value: function _selectCurrent(target) {
      // 인덱스
      if (!isNaN(target)) {
        var accHeaders = this._element.querySelectorAll("[".concat(accordion_ARIA_CONTROLS, "]"));

        this._current = {
          header: accHeaders[target],
          content: this._getContent(accHeaders[target])
        };
      } else {
        // 셀렉터 스트링
        if (typeof target === 'string') {
          var header = this._element.querySelector(target);

          console.log('열루');
          this._current = {
            header: header,
            content: this._getContent(header)
          };
        } else {
          // 엘리먼트
          var tab = target.jquery ? target[0] : target;
          this._current = {
            header: tab,
            content: this._getContent(tab)
          };
        }
      }
    }
    /**
     * 웹 접근성 aria 속성 및 tabindex 설정
     * @param {*} target
     * @param {*} isActive
     */

  }, {
    key: "_aria",
    value: function _aria(target) {
      var isActive = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : true;
      var toggle = this._config.toggle;
      var header = target.header,
          content = target.content;
      var isSelected = isActive ? true : false;
      var isHidden = isActive ? false : true; // const tabIndex = isActive ? 0 : -1;
      // header.setAttribute('tabIndex', tabIndex);

      header.setAttribute('aria-expanded', isSelected);

      if (toggle === false) {
        header.setAttribute('aria-disabled', isActive);
      }

      content.setAttribute('aria-hidden', isHidden); // content.setAttribute('tabIndex', tabIndex);
    }
    /**
     * acc header(aria-controls)에 선언 된 컨텐츠 찾아서 반환
     * @param {*} target
     * @returns
     */

  }, {
    key: "_getContent",
    value: function _getContent(target) {
      if (!target) accordion_get(accordion_getPrototypeOf(Accordion.prototype), "_throwError", this).call(this, "[".concat(target, "] not found!"));
      var contentName = target.getAttribute(accordion_ARIA_CONTROLS);
      var content = document.querySelector("#".concat(contentName));

      if (!content) {
        accordion_get(accordion_getPrototypeOf(Accordion.prototype), "_throwError", this).call(this, "[".concat(contentName, "] does not match any content element! "));
      }

      return content;
    }
  }, {
    key: "_dispatch",
    value: function _dispatch(event, params) {
      event_handler.trigger(this._element, event, params);
    }
  }], [{
    key: "EVENT",
    get: function get() {
      return {
        OPEN: "".concat(accordion_NAME, ".open"),
        OPEND: "".concat(accordion_NAME, ".opened"),
        CLOSE: "".concat(accordion_NAME, ".close"),
        CLOSED: "".concat(accordion_NAME, ".closed")
      };
    }
  }, {
    key: "NAME",
    get: function get() {
      return accordion_NAME;
    }
  }, {
    key: "redraw",
    value: function redraw() {
      var insList = Accordion.getInstances();

      if (insList.length > 0) {
        insList.forEach(function (ins) {
          ins._defaultActive();
        });
      }
    }
  }]);

  return Accordion;
}(ui_base);

accordion_defineProperty(accordion_Accordion, "GLOBAL_CONFIG", {});

/* harmony default export */ var accordion = (accordion_Accordion);
// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/ui/dialog.js
function dialog_typeof(obj) { "@babel/helpers - typeof"; return dialog_typeof = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (obj) { return typeof obj; } : function (obj) { return obj && "function" == typeof Symbol && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }, dialog_typeof(obj); }

















function dialog_classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function dialog_defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function dialog_createClass(Constructor, protoProps, staticProps) { if (protoProps) dialog_defineProperties(Constructor.prototype, protoProps); if (staticProps) dialog_defineProperties(Constructor, staticProps); Object.defineProperty(Constructor, "prototype", { writable: false }); return Constructor; }

function dialog_get() { if (typeof Reflect !== "undefined" && Reflect.get) { dialog_get = Reflect.get; } else { dialog_get = function _get(target, property, receiver) { var base = dialog_superPropBase(target, property); if (!base) return; var desc = Object.getOwnPropertyDescriptor(base, property); if (desc.get) { return desc.get.call(arguments.length < 3 ? target : receiver); } return desc.value; }; } return dialog_get.apply(this, arguments); }

function dialog_superPropBase(object, property) { while (!Object.prototype.hasOwnProperty.call(object, property)) { object = dialog_getPrototypeOf(object); if (object === null) break; } return object; }

function dialog_inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); Object.defineProperty(subClass, "prototype", { writable: false }); if (superClass) dialog_setPrototypeOf(subClass, superClass); }

function dialog_setPrototypeOf(o, p) { dialog_setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return dialog_setPrototypeOf(o, p); }

function dialog_createSuper(Derived) { var hasNativeReflectConstruct = dialog_isNativeReflectConstruct(); return function _createSuperInternal() { var Super = dialog_getPrototypeOf(Derived), result; if (hasNativeReflectConstruct) { var NewTarget = dialog_getPrototypeOf(this).constructor; result = Reflect.construct(Super, arguments, NewTarget); } else { result = Super.apply(this, arguments); } return dialog_possibleConstructorReturn(this, result); }; }

function dialog_possibleConstructorReturn(self, call) { if (call && (dialog_typeof(call) === "object" || typeof call === "function")) { return call; } else if (call !== void 0) { throw new TypeError("Derived constructors may only return object or undefined"); } return dialog_assertThisInitialized(self); }

function dialog_assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return self; }

function dialog_isNativeReflectConstruct() { if (typeof Reflect === "undefined" || !Reflect.construct) return false; if (Reflect.construct.sham) return false; if (typeof Proxy === "function") return true; try { Boolean.prototype.valueOf.call(Reflect.construct(Boolean, [], function () {})); return true; } catch (e) { return false; } }

function dialog_getPrototypeOf(o) { dialog_getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return dialog_getPrototypeOf(o); }

function dialog_ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); enumerableOnly && (symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; })), keys.push.apply(keys, symbols); } return keys; }

function dialog_objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = null != arguments[i] ? arguments[i] : {}; i % 2 ? dialog_ownKeys(Object(source), !0).forEach(function (key) { dialog_defineProperty(target, key, source[key]); }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)) : dialog_ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } return target; }

function dialog_defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }




 // eslint-disable-next-line no-unused-vars

var dialog_VERSION = '1.0.1';
var dialog_NAME = 'ui.dialog';
var IDENTIFIER = {
  TRIGGER: 'data-dialog-trigger',
  CLOSE: 'data-dialog-close'
};
var dialog_dataAttrConfig = {
  bg: true,
  bgclose: true,
  destroy: false,
  animate: true,
  openClass: 'show',
  closeClass: 'hide'
};

var dialog_defaultConfig = dialog_objectSpread(dialog_objectSpread({}, dialog_dataAttrConfig), {}, {
  bgTemplate: "\n    <div style=\"\n      position: fixed; \n      width: 100%; \n      height: 100%;\n      left: 0;\n      top: 0;\n      background-color: rgba(0,0,0,0.6);\">\n    </div>"
});

var ZINDEX = {
  CONTENT: 101,
  BACKDROP: 100,
  INCREASE: 1
};

var dialog_Dialog = /*#__PURE__*/function (_UI) {
  dialog_inherits(Dialog, _UI);

  var _super = dialog_createSuper(Dialog);

  function Dialog(element) {
    var _this;

    var config = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

    dialog_classCallCheck(this, Dialog);

    _this = _super.call(this, element, config);

    _this._setupConfog(config);

    _this._bg = null;
    _this._closeButtons = null;
    _this._isOpen = false;
    _this._confineTab = null;

    _this._init();

    return _this;
  }

  dialog_createClass(Dialog, [{
    key: "open",
    value: function open() {
      this._open();
    }
  }, {
    key: "close",
    value: function close(e) {
      this._close(e);
    }
  }, {
    key: "getElement",
    value: function getElement() {
      return this._element;
    }
  }, {
    key: "_setupConfog",
    value: function _setupConfog(config) {
      this._config = dialog_objectSpread(dialog_objectSpread(dialog_objectSpread(dialog_objectSpread({}, dialog_defaultConfig), Dialog.GLOBAL_CONFIG), config), dataSetToObject(this._element, dialog_dataAttrConfig, 'dialog'));
    }
  }, {
    key: "_init",
    value: function _init() {
      this._element.setAttribute('aria-modal', 'true');
    }
  }, {
    key: "_initVars",
    value: function _initVars() {
      this._confineTab = a11y_confineTab(this._element);
      this._bg = this._config.bg ? this._createBackground() : null;
      this._closeButtons = this._element.querySelectorAll("[".concat(IDENTIFIER.CLOSE, "]"));
    }
  }, {
    key: "_initEvents",
    value: function _initEvents() {
      var _this2 = this;

      this._closeButtons.forEach(function (el) {
        event_handler.one(el, dialog_get(dialog_getPrototypeOf(Dialog.prototype), "_eventName", _this2).call(_this2, 'click'), _this2._close.bind(_this2));
      });
    }
  }, {
    key: "_removeEvents",
    value: function _removeEvents() {
      var _this3 = this;

      if (this._closeButtons) {
        this._closeButtons.forEach(function (el) {
          event_handler.off(el, dialog_get(dialog_getPrototypeOf(Dialog.prototype), "_eventName", _this3).call(_this3, 'click'));
        });
      }
    }
  }, {
    key: "_removeVars",
    value: function _removeVars() {
      var redraw = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;

      if (redraw === false) {
        this._bg = null;
      }

      this._closeButtons = null;
    }
  }, {
    key: "_createBackground",
    value: function _createBackground() {
      if (this._bg) return this._bg;
      return toHTML(this._config.bgTemplate);
    }
  }, {
    key: "_zIndexOrderIncrease",
    value: function _zIndexOrderIncrease() {
      this._element.style.zIndex = ZINDEX.CONTENT += ZINDEX.INCREASE;
      if (this._bg) this._bg.style.zIndex = ZINDEX.BACKDROP += ZINDEX.INCREASE;
    }
  }, {
    key: "_open",
    value: function _open() {
      if (this._isOpen === true) return;

      this._initVars();

      this._initEvents();

      this._zIndexOrderIncrease();

      this._showBackground();

      this._showDialog();

      this._isOpen = true;
      event_handler.trigger(this._element, Dialog.EVENT.OPEN, {
        component: this
      });

      if (Dialog.COUNT === 0) {
        event_handler.trigger(window, Dialog.EVENT.FIRST_OPEN);
      }

      Dialog.COUNT++;
    }
  }, {
    key: "_close",
    value: function _close(e) {
      if (e && e.preventDefault) {
        e.preventDefault();
      }

      this._hideDialog();

      event_handler.trigger(this._element, Dialog.EVENT.CLOSE, {
        component: this
      });

      if (this._config.animate !== true) {
        this._hideBackground();

        this._confineTab.destroy();

        this.destroy();
      }
    }
    /**
     * 다이얼로그를 닫거나 삭제처리 하지 않고
     * 다이얼로그의 컨텐츠만 새로 그려서 사용할 경우
     * 기존의 이벤트나 기타 처리를 모두 초기화 하고
     * 새로 그려진 엘리먼트에 새로 적용한다.
     */

  }, {
    key: "redraw",
    value: function redraw() {
      // 기존의 탭 시퀀스 처리를 삭제
      this._confineTab.destroy(); // 기존의 엘리먼틔 이벤트를 삭제


      this._removeEvents(); // 기존의 엘리먼트를 근거로 저장했던 변수를 삭제


      this._removeVars(true); // 새로 그려진 엘리먼트를 기준으로 변수 초기화


      this._initVars(); // 새로 그려진 엘리먼트에 이벤트 바인딩( 닫기 버튼 등.. )


      this._initEvents(); // 새로 그려진 엘리먼트의 탭 시퀀스 처리를 초기화


      var tabInfo = this._confineTab.init(); // 포커싱 처리.


      if (tabInfo && tabInfo.length > 0) tabInfo[0].focus();
    }
  }, {
    key: "destroy",
    value: function destroy() {
      this._removeEvents();

      this._removeVars();

      this._isOpen = false;

      if (this._config.destroy === true || this._config.destroy === 'true') {
        this._element.parentNode.removeChild(this._element);

        dialog_get(dialog_getPrototypeOf(Dialog.prototype), "destroy", this).call(this);
      }

      Dialog.COUNT--;

      if (Dialog.COUNT <= 0) {
        event_handler.trigger(window, Dialog.EVENT.LAST_CLOSE);
      }
    }
  }, {
    key: "_showBackground",
    value: function _showBackground() {
      if (this._bg) {
        document.body.appendChild(this._bg); // eslint-disable-next-line no-unused-vars

        var reflow = this._bg.offsetHeight;

        this._bg.classList.add('fadeIn');
      }
    }
  }, {
    key: "_showDialog",
    value: function _showDialog() {
      var _this4 = this;

      // eslint-disable-next-line no-unused-vars
      var reflow = this._element.offsetHeight;

      if (this._config.animate === true) {
        event_handler.one(this._element, 'animationend', function () {
          var focusable = _this4._confineTab.init();

          focusable[0].focus();
          event_handler.trigger(_this4._element, Dialog.EVENT.OPENED, {
            component: _this4
          });
        });
      } else {
        this._confineTab.init();
      }

      this._element.classList.add(this._config.openClass);
    }
  }, {
    key: "_hideBackground",
    value: function _hideBackground() {
      if (this._bg) document.body.removeChild(this._bg);
    }
  }, {
    key: "_hideDialog",
    value: function _hideDialog() {
      var _this5 = this;

      this._element.classList.add(this._config.closeClass);

      this._element.classList.remove(this._config.openClass);

      event_handler.one(this._element, 'animationend', function () {
        _this5._element.classList.remove(_this5._config.closeClass);

        _this5._hideBackground();

        event_handler.trigger(_this5._element, Dialog.EVENT.CLOSED, {
          component: _this5
        });

        _this5._confineTab.destroy();

        _this5.destroy();
      });
    }
  }], [{
    key: "EVENT",
    get: function get() {
      return {
        OPEN: "".concat(dialog_NAME, ".open"),
        OPENED: "".concat(dialog_NAME, ".opened"),
        CLOSE: "".concat(dialog_NAME, ".close"),
        CLOSED: "".concat(dialog_NAME, ".closed"),
        FIRST_OPEN: "".concat(dialog_NAME, ".firstOpen"),
        LAST_CLOSE: "".concat(dialog_NAME, ".laseClose")
      };
    }
    /**
     * 글로벌 설정은 처음에..
     */

  }, {
    key: "closeAll",
    value:
    /**
     * 현재 활성화 되어있는 dialog 모두 닫기
     */
    function closeAll() {
      var instances = Dialog.getInstances();

      for (var p in instances) {
        if (Object.prototype.hasOwnProperty.call(instances, p)) {
          var dialog = instances[p];
          if (dialog) dialog.close();
        }
      }
    }
  }, {
    key: "NAME",
    get: function get() {
      return dialog_NAME;
    }
  }]);

  return Dialog;
}(ui_base);

dialog_defineProperty(dialog_Dialog, "GLOBAL_CONFIG", {});

dialog_defineProperty(dialog_Dialog, "COUNT", 0);

dialog_defineProperty(dialog_Dialog, "SCROLL_POY", 0);

event_handler.on(document, "click.DIALOG_TRIGGER", function (event) {
  var el = event.target.closest("[".concat(IDENTIFIER.TRIGGER, "]"));
  if (!el) return;
  var target = el.getAttribute(IDENTIFIER.TRIGGER);

  if (target) {
    var dialogElement = getElement(target);

    if (dialogElement) {
      var dialog = dialog_Dialog.getInstance(dialogElement);

      if (dialog) {
        dialog.open();
      } else {
        new dialog_Dialog(dialogElement).open();
      }
    }
  }
});

/* harmony default export */ var ui_dialog = (dialog_Dialog);
// EXTERNAL MODULE: ./node_modules/core-js/modules/esnext.string.replace-all.js
var esnext_string_replace_all = __webpack_require__(185);

// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/util/object-util.js


/**
 * 빈 오브젝트 체크
 * @param {*} param
 * @returns
 */
var isEmptyObject = function isEmptyObject(param) {
  return Object.keys(param).length === 0 && param.constructor === Object;
};
// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/ui/dialog-hoc.js




function dialog_hoc_classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function dialog_hoc_defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function dialog_hoc_createClass(Constructor, protoProps, staticProps) { if (protoProps) dialog_hoc_defineProperties(Constructor.prototype, protoProps); if (staticProps) dialog_hoc_defineProperties(Constructor, staticProps); Object.defineProperty(Constructor, "prototype", { writable: false }); return Constructor; }

/**
 * Dialog Wrapping Class
 * static Element 기반으로 동작하는 Dialog에
 * element를 동적으로 주입하여 사용
 *
 * 한번 쓰고 버릴 Alert, Confrim popup에 사용
 */




var dialog_hoc_DialogHOC = /*#__PURE__*/function () {
  function DialogHOC() {
    var config = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {
      layout: "",
      replacer: {}
    };

    dialog_hoc_classCallCheck(this, DialogHOC);

    if (config.layout === '') {
      throw Error("\uD15C\uD50C\uB9BF\uC774 \uC124\uC815\uB418\uC9C0 \uC54A\uC558\uC2B5\uB2C8\uB2E4!");
    }

    if (isEmptyObject(config.replacer)) {
      console.warn("\uB9AC\uD50C\uB808\uC774\uC11C\uAC00 \uC124\uC815\uB418\uC9C0 \uC54A\uC73C\uBA74 \uCD9C\uB825\uB418\uB294 \uB370\uC774\uD130\uAC00 \uC62C\uBC14\uB974\uC9C0 \uC54A\uC744 \uC218 \uC788\uC2B5\uB2C8\uB2E4.");
    }

    this._instance = null;
    this._element = this._createElement(config);
    document.body.appendChild(this._element); // 다이나믹한 컨텐츠는 바로 삭제

    this._instance = new ui_dialog(this._element, {
      destroy: true
    });
    return this._instance;
  }

  dialog_hoc_createClass(DialogHOC, [{
    key: "open",
    value: function open() {
      this._instance.open();
    }
  }, {
    key: "close",
    value: function close() {
      this._instance.close();
    }
  }, {
    key: "getElement",
    value: function getElement() {
      return this._element;
    }
  }, {
    key: "_createElement",
    value: function _createElement(config) {
      var layout = config.layout,
          replacer = config.replacer;
      var source = layout;

      for (var key in replacer) {
        if (Object.prototype.hasOwnProperty.call(replacer, key)) {
          source = source.replaceAll(key, replacer[key]);
        }
      }

      return toHTML(source);
    }
  }]);

  return DialogHOC;
}();

/* harmony default export */ var dialog_hoc = (dialog_hoc_DialogHOC);
// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/ui/range-slider.js
function range_slider_typeof(obj) { "@babel/helpers - typeof"; return range_slider_typeof = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (obj) { return typeof obj; } : function (obj) { return obj && "function" == typeof Symbol && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }, range_slider_typeof(obj); }

















function range_slider_classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function range_slider_defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function range_slider_createClass(Constructor, protoProps, staticProps) { if (protoProps) range_slider_defineProperties(Constructor.prototype, protoProps); if (staticProps) range_slider_defineProperties(Constructor, staticProps); Object.defineProperty(Constructor, "prototype", { writable: false }); return Constructor; }

function range_slider_get() { if (typeof Reflect !== "undefined" && Reflect.get) { range_slider_get = Reflect.get; } else { range_slider_get = function _get(target, property, receiver) { var base = range_slider_superPropBase(target, property); if (!base) return; var desc = Object.getOwnPropertyDescriptor(base, property); if (desc.get) { return desc.get.call(arguments.length < 3 ? target : receiver); } return desc.value; }; } return range_slider_get.apply(this, arguments); }

function range_slider_superPropBase(object, property) { while (!Object.prototype.hasOwnProperty.call(object, property)) { object = range_slider_getPrototypeOf(object); if (object === null) break; } return object; }

function range_slider_inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); Object.defineProperty(subClass, "prototype", { writable: false }); if (superClass) range_slider_setPrototypeOf(subClass, superClass); }

function range_slider_setPrototypeOf(o, p) { range_slider_setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return range_slider_setPrototypeOf(o, p); }

function range_slider_createSuper(Derived) { var hasNativeReflectConstruct = range_slider_isNativeReflectConstruct(); return function _createSuperInternal() { var Super = range_slider_getPrototypeOf(Derived), result; if (hasNativeReflectConstruct) { var NewTarget = range_slider_getPrototypeOf(this).constructor; result = Reflect.construct(Super, arguments, NewTarget); } else { result = Super.apply(this, arguments); } return range_slider_possibleConstructorReturn(this, result); }; }

function range_slider_possibleConstructorReturn(self, call) { if (call && (range_slider_typeof(call) === "object" || typeof call === "function")) { return call; } else if (call !== void 0) { throw new TypeError("Derived constructors may only return object or undefined"); } return range_slider_assertThisInitialized(self); }

function range_slider_assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return self; }

function range_slider_isNativeReflectConstruct() { if (typeof Reflect === "undefined" || !Reflect.construct) return false; if (Reflect.construct.sham) return false; if (typeof Proxy === "function") return true; try { Boolean.prototype.valueOf.call(Reflect.construct(Boolean, [], function () {})); return true; } catch (e) { return false; } }

function range_slider_getPrototypeOf(o) { range_slider_getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return range_slider_getPrototypeOf(o); }

function range_slider_ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); enumerableOnly && (symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; })), keys.push.apply(keys, symbols); } return keys; }

function range_slider_objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = null != arguments[i] ? arguments[i] : {}; i % 2 ? range_slider_ownKeys(Object(source), !0).forEach(function (key) { range_slider_defineProperty(target, key, source[key]); }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)) : range_slider_ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } return target; }

function range_slider_defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }



 // eslint-disable-next-line no-unused-vars

var range_slider_VERSION = '0.0.1';
var range_slider_NAME = 'ui.rslider'; // data-attribute 로 설정 가능한 옵션 들

var range_slider_dataAttrConfig = {};

var range_slider_defaultConfig = range_slider_objectSpread({}, range_slider_dataAttrConfig);

var range_slider_RangeSlider = /*#__PURE__*/function (_UI) {
  range_slider_inherits(RangeSlider, _UI);

  var _super = range_slider_createSuper(RangeSlider);

  function RangeSlider(element) {
    var _this;

    var config = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

    range_slider_classCallCheck(this, RangeSlider);

    _this = _super.call(this, element, config);

    _this._setupConfog(config);

    _this._fake = null;
    _this._input = null;
    _this._handle = null;
    _this._fill = null;
    _this._currentValue = 0;

    _this._init();

    return _this;
  }

  range_slider_createClass(RangeSlider, [{
    key: "destroy",
    value:
    /**
     * 삭제
     */
    function destroy() {
      this._removeEvents();

      this._removeVars();

      range_slider_get(range_slider_getPrototypeOf(RangeSlider.prototype), "destroy", this).call(this);
    }
  }, {
    key: "_setupConfog",
    value: function _setupConfog(config) {
      this._config = range_slider_objectSpread(range_slider_objectSpread(range_slider_objectSpread({}, range_slider_defaultConfig), config), dataSetToObject(this._element, range_slider_dataAttrConfig, 'rslider'));
    }
    /**
     * 필요에 따라 public으로..
     */

  }, {
    key: "_init",
    value: function _init() {
      this._createElements();

      this._initVars();

      this._initEvents();

      this._currentValue = this._input.value / this._input.max * 100;

      this._render(this._currentValue);
    }
    /**
     * 변수 초기화
     */

  }, {
    key: "_initVars",
    value: function _initVars() {
      this._input = this._element.querySelector('input[type="range"]');
      this._handle = this._element.querySelector('.range-fake__handle');
      this._fill = this._element.querySelector('.range-fake__fill');
    }
    /**
     * 이벤트 초기화
     */

  }, {
    key: "_initEvents",
    value: function _initEvents() {
      var _this2 = this;

      event_handler.on(this._input, range_slider_get(range_slider_getPrototypeOf(RangeSlider.prototype), "_eventName", this).call(this, 'input'), function (e) {
        _this2._currentValue = e.target.value;
        var per = e.target.value / e.target.max * 100;
        event_handler.trigger(_this2._element, RangeSlider.EVENT.CHANGE, {
          value: _this2._currentValue
        });

        _this2._render(per);
      });
    }
    /**
     * 변수 초기화
     */

  }, {
    key: "_removetVars",
    value: function _removetVars() {
      this._element.removeChild(this._fake);

      this._input = null;
      this._handle = null;
      this._fill = null;
      this._fake = null;
      this._currentValue = 0;
      this._config = null;
      this._element = null;
    }
    /**
     * 이벤트 초기화
     */

  }, {
    key: "_removeEvents",
    value: function _removeEvents() {
      event_handler.off(this._input, range_slider_get(range_slider_getPrototypeOf(RangeSlider.prototype), "_eventName", this).call(this, 'input'));
    }
  }, {
    key: "_render",
    value: function _render(per) {
      this._handle.style.left = "".concat(per, "%");
      this._fill.style.width = "".concat(per, "%");
    }
  }, {
    key: "_createElements",
    value: function _createElements() {
      var template = "\n      <div class=\"range-fake\">\n        <span class=\"range-fake__bg\"></span>\n        <span class=\"range-fake__fill\"></span>\n        <button type=\"button\" class=\"range-fake__handle left\"></button>\n      </div>\n    ";
      this._fake = toHTML(template);

      this._element.appendChild(this._fake);
    }
  }], [{
    key: "EVENT",
    get: function get() {
      return {
        CHANGE: "".concat(range_slider_NAME, ".change")
      };
    }
  }, {
    key: "NAME",
    get: function get() {
      return range_slider_NAME;
    }
  }]);

  return RangeSlider;
}(ui_base);

/* harmony default export */ var range_slider = (range_slider_RangeSlider);
// EXTERNAL MODULE: ./node_modules/core-js/modules/es.number.constructor.js
var es_number_constructor = __webpack_require__(139);

// EXTERNAL MODULE: ./node_modules/nouislider/dist/nouislider.js
var nouislider = __webpack_require__(141);
var nouislider_default = /*#__PURE__*/__webpack_require__.n(nouislider);

// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/ui/range-slider-multi.js
function range_slider_multi_typeof(obj) { "@babel/helpers - typeof"; return range_slider_multi_typeof = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (obj) { return typeof obj; } : function (obj) { return obj && "function" == typeof Symbol && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }, range_slider_multi_typeof(obj); }





















function range_slider_multi_classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function range_slider_multi_defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function range_slider_multi_createClass(Constructor, protoProps, staticProps) { if (protoProps) range_slider_multi_defineProperties(Constructor.prototype, protoProps); if (staticProps) range_slider_multi_defineProperties(Constructor, staticProps); Object.defineProperty(Constructor, "prototype", { writable: false }); return Constructor; }

function range_slider_multi_get() { if (typeof Reflect !== "undefined" && Reflect.get) { range_slider_multi_get = Reflect.get; } else { range_slider_multi_get = function _get(target, property, receiver) { var base = range_slider_multi_superPropBase(target, property); if (!base) return; var desc = Object.getOwnPropertyDescriptor(base, property); if (desc.get) { return desc.get.call(arguments.length < 3 ? target : receiver); } return desc.value; }; } return range_slider_multi_get.apply(this, arguments); }

function range_slider_multi_superPropBase(object, property) { while (!Object.prototype.hasOwnProperty.call(object, property)) { object = range_slider_multi_getPrototypeOf(object); if (object === null) break; } return object; }

function range_slider_multi_inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); Object.defineProperty(subClass, "prototype", { writable: false }); if (superClass) range_slider_multi_setPrototypeOf(subClass, superClass); }

function range_slider_multi_setPrototypeOf(o, p) { range_slider_multi_setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return range_slider_multi_setPrototypeOf(o, p); }

function range_slider_multi_createSuper(Derived) { var hasNativeReflectConstruct = range_slider_multi_isNativeReflectConstruct(); return function _createSuperInternal() { var Super = range_slider_multi_getPrototypeOf(Derived), result; if (hasNativeReflectConstruct) { var NewTarget = range_slider_multi_getPrototypeOf(this).constructor; result = Reflect.construct(Super, arguments, NewTarget); } else { result = Super.apply(this, arguments); } return range_slider_multi_possibleConstructorReturn(this, result); }; }

function range_slider_multi_possibleConstructorReturn(self, call) { if (call && (range_slider_multi_typeof(call) === "object" || typeof call === "function")) { return call; } else if (call !== void 0) { throw new TypeError("Derived constructors may only return object or undefined"); } return range_slider_multi_assertThisInitialized(self); }

function range_slider_multi_assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return self; }

function range_slider_multi_isNativeReflectConstruct() { if (typeof Reflect === "undefined" || !Reflect.construct) return false; if (Reflect.construct.sham) return false; if (typeof Proxy === "function") return true; try { Boolean.prototype.valueOf.call(Reflect.construct(Boolean, [], function () {})); return true; } catch (e) { return false; } }

function range_slider_multi_getPrototypeOf(o) { range_slider_multi_getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return range_slider_multi_getPrototypeOf(o); }

function range_slider_multi_ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); enumerableOnly && (symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; })), keys.push.apply(keys, symbols); } return keys; }

function range_slider_multi_objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = null != arguments[i] ? arguments[i] : {}; i % 2 ? range_slider_multi_ownKeys(Object(source), !0).forEach(function (key) { range_slider_multi_defineProperty(target, key, source[key]); }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)) : range_slider_multi_ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } return target; }

function range_slider_multi_defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }




 // eslint-disable-next-line no-unused-vars

var range_slider_multi_VERSION = '0.0.1';
var range_slider_multi_NAME = 'ui.rsliders'; // data-attribute 로 설정 가능한 옵션 들

var range_slider_multi_dataAttrConfig = {
  blockStep: 0
};

var range_slider_multi_defaultConfig = range_slider_multi_objectSpread({}, range_slider_multi_dataAttrConfig);

var range_slider_multi_RangeSliderMulti = /*#__PURE__*/function (_UI) {
  range_slider_multi_inherits(RangeSliderMulti, _UI);

  var _super = range_slider_multi_createSuper(RangeSliderMulti);

  function RangeSliderMulti(element) {
    var _this;

    var config = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

    range_slider_multi_classCallCheck(this, RangeSliderMulti);

    _this = _super.call(this, element, config);

    _this._setupConfog(config);

    _this._minStep = -1;
    _this._maxStep = -1;
    _this._allyStepList = [];

    _this._init();

    return _this;
  }

  range_slider_multi_createClass(RangeSliderMulti, [{
    key: "destroy",
    value:
    /**
     * 삭제
     */
    function destroy() {
      this._removeVars();

      range_slider_multi_get(range_slider_multi_getPrototypeOf(RangeSliderMulti.prototype), "destroy", this).call(this);
    }
  }, {
    key: "_setupConfog",
    value: function _setupConfog(config) {
      this._config = range_slider_multi_objectSpread(range_slider_multi_objectSpread(range_slider_multi_objectSpread({}, range_slider_multi_defaultConfig), config), dataSetToObject(this._element, range_slider_multi_dataAttrConfig, 'rsliders'));
    }
    /**
     * 필요에 따라 public으로..
     */

  }, {
    key: "_init",
    value: function _init() {
      var _this2 = this;

      var a11yStep = this._element.getAttribute('data-rsliders-a11y');

      var allyStepList = a11yStep ? a11yStep.split('|') : null;

      var inputs = this._element.querySelectorAll('input[type="range"]');

      inputs.forEach(function (el) {
        el.style.display = 'none';
      });
      var stepValue = 100 / (allyStepList.length - 1);
      var range = {};

      for (var i = 0; i < allyStepList.length; i++) {
        if (i === 0) {
          range['min'] = i * stepValue;
        } else if (i === allyStepList.length - 1) {
          range['max'] = i * stepValue;
        } else {
          range[i * stepValue] = i * stepValue;
        }
      }

      nouislider_default.a.create(this._element, {
        start: [inputs[0].value, inputs[1].value],
        connect: true,
        snap: true,
        range: range,
        margin: stepValue
      });

      this._element.noUiSlider.on('update', function (values, handle, unencoded, tap, positions, noUiSlider) {
        _this2._minStep = Math.ceil(Number(values[0]) / stepValue);
        _this2._maxStep = Math.ceil(Number(values[1]) / stepValue);
        inputs[0].value = Number(values[0]);
        inputs[1].value = Number(values[1]);

        _this2._updateAria(noUiSlider, allyStepList);

        event_handler.trigger(_this2._element, RangeSliderMulti.EVENT.CHANGE, {
          value: [Number(values[0]), Number(values[1])]
        });
      });
    }
  }, {
    key: "_updateAria",
    value: function _updateAria(noUiSlider, _allyStepList) {
      if (_allyStepList) {
        var minHandle = noUiSlider.target.querySelector('.noUi-handle-lower');
        var maxHandle = noUiSlider.target.querySelector('.noUi-handle-upper');
        var start = this._minStep;
        var end = this._maxStep;
        var currString = "".concat(_allyStepList[start], "\uBD80\uD130 ").concat(_allyStepList[end], "\uAE4C\uC9C0");
        minHandle.setAttribute('aria-valuetext', "\uBC94\uC704\uC120\uD0DD \uC2DC\uC791 \uC810, \uD604\uC7AC \uBC94\uC704: ".concat(currString));
        maxHandle.setAttribute('aria-valuetext', "\uBC94\uC704\uC120\uD0DD \uC885\uB8CC \uC810, \uD604\uC7AC \uBC94\uC704: ".concat(currString));
      }
    }
  }], [{
    key: "EVENT",
    get: function get() {
      return {
        CHANGE: "".concat(range_slider_multi_NAME, ".change")
      };
    }
  }, {
    key: "NAME",
    get: function get() {
      return range_slider_multi_NAME;
    }
  }]);

  return RangeSliderMulti;
}(ui_base);

/* harmony default export */ var range_slider_multi = (range_slider_multi_RangeSliderMulti);
// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/ui/tooltip-box.js
function tooltip_box_typeof(obj) { "@babel/helpers - typeof"; return tooltip_box_typeof = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (obj) { return typeof obj; } : function (obj) { return obj && "function" == typeof Symbol && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }, tooltip_box_typeof(obj); }

















function tooltip_box_ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); enumerableOnly && (symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; })), keys.push.apply(keys, symbols); } return keys; }

function tooltip_box_objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = null != arguments[i] ? arguments[i] : {}; i % 2 ? tooltip_box_ownKeys(Object(source), !0).forEach(function (key) { tooltip_box_defineProperty(target, key, source[key]); }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)) : tooltip_box_ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } return target; }

function tooltip_box_classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function tooltip_box_defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function tooltip_box_createClass(Constructor, protoProps, staticProps) { if (protoProps) tooltip_box_defineProperties(Constructor.prototype, protoProps); if (staticProps) tooltip_box_defineProperties(Constructor, staticProps); Object.defineProperty(Constructor, "prototype", { writable: false }); return Constructor; }

function tooltip_box_get() { if (typeof Reflect !== "undefined" && Reflect.get) { tooltip_box_get = Reflect.get; } else { tooltip_box_get = function _get(target, property, receiver) { var base = tooltip_box_superPropBase(target, property); if (!base) return; var desc = Object.getOwnPropertyDescriptor(base, property); if (desc.get) { return desc.get.call(arguments.length < 3 ? target : receiver); } return desc.value; }; } return tooltip_box_get.apply(this, arguments); }

function tooltip_box_superPropBase(object, property) { while (!Object.prototype.hasOwnProperty.call(object, property)) { object = tooltip_box_getPrototypeOf(object); if (object === null) break; } return object; }

function tooltip_box_inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function"); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, writable: true, configurable: true } }); Object.defineProperty(subClass, "prototype", { writable: false }); if (superClass) tooltip_box_setPrototypeOf(subClass, superClass); }

function tooltip_box_setPrototypeOf(o, p) { tooltip_box_setPrototypeOf = Object.setPrototypeOf || function _setPrototypeOf(o, p) { o.__proto__ = p; return o; }; return tooltip_box_setPrototypeOf(o, p); }

function tooltip_box_createSuper(Derived) { var hasNativeReflectConstruct = tooltip_box_isNativeReflectConstruct(); return function _createSuperInternal() { var Super = tooltip_box_getPrototypeOf(Derived), result; if (hasNativeReflectConstruct) { var NewTarget = tooltip_box_getPrototypeOf(this).constructor; result = Reflect.construct(Super, arguments, NewTarget); } else { result = Super.apply(this, arguments); } return tooltip_box_possibleConstructorReturn(this, result); }; }

function tooltip_box_possibleConstructorReturn(self, call) { if (call && (tooltip_box_typeof(call) === "object" || typeof call === "function")) { return call; } else if (call !== void 0) { throw new TypeError("Derived constructors may only return object or undefined"); } return tooltip_box_assertThisInitialized(self); }

function tooltip_box_assertThisInitialized(self) { if (self === void 0) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return self; }

function tooltip_box_isNativeReflectConstruct() { if (typeof Reflect === "undefined" || !Reflect.construct) return false; if (Reflect.construct.sham) return false; if (typeof Proxy === "function") return true; try { Boolean.prototype.valueOf.call(Reflect.construct(Boolean, [], function () {})); return true; } catch (e) { return false; } }

function tooltip_box_getPrototypeOf(o) { tooltip_box_getPrototypeOf = Object.setPrototypeOf ? Object.getPrototypeOf : function _getPrototypeOf(o) { return o.__proto__ || Object.getPrototypeOf(o); }; return tooltip_box_getPrototypeOf(o); }

function tooltip_box_defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

/* eslint-disable no-unused-vars */


 // eslint-disable-next-line no-unused-vars

var tooltip_box_VERSION = '1.0.0';
var tooltip_box_NAME = 'ui.tooltip-box'; // 고정 식별자로 쓸 data-attr

var tooltip_box_IDENTIFIER = {
  TARGET: 'data-ui-tpbox',
  CLOSE: 'data-ui-tpbox-close'
};
var tooltip_box_defaultConfig = {
  showClass: 'active',
  hideClass: 'deactive',
  animate: true
};

var tooltip_box_TooltipBox = /*#__PURE__*/function (_UI) {
  tooltip_box_inherits(TooltipBox, _UI);

  var _super = tooltip_box_createSuper(TooltipBox);

  function TooltipBox(element) {
    var _this;

    var config = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

    tooltip_box_classCallCheck(this, TooltipBox);

    _this = _super.call(this, element, config);
    _this._tooltipBox = null;
    _this._closeButton = null;
    _this._tooltipBoxName = '';

    _this._setupConfog(config);

    _this._init();

    return _this;
  }

  tooltip_box_createClass(TooltipBox, [{
    key: "destroy",
    value: // public method
    function destroy() {
      this._removeEvents();

      this._removeVars();

      tooltip_box_get(tooltip_box_getPrototypeOf(TooltipBox.prototype), "destroy", this).call(this);
    }
  }, {
    key: "_setupConfog",
    value: function _setupConfog(config) {
      this._config = tooltip_box_objectSpread(tooltip_box_objectSpread(tooltip_box_objectSpread({}, tooltip_box_defaultConfig), TooltipBox.GLOBAL_CONFIG), config);
    }
  }, {
    key: "_init",
    value: function _init() {
      this._initVars();

      this._initEvents();
    }
    /**
     * 변수 초기화
     */

  }, {
    key: "_initVars",
    value: function _initVars() {
      this._tooltipBoxName = this._element.getAttribute(tooltip_box_IDENTIFIER.TARGET);
      this._tooltipBox = document.querySelector(this._tooltipBoxName);

      if (!this._tooltipBox) {
        this._throwError("".concat(this._tooltipBoxName, " -> does not match any content element!"));
      }

      this._closeButton = this._tooltipBox.querySelector("[".concat(tooltip_box_IDENTIFIER.CLOSE, "]"));
    }
    /**
     * 이벤트 초기화
     */

  }, {
    key: "_initEvents",
    value: function _initEvents() {
      var _this2 = this;

      event_handler.on(this._element, this._eventName('click'), function (e) {
        e.preventDefault();
        if (_this2._tooltipBox.classList.contains(_this2._config.showClass)) return;

        _this2._show();

        setTimeout(function () {
          event_handler.on(window, _this2._eventName('click'), function (e) {
            if (!e.target.closest(_this2._tooltipBoxName)) {
              _this2._hide();
            }
          });
        }, 0);
      });

      if (this._closeButton) {
        event_handler.on(this._closeButton, this._eventName('click'), function (e) {
          e.preventDefault();

          _this2._hide();
        });
      }
    }
    /**
     * 변수 초기화
     */

  }, {
    key: "_removetVars",
    value: function _removetVars() {
      event_handler.off(this._element, this._eventName('click'));

      if (this._closeButton) {
        event_handler.off(this._closeButton, this._eventName('click'));
      }
    }
    /**
     * 이벤트 초기화
     */

  }, {
    key: "_removeEvents",
    value: function _removeEvents() {}
  }, {
    key: "_show",
    value: function _show() {
      this._tooltipBox.classList.add(this._config.showClass);

      if (this._config.hideClass) {
        this._tooltipBox.classList.remove(this._config.hideClass);
      }
    }
  }, {
    key: "_hide",
    value: function _hide() {
      var _this3 = this;

      this._tooltipBox.classList.remove(this._config.showClass);

      if (this._config.hideClass) {
        this._tooltipBox.classList.add(this._config.hideClass);

        if (this._config.animate) {
          event_handler.one(this._tooltipBox, 'animationend', function () {
            _this3._tooltipBox.classList.remove(_this3._config.hideClass);
          });
        }
      }

      event_handler.off(window, this._eventName('click'));
    }
  }], [{
    key: "NAME",
    get: function get() {
      return tooltip_box_NAME;
    }
  }]);

  return TooltipBox;
}(ui_base);

tooltip_box_defineProperty(tooltip_box_TooltipBox, "GLOBAL_CONFIG", {});

/* harmony default export */ var tooltip_box = (tooltip_box_TooltipBox);
// EXTERNAL MODULE: ./src/portal/resources/js/portal/src/iePolyfill.js
var iePolyfill = __webpack_require__(188);

// EXTERNAL MODULE: ./node_modules/core-js/modules/es.array.fill.js
var es_array_fill = __webpack_require__(189);

// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/charts/bar-chart.js







function bar_chart_classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function bar_chart_defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function bar_chart_createClass(Constructor, protoProps, staticProps) { if (protoProps) bar_chart_defineProperties(Constructor.prototype, protoProps); if (staticProps) bar_chart_defineProperties(Constructor, staticProps); Object.defineProperty(Constructor, "prototype", { writable: false }); return Constructor; }



var CANVAS_HEIGHT = 185;
var START_Y = 148;
var BAR_WIDTH = 12;
var BAR_SPACING = 20;

var bar_chart_BarChart = /*#__PURE__*/function () {
  function BarChart(element, options) {
    bar_chart_classCallCheck(this, BarChart);

    this._element = element;
    this._categories = options.categories;
    this._dataset = options.dataset;
    this._minData = 0;
    this._maxData = 0;
    this._canvas = null;
    this._ctx = null;
    this._tpTrigger = null;
    this._tooltip = null;
    this._objectInfos = [];
    this._devicePixelRatio = 0;
    this._backingStoreRatio = 0;
    this._ratio = 0;
    this._viewWidth = 0;

    this._init();
  }

  bar_chart_createClass(BarChart, [{
    key: "update",
    value: function update(options) {
      this._categories = options.categories || this._categories;
      this._dataset = options.dataset || this._dataset;

      var minMax = this._getMinMax();

      this._minData = minMax.min;
      this._maxData = minMax.max;

      this._createLegend();

      this._resize();
    }
  }, {
    key: "_init",
    value: function _init() {
      this._canvas = document.createElement('canvas');
      this._canvas.height = CANVAS_HEIGHT;
      this._ctx = this._canvas.getContext('2d');

      this._element.appendChild(this._canvas);

      var minMax = this._getMinMax();

      this._minData = minMax.min;
      this._maxData = minMax.max;
      this._tpTrigger = document.querySelector('#tp_trigger');
      this._tooltip = document.querySelector('#bar_chart_tp');
      this._barGroupWidth = 0;
      this._devicePixelRatio = window.devicePixelRatio || 1; // eslint-disable-next-line max-len

      this._backingStoreRatio = this._ctx.webkitBackingStorePixelRatio || this._ctx.mozBackingStorePixelRatio || this._ctx.msBackingStorePixelRatio || this._ctx.oBackingStorePixelRatio || this._ctx.backingStorePixelRatio || 1;
      this._ratio = this._devicePixelRatio / this._backingStoreRatio;

      this._initEvents();

      this._createLegend();

      this._resize();

      this._updateTooltip(this._categories.length - 1);
    }
  }, {
    key: "_initEvents",
    value: function _initEvents() {
      var _this = this;

      event_handler.on(this._canvas, 'click', function (e) {
        var currentX = e.pageX - _this._canvas.getBoundingClientRect().left;

        for (var i = 0; i < _this._objectInfos.length; i++) {
          var sx = _this._objectInfos[i].startX;
          var ex = _this._objectInfos[i].endX;

          if (currentX > sx && ex > currentX) {
            _this._updateTooltip(i);

            break;
          }
        }
      });
      event_handler.on(window, 'resize.chart-bar', this._resize.bind(this));
    }
  }, {
    key: "_resize",
    value: function _resize() {
      var stageWidth = this._element.getBoundingClientRect().width;

      this._viewWidth = stageWidth;
      this._canvas.style.width = "".concat(stageWidth, "px");
      this._canvas.style.height = "".concat(CANVAS_HEIGHT, "px");
      this._canvas.width = stageWidth * this._ratio;
      this._canvas.height = CANVAS_HEIGHT * this._ratio;

      this._ctx.scale(this._ratio, this._ratio);

      this._drawCanvas();
    }
  }, {
    key: "_updateTooltip",
    value: function _updateTooltip(index) {
      var titleEl = this._tooltip.querySelector('.chart__title');

      var contentEl = this._tooltip.querySelector('.chart__content');

      var curTitle = this._categories[index];
      var contentString = '';
      titleEl.textContent = curTitle;

      for (var i = 0; i < this._dataset.length; i++) {
        var currentTooltip = this._dataset[i].tooltip;
        var currentValue = this._dataset[i].data[index];
        var currentColor = this._dataset[i].color;
        var currentPtn = this._dataset[i].pattern === 0 ? '../../resources/images/portal/common/ptn2.png' : '../../resources/images/portal/common/ptn1.png';
        contentString += "<div>\n        <span class=\"chart__bullet\" style=\"background: url(".concat(currentPtn, ");background-color:").concat(currentColor, "\"></span>\n        ").concat(currentTooltip.replace('{value}', Number(currentValue).toLocaleString('ko-KR')), "</div>");
      }

      contentEl.innerHTML = contentString;

      this._tpTrigger.click();
    }
  }, {
    key: "_getMinMax",
    value: function _getMinMax() {
      var min = 99999;
      var max = -99999;

      for (var i = 0; i < this._categories.length; i++) {
        for (var ii = 0; ii < this._dataset.length; ii++) {
          var currentValue = this._dataset[ii].data[i];
          min = Math.min(min, currentValue);
          max = Math.max(max, currentValue);
        }
      }

      return {
        min: min,
        max: max
      };
    }
  }, {
    key: "_getBarHeigh",
    value: function _getBarHeigh(value) {
      var max = this._maxData + 100;
      var per = value / max;
      var perByPx = START_Y * per;
      return perByPx; //
    }
  }, {
    key: "_categoryRender",
    value: function _categoryRender(index, category) {
      var drawInfo = {};
      var width = this._viewWidth / this._categories.length;

      this._ctx.beginPath();

      this._ctx.fillStyle = '#eaeaea';

      this._ctx.fillRect(20, START_Y, this._viewWidth - 40, 1);

      this._ctx.beginPath();

      this._ctx.fillStyle = '#4c4c4c';
      this._ctx.font = '14px KT-sans';
      this._ctx.textAlign = 'center';
      this._ctx.textBaseline = 'top';

      this._ctx.fillText(category, index * width + width / 2, 165, width);

      for (var i = 0; i < this._dataset.length; i++) {
        var currentValue = this._getBarHeigh(this._dataset[i].data[index]);

        var startX = width * index + i * BAR_SPACING;
        var resultX = startX + width / 2 - this._barGroupWidth / 2;

        this._barRender(this._dataset[i].color, currentValue, resultX);

        this._barPtnRender(this._dataset[i].pattern, currentValue, resultX);

        if (i === 0) {
          drawInfo.startX = resultX;
        } else {
          drawInfo.endX = resultX + BAR_WIDTH;
        }
      }

      return drawInfo;
    }
  }, {
    key: "_barRender",
    value: function _barRender(color, value, x) {
      var radius = 5;
      this._ctx.fillStyle = color;

      this._ctx.beginPath();

      this._ctx.moveTo(x + radius, START_Y - value);

      this._ctx.arcTo(x + BAR_WIDTH, START_Y - value, x + BAR_WIDTH, START_Y - value + value, radius);

      this._ctx.arcTo(x + BAR_WIDTH, START_Y - value + value, x, START_Y - value + value, 0);

      this._ctx.arcTo(x, START_Y - value + value, x, START_Y - value, 0);

      this._ctx.arcTo(x, START_Y - value, x + BAR_WIDTH, START_Y - value, radius);

      this._ctx.fill();
    }
  }, {
    key: "_barPtnRender",
    value: function _barPtnRender(pattern, value, x) {
      var _this2 = this;

      var img = new Image();
      var radius = 5;
      img.src = pattern === 0 ? '../../resources/images/portal/common/ptn2.png' : '../../resources/images/portal/common/ptn1.png';

      img.onload = function () {
        var pattern = _this2._ctx.createPattern(img, 'repeat');

        _this2._ctx.beginPath();

        _this2._ctx.fillStyle = pattern;

        _this2._ctx.moveTo(x + radius, START_Y - value);

        _this2._ctx.arcTo(x + BAR_WIDTH, START_Y - value, x + BAR_WIDTH, START_Y - value + value, radius);

        _this2._ctx.arcTo(x + BAR_WIDTH, START_Y - value + value, x, START_Y - value + value, 0);

        _this2._ctx.arcTo(x, START_Y - value + value, x, START_Y - value, 0);

        _this2._ctx.arcTo(x, START_Y - value, x + BAR_WIDTH, START_Y - value, radius);

        _this2._ctx.fill();
      };
    }
  }, {
    key: "_getLegend",
    value: function _getLegend(name, color, ptnurl) {
      var t = "\n    <div class=\"legend__item\">\n      <span class=\"chart__bullet\" style=\"background: url(".concat(ptnurl, ");background-color: ").concat(color, ";\"></span>\n      <span class=\"legend__text\">").concat(name, "</span>\n    </div>");
      return toHTML(t);
    }
  }, {
    key: "_createLegend",
    value: function _createLegend() {
      var hasLegendWrap = this._element.querySelector('.legend');

      if (hasLegendWrap) {
        this._element.removeChild(hasLegendWrap);
      }

      var legendWrap = document.createElement('div');
      var groupChildLength = this._dataset.length;
      legendWrap.className = "legend";

      this._element.appendChild(legendWrap);

      for (var i = 0; i < groupChildLength; i++) {
        var dSet = this._dataset[i];
        var color = dSet.color;
        var ptnurl = dSet.pattern === 0 ? '../../resources/images/portal/common/ptn2.png' : '../../resources/images/portal/common/ptn1.png';
        var name = dSet.name;
        legendWrap.appendChild(this._getLegend(name, color, ptnurl));
      }

      this._barGroupWidth = groupChildLength * 6 + BAR_SPACING / 2;
    }
  }, {
    key: "_drawCanvas",
    value: function _drawCanvas() {
      this._ctx.clearRect(0, 0, this._viewWidth, CANVAS_HEIGHT);

      this._objectInfos = [];

      for (var i = 0; i < this._categories.length; i++) {
        var dInfo = this._categoryRender(i, this._categories[i]);

        this._objectInfos.push(dInfo);
      }

      this._ctx.closePath();
    }
  }]);

  return BarChart;
}();

/* harmony default export */ var bar_chart = (bar_chart_BarChart);
// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/ui/toast.js










function toast_ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); enumerableOnly && (symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; })), keys.push.apply(keys, symbols); } return keys; }

function toast_objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = null != arguments[i] ? arguments[i] : {}; i % 2 ? toast_ownKeys(Object(source), !0).forEach(function (key) { toast_defineProperty(target, key, source[key]); }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)) : toast_ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } return target; }

function toast_classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function toast_defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } }

function toast_createClass(Constructor, protoProps, staticProps) { if (protoProps) toast_defineProperties(Constructor.prototype, protoProps); if (staticProps) toast_defineProperties(Constructor, staticProps); Object.defineProperty(Constructor, "prototype", { writable: false }); return Constructor; }

function toast_defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }


 // eslint-disable-next-line no-unused-vars

var toast_VERSION = '1.0.0';
var toast_NAME = 'ui.toast';
var toast_defaultConfig = {
  templateContainer: "",
  templateBox: "",
  lifeTime: 2000
};

var toast_Toast = /*#__PURE__*/function () {
  function Toast() {
    var config = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};

    toast_classCallCheck(this, Toast);

    this._config = toast_objectSpread(toast_objectSpread(toast_objectSpread({}, toast_defaultConfig), Toast.GLOBAL_CONFIG), config);
    this._toastBox = null;
  }

  toast_createClass(Toast, [{
    key: "show",
    value: function show(message) {
      var isPopup = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : false;

      this._show(message, isPopup);
    }
  }, {
    key: "_show",
    value: function _show(message, isPopup) {
      var _this = this;

      this._toastBox = toHTML(this._config.templateBox.replace('{{MESSAGE}}', message));
      Toast.getContainer(this._config.templateContainer).appendChild(this._toastBox); // eslint-disable-next-line no-unused-vars

      var reflow = this._toastBox.offsetHeight;

      this._toastBox.classList.add('is-active');

      console.log('isPopup', isPopup);

      if (isPopup) {
        this._toastBox.classList.add('is-popup');
      }

      Toast.TOAST_COUNT++;
      setTimeout(function () {
        _this._hide();
      }, this._config.lifeTime);
    }
  }, {
    key: "_hide",
    value: function _hide() {
      var _this2 = this;

      this._toastBox.classList.remove('is-active');

      this._toastBox.classList.add('is-deactive');

      event_handler.one(this._toastBox, 'animationend', function () {
        Toast.getContainer(_this2._config.templateContainer).removeChild(_this2._toastBox);
        Toast.TOAST_COUNT--;

        if (Toast.TOAST_COUNT <= 0) {
          document.body.removeChild(Toast.TOAST_HOLDER);
          Toast.TOAST_HOLDER = null;
        }
      });
    }
  }], [{
    key: "getContainer",
    value: function getContainer(template) {
      if (Toast.TOAST_HOLDER === null) {
        Toast.TOAST_HOLDER = toHTML(template);
        document.body.appendChild(Toast.TOAST_HOLDER);
      }

      return Toast.TOAST_HOLDER;
    }
  }, {
    key: "NAME",
    get: function get() {
      return toast_NAME;
    }
  }]);

  return Toast;
}();

toast_defineProperty(toast_Toast, "GLOBAL_CONFIG", {});

toast_defineProperty(toast_Toast, "TOAST_HOLDER", null);

toast_defineProperty(toast_Toast, "TOAST_COUNT", 0);

/* harmony default export */ var toast = (toast_Toast);
// CONCATENATED MODULE: ./src/portal/resources/js/portal/src/ktm.ui.js
function ktm_ui_ownKeys(object, enumerableOnly) { var keys = Object.keys(object); if (Object.getOwnPropertySymbols) { var symbols = Object.getOwnPropertySymbols(object); enumerableOnly && (symbols = symbols.filter(function (sym) { return Object.getOwnPropertyDescriptor(object, sym).enumerable; })), keys.push.apply(keys, symbols); } return keys; }

function ktm_ui_objectSpread(target) { for (var i = 1; i < arguments.length; i++) { var source = null != arguments[i] ? arguments[i] : {}; i % 2 ? ktm_ui_ownKeys(Object(source), !0).forEach(function (key) { ktm_ui_defineProperty(target, key, source[key]); }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(target, Object.getOwnPropertyDescriptors(source)) : ktm_ui_ownKeys(Object(source)).forEach(function (key) { Object.defineProperty(target, key, Object.getOwnPropertyDescriptor(source, key)); }); } return target; }

function ktm_ui_defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }










/* eslint-disable new-cap */















 // eslint-disable-next-line no-unused-vars

 // import FillChart from './charts/fill-chart';



smoothscroll_default.a.polyfill();
/**
 * UI 초기화 처리
 * @param {*} target
 * @param {*} UI
 * @param {*} options
 */

var UIInitializer = function UIInitializer(target, UI) {
  var options = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : {};
  var elements = document.querySelectorAll(target);
  elements.forEach(function (el) {
    if (!UI.getInstance(el)) {
      var _ui = new UI(el, options);

      _ui.init();
    }
  });
};

ui_tab.GLOBAL_CONFIG = {
  activeClass: 'is-active'
};
accordion.GLOBAL_CONFIG = {
  activeClass: 'is-active'
};
ui_dialog.GLOBAL_CONFIG = {
  openClass: 'is-active',
  closeClass: 'is-deactive'
};
toast.GLOBAL_CONFIG = {
  templateContainer: "<div class=\"c-toast\"></div>",
  templateBox: "<div class=\"c-toast__box\">{{MESSAGE}}</div>"
};
tooltip_box.GLOBAL_CONFIG = {
  showClass: 'is-active',
  hideClass: 'is-deactive'
};

var ktm_ui_Alert = function Alert(message) {
  var callbacks = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};
  var dialog = new dialog_hoc({
    layout: "\n      <div class=\"c-modal c-modal--alert\" role=\"alert\" aria-modal=\"true\">\n        <div class=\"c-modal__dialog\">\n          <div class=\"c-modal__content\">\n            <div class=\"c-modal__body\">\n              {{message}}\n            </div>\n            <div class=\"c-modal__footer\">\n              <div class=\"c-button-wrap u-mt--0\">\n                <button class=\"c-button c-button--lg c-button--primary c-button--w460\" data-dialog-close>\uD655\uC778</button>\n              </div>\n            </div>\n          </div>\n        </div>\n      </div>\n    ",
    replacer: {
      '{{a11y}}': getRandomID(),
      '{{message}}': message
    }
  });

  if (callbacks.open) {
    event_handler.one(dialog.getElement(), ui_dialog.EVENT.OPEN, function (event) {
      callbacks.open.apply(event.component, []);
    });
  }

  if (callbacks.close) {
    event_handler.one(dialog.getElement(), ui_dialog.EVENT.CLOSE, function (event) {
      callbacks.close.apply(event.component, []);
    });
  }

  if (callbacks.opened) {
    event_handler.one(dialog.getElement(), ui_dialog.EVENT.OPENED, function (event) {
      callbacks.opened.apply(event.component, []);
    });
  }

  if (callbacks.closed) {
    event_handler.one(dialog.getElement(), ui_dialog.EVENT.CLOSED, function (event) {
      setTimeout(function () {
        callbacks.closed.apply(event.component, []);
      }, 0);
    });
  }

  if (callbacks.click) {
    event_handler.one(dialog.getElement(), 'click', function (event) {
      callbacks.click.apply(event.component, []);
    });
  }

  dialog.open();
};

var ktm_ui_Confirm = function Confirm(message) {
  var confirmCallback = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : null;
  var confirmCancelCallback = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : null;
  var options = arguments.length > 3 && arguments[3] !== undefined ? arguments[3] : {};

  var confirmText = options.confirmText || "\uD655\uC778";
  var cancelText = options.cancelText || "\uCDE8\uC18C";

  var dialog = new dialog_hoc({
    layout: "\n\n  <div class=\"c-modal c-modal--alert\" role=\"alertdialog\" aria-modal=\"true\">\n    <div class=\"c-modal__dialog\">\n      <div class=\"c-modal__content\">\n        <div class=\"c-modal__body\">\n          {{message}}\n        </div>\n        <div class=\"c-modal__footer\">\n          <div class=\"c-button-wrap u-mt--0\">\n            <button class=\"c-button c-button--full c-button--gray\" data-dialog-close>{{cancelText}}</button>\n            <button class=\"c-button c-button--full c-button--primary\" data-dialog-confirm>{{confirmText}}</button>\n          </div>\n        </div>\n      </div>\n    </div>\n  </div>\n\n    ",
    replacer: {
      '{{a11y}}': getRandomID(),
      '{{message}}': message,
      '{{cancelText}}': cancelText,
      '{{confirmText}}': confirmText
    }
  });
  event_handler.one(dialog.getElement(), ui_dialog.EVENT.OPEN, function (event) {
    var confirm = dialog.getElement().querySelector('[data-dialog-confirm]');

    if (confirm) {
      event_handler.one(confirm, 'click', function () {
        if (confirmCallback) {
          confirmCallback.apply(event.component);
        }
      });
    }

    var confirm2 = dialog.getElement().querySelector('[data-dialog-close]');

    if (confirm2) {
      event_handler.one(confirm2, 'click', function () {
        if (confirmCancelCallback) {
            confirmCancelCallback.apply(event.component);
        }
      });
    }

  });


  dialog.open();
};

//data-dialog-close



var ktm_ui_initialize = function initialize() {
  // 탭
  UIInitializer('[data-ui-tab]', ui_tab); // 아코디언

  UIInitializer('[data-ui-accordion]', accordion); // 슬라이더 싱글

  UIInitializer('.c-range:not(.multi) > .c-range__slider', range_slider); // 슬라이더 멀티

  UIInitializer('.c-range.multi > .c-range__slider', range_slider_multi); // 툴팁박스

  UIInitializer('[data-ui-tpbox]', tooltip_box);
  data_helper_toggleHelper();
  data_helper_expandHelper();
  common.initialize();
  return 'initialized';
};
/**
 * flatpickr 원본소스 수정없이 커스텀이 필요하기 때문에
 * 플러그인 함수를 한번 랩핑하여 처리해야 하는 작업을 일괄적으로 처리하고
 * 플러그인을 반환한다.
 * @param {*} el
 * @param {*} options
 * @returns
 */


var ktm_ui_datePicker = function datePicker(el) {
  var options = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

  if (!window.flatpickr) {
    alert("\n      from ktm.ui.js\n      flatpickr.js(\uB2EC\uB825) \uBAA8\uB4C8\uC744 \uCC3E\uC744 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.\n\n      flatpickr \uBAA8\uB4C8\uC744 \uB85C\uB4DC\uD574\uC8FC\uC138\uC694.\n\n      resources/js/portal/vendor/flatpickr.min.js\n      ");
    throw new Error("\n    flatpickr.js(\uB2EC\uB825) \uBAA8\uB4C8\uC744 \uCC3E\uC744 \uC218 \uC5C6\uC2B5\uB2C8\uB2E4.\n\n    flatpickr \uBAA8\uB4C8\uC744 \uB85C\uB4DC\uD574\uC8FC\uC138\uC694.\n\n    <script src=\"../../resources/js/portal/vendor/flatpickr.min.js\"></script>\n    ");
  }

  common.datepickerValueBinder();
  return flatpickr(el, ktm_ui_objectSpread({
    onReady: function onReady() {
      this.currentYearElement.parentNode.insertBefore(toHTML("<span class=\"year-text\">\uB144</span>"), this.currentYearElement.nextSibling);
    }
  }, options));
};

if (window.KTM) {
  console.warn('');
  console.warn('   [ktm.ui.js] Overloading!');
  console.warn('');
  console.warn('   이 경고 문구는 [ktm.ui.js] 파일이 중복으로 로드되는 경우에 출력됩니다.');
  console.warn('   중복로드를 찾아 제거하세요.');
  console.warn('');
} else {
  document.addEventListener('DOMContentLoaded', function () {
    var windowScrollY = 0;
    event_handler.on(window, ui_dialog.EVENT.FIRST_OPEN, function () {
      document.body.style.overflow = 'hidden';
      windowScrollY = window.scrollY || window.pageYOffset;
    });
    event_handler.on(window, ui_dialog.EVENT.LAST_CLOSE, function () {
      document.body.style.overflow = '';
      window.scrollTo(0, windowScrollY);
    });
    ktm_ui_initialize();
    common.initFunc();
    event_handler.trigger(document, 'UILoaded');
    //console.log('UI Initialized!');
  });
}

var ktm_ui_swiperA11y = function swiperA11y(el) {
  var options = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : {};

  if (!options.pagination) {
    options.pagination = {
      el: '.swiper-pagination',
      type: 'fraction'
    };
  }

  return new window.Swiper(el, ktm_ui_objectSpread(ktm_ui_objectSpread({}, options), {}, {
    threshold: 10,
    // 10px 이상 움직여야 슬라이드 기능작동
    a11y: {
      nextSlideMessage: '다음 슬라이드',
      prevSlideMessage: '이전 슬라이드',
      firstSlideMessage: '첫번째 슬라이드',
      lastSlideMessage: '마지막 슬라이드'
    },
    on: ktm_ui_objectSpread({
      init: function init() {
        var _this = this;

        var target = typeof el === 'string' ? document.querySelector(el) : el; // 오토플레이 버튼이 있는 경우

        if (options.autoplay) {
          var autoPlayButton = target.querySelector('.swiper-button-pause');

          if (autoPlayButton) {
            autoPlayButton.setAttribute('aria-pressed', false);
            autoPlayButton.removeAttribute('aria-prssed');
            autoPlayButton.removeAttribute('type');
            event_handler.on(autoPlayButton, 'click', function (e) {
              var isPlayed = autoPlayButton.classList.contains('swiper-button-pause');

              if (isPlayed) {
                autoPlayButton.classList.remove('swiper-button-pause');
                autoPlayButton.classList.add('swiper-button-play');

                _this.autoplay.stop();
              } else {
                autoPlayButton.classList.add('swiper-button-pause');
                autoPlayButton.classList.remove('swiper-button-play');

                _this.autoplay.start();
              }

              autoPlayButton.setAttribute('aria-pressed', isPlayed);
            });
          }
        }

        var slides = this.slides;
        var swiper = this;
        var wrapper = this.el;
        [].forEach.call(slides, function (el, i) {
          var elTarget = options.a11yTarget ? el.querySelector(options.a11yTarget) : el;

          if (options.noMessage !== true) {
            elTarget.appendChild(toHTML("<span class=\"c-hidden\">\uCD1D ".concat(slides.length, " \uC7A5\uC758 \uC2AC\uB77C\uC774\uB4DC \uC911 ").concat(i + 1, " \uBC88\uC9F8 \uC2AC\uB77C\uC774\uB4DC\uC785\uB2C8\uB2E4.</span>"))); // elTarget.setAttribute('aria-label', '총' + slides.length + '장의 슬라이드 중 ' + (i + 1) + '번째 슬라이드입니다.');
          }

          elTarget.setAttribute('tabindex', 0);
          event_handler.on(elTarget, 'focusin', function () {
            setTimeout(function () {
              wrapper.scrollLeft = 0;
              swiper.slideTo(i, 100);
            }, 0);
          });
        });

        if (options.init) {
          options.init.apply(this);
        }
      },
      afterInit: function afterInit() {
        if (options.afterInit) {
          options.afterInit.apply(this);
        }
      }
    }, options.on)
  }));
};

var a11yChecker = function a11yChecker() {
  var tabChecker = function tabChecker() {
    var tabs = document.querySelectorAll('[data-ui-tab]');
    console.warn('-------------------> a11y Check! <-------------------');
    console.warn("\uD604\uC7AC \uAC80\uC0AC\uB41C \uD0ED \uAC1C\uC218: ".concat(tabs.length, "\uAC1C"));
    tabs.forEach(function (tab) {
      var tablist = tab.querySelectorAll('[role="tablist"]');

      if (tablist.length > 0) {
        tablist.forEach(function (el) {
          console.log('-----------------------------------------------------');
          console.log("  1. role=\"tablist\": \u2705");
          var roleTab = el.querySelectorAll('[role="tab"]');
          roleTab.forEach(function (ta) {
            var cId = ta.getAttribute('id');
            var sameId = document.querySelectorAll("#".concat(cId));

            if (sameId.length > 1) {
              console.log("       found the same ID --> ".concat(cId, " \u274C"));
            }

            console.log("    > ".concat(ta.textContent));
            console.log("       [header] role=\"tab\" \u2705");
            console.log("       [header] id=\"".concat(ta.getAttribute('id'), "\" \u2705"));
            var contentPanelID = ta.getAttribute('aria-controls');
            var contentPage = document.querySelector("#".concat(contentPanelID));

            if (contentPage) {
              console.log("       [header] aria-controls=".concat(ta.getAttribute('aria-controls'), " \u2705"));

              if (contentPage.getAttribute('role') === 'tabpanel') {
                console.log("       [content] role=\"tabpanel\" \u2705");
                console.log("       [content] id=\"".concat(contentPage.getAttribute('id'), "\" \u2705"));
              } else {
                console.log("       [content] role=\"tabpanel\" \u274C");
              }

              if (contentPage.getAttribute('aria-labelledby') === cId) {
                console.log("       [content] aria-labelledby=\"".concat(cId, "\" \u2705"));
              } else {
                console.log("       [content] aria-labelledby=\"".concat(cId, "\" \u274C"));
              }
            } else {
              console.log("       [header] aria-controls=".concat(ta.getAttribute('aria-controls'), " \u274C"));
            }

            if (ta.getAttribute('aria-selected') === 'false') {
              if (contentPage.offsetWidth < 1 && contentPage.offsetHeight < 1) {
                console.log("       [header] aria-selected=".concat(ta.getAttribute('aria-selected'), " \u2705"));
              } else {
                console.log("       [header] aria-selected=".concat(ta.getAttribute('aria-selected'), " \u274C"));
              }
            } else {
              if (contentPage.offsetWidth < 1 && contentPage.offsetHeight < 1) {
                console.log("       [header] aria-selected=".concat(ta.getAttribute('aria-selected'), " \u274C"));
              } else {
                console.log("       [header] aria-selected=".concat(ta.getAttribute('aria-selected'), " \u2705"));
              }
            }
          });
        });
      } else {
        console.warn("  1. role=\"tablist\": OK!");
      }
    });
  };

  tabChecker();
};

var ui = {
  initialize: ktm_ui_initialize,
  Tab: ui_tab,
  Dialog: ui_dialog,
  Alert: ktm_ui_Alert,
  Confirm: ktm_ui_Confirm,
  Accordion: accordion,
  datePicker: ktm_ui_datePicker,
  swiperA11y: ktm_ui_swiperA11y,
  RangeSlider: range_slider,
  RangeSliderMulti: range_slider_multi,
  LoadingSpinner: loading_spinner,
  //로딩 2번째 샘플
  LoadingSpinner2: loading_spinner2,
  // FillChart,
  BarChart: bar_chart,
  Toast: toast,
  a11yChecker: a11yChecker,
  imageLoadCheck: imageLoadCheck,
  initMenu:initMenu
};
window.KTM = ktm_ui_objectSpread({}, ui);

/***/ })
/******/ ]);
//# sourceMappingURL=ktm.ui.js.map