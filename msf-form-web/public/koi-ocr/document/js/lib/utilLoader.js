// lib/utilLoader.js
import { APP_VERSION } from "../lib/version.js";

let _utilsPromise = null;

export function loadUtils() {
  if (!_utilsPromise) {
    _utilsPromise = import(`./utils.js?v=${APP_VERSION}`).then((m) => m);
  }
  return _utilsPromise;
}
