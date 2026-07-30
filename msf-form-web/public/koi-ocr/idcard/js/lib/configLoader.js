// lib/configLoader.js
import { APP_VERSION } from "../lib/version.js";

let _configPromise = null;

export function loadConfig() {
  if (!_configPromise) {
    _configPromise = import(`../configs.js?v=${APP_VERSION}`).then((m) => m.config);
  }
  return _configPromise;
}
