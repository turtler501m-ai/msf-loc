<template>
  <i :class="iconClasses" :aria-label="label" :aria-hidden="!label" v-bind="$attrs"></i>
</template>

<script>
export const ICON_NAMES = [
  'checkbox',
  'notice',
  'cart',
  'gofirst',
  'goprev',
  'gonext',
  'golast',
  'logout',
  'pwchange',
  'calendar',
  'home',
  'close',
  'menuPlus',
  'menuMinus',
  'arrowRight',
  'arrowUp',
  'arrowDown',
  'upload',
  'download',
  'link',
  'delete',
  'add',
  'clear',
  'print',
  'expand',
  'check',
]
export const ICON_SIZES = ['xsmall', 'small', 'medium', 'large']
</script>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  /** 아이콘 종류 */
  name: {
    type: String,
    required: true,
    // validator: (v) => ICON_NAMES.includes(v),
  },
  /** 아이콘 사이즈 */
  size: {
    type: String,
    default: 'large',
    // validator: (v) => ICON_SIZES.includes(v),
  },
  label: String,
  className: String, // 부모가 추가로 주는 클래스
})

const iconClasses = computed(() => [
  'msf-icon ', // 스타일의 .msf-icon 와 매칭
  props.name, // 스타일의 .notice, .cart 등과 매칭
  props.size, // 스타일의 .small, .large 등과 매칭
  props.className,
])
</script>

<style lang="scss">
@use 'sass:map';
@use 'sass:list';

/* 1. 기본 루트 스타일 */
.msf-icon {
  @include flex(inline-flex, center, center);
  color: var(--icon-color, inherit);
  font-size: var(--icon-size, rem(24px));
  vertical-align: middle;

  &::before {
    content: '';
    display: inline-block;
    width: 1em;
    aspect-ratio: 1;
    background-color: currentColor;
    mask-image: var(--icon-mask-image);
    mask-size: contain;
    mask-position: var(--icon-mask-position, center);
    mask-repeat: no-repeat;
  }
}

/* 2. 사이즈 정의 */
$icon-size-map: (
  xsmall: 12,
  small: 16,
  medium: 20,
  large: 24,
);

/* 3. 예외 아이콘 (mask 대신 background-image 사용) */
$bg-icons: userAvatar;

/* 4. 아이콘 생성 루프 */
@each $name, $sizes in $icons {
  .#{$name} {
    $first-size: list.nth(map.keys($sizes), 1);
    $fallback-path: map.get($sizes, $first-size);

    @if list.index($bg-icons, $name) {
      &::before {
        mask-image: unset;
        background-color: transparent;
        // 수정: 함수 호출 시 #{ } 인터폴레이션 사용
        background-image: safe-svg-url($fallback-path);
        background-size: contain;
        background-position: center;
        background-repeat: no-repeat;
      }
    } @else {
      // 수정: url() 직접 쓰지 말고 함수 거치기
      --icon-mask-image: #{safe-svg-url($fallback-path)};
    }

    @each $size-name, $size-value in $icon-size-map {
      $path: map.get($sizes, $size-value);
      @if $path {
        &.#{$size-name} {
          --icon-mask-image: #{safe-svg-url($path)};
        }
      }
    }
  }
}

/* 5. 사이즈별 변수 할당 클래스 (예: .small) */
@each $size-name, $size-value in $icon-size-map {
  .#{$size-name} {
    --icon-size: #{rem($size-value * 1px)};
  }
}
</style>
