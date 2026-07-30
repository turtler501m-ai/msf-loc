// moduleLoader.js
import { APP_VERSION } from "./version.js";

export async function loadKoiOcr() {
  return (await import(`../ocr/koiOcr.js?v=${APP_VERSION}`)).default;
}
