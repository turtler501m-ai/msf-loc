<template>
  <div class="base-table-root">
    <table class="base-table">
      <colgroup>
        <slot name="colgroup"></slot>
      </colgroup>
      <thead>
        <slot name="thead"></slot>
      </thead>
      <tbody>
        <slot name="tbody"></slot>
      </tbody>
    </table>
  </div>
</template>

<style lang="scss" scoped>
.base-table-root {
  --tbl-body-border-color: var(--color-gray-75);
  --tbl-inner-padding: #{rem(8px)};

  width: 100%;
  border: var(--border-width-base) solid var(--color-gray-150);
  border-left: none;
  border-right: none;
  // margin-top: var(--layout-gutter-y);

  .base-table {
    width: 100%;
    border-collapse: collapse;
    table-layout: fixed;
    font-size: var(--font-size-14);
  }
  :deep(tr th:first-child),
  :deep(tr td:first-child) {
    border-left: none;
  }
  :deep(tr:first-child th),
  :deep(tr:first-child td) {
    border-top: none;
  }
  :deep(th),
  :deep(td) {
    padding: var(--tbl-inner-padding);
    border-top: var(--border-width-base) solid var(--tbl-body-border-color);
    border-left: var(--border-width-base) solid var(--tbl-body-border-color);
    text-align: left;
    height: rem(48px);
  }
  :deep(th) {
    background-color: var(--color-gray-25);
    color: var(--color-foreground);
    text-align: center;
  }
  :deep(tbody th) {
    text-align: left;
  }
  :deep(td) {
    color: var(--color-foreground);
  }
  :deep([class^='input-root']) {
    // min-width: rem(300px);
  }
}
</style>
