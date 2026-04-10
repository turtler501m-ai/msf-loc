<!-- 예상 납부금액 버튼 + 팝오버 -->
<template>
  <MsfPopover :is-open="isOpen" v-bind="$attrs" @update:is-open="onClose" title="예상 납부금액">
    <template #trigger>
      <MsfButton variant="accent1" @click="handlePrevPayCost">예상 납부금액</MsfButton>
    </template>
    <!-- cost-wrap -->
    <div class="cost-wrap">
      <!-- cost-list -->
      <div class="cost-list">
        <div class="cost-item">
          <dl class="cost-title">
            <dt>월 단말요금</dt>
            <dd>0 원</dd>
          </dl>
          <ul class="cost-infos">
            <li>
              <dl>
                <dt>단말기 출고가</dt>
                <dd>499,400 원</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>공통지원금</dt>
                <dd class="ut-color-accent">- 499,400 원</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>추가지원금</dt>
                <dd class="ut-color-accent">- 0 원</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>할부원금</dt>
                <dd>0 원</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>총할부수수료</dt>
                <dd>0 원</dd>
              </dl>
            </li>
          </ul>
        </div>
        <div class="cost-item">
          <dl class="cost-title">
            <dt>월 통신요금</dt>
            <dd>21,000 원</dd>
          </dl>
          <ul class="cost-infos">
            <li>
              <dl>
                <dt>기본요금</dt>
                <dd>21,000 원</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>프로모션 할인</dt>
                <dd class="ut-color-accent">0 원</dd>
              </dl>
            </li>
          </ul>
        </div>
        <div class="cost-item">
          <dl class="cost-title">
            <dt>기타요금</dt>
            <dd>0 원</dd>
          </dl>
          <ul class="cost-infos">
            <li>
              <dl>
                <dt>가입비(3개월 분납)</dt>
                <dd class="ut-color-accent ut-text-strike">7,200 원(무료)</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>USIM(최초 1회)</dt>
                <dd>8,800 원</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>가입비(3개월 분납)</dt>
                <dd class="ut-color-accent">0 원</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>eSIM(1회)</dt>
                <dd>0 원</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>번호 이동 수수료</dt>
                <dd>800 원</dd>
              </dl>
            </li>
          </ul>
        </div>
      </div>
      <!-- // cost-list -->
      <!-- cost-total -->
      <div class="cost-total">
        <dl>
          <dt>월 납부금액(부가세 포함)</dt>
          <dd class="ut-color-point">21,000 원</dd>
        </dl>
        <span class="cost-desc">5G / 번호이동 / 요금제명</span>
      </div>
      <!-- // cost-total -->
    </div>
    <!-- // cost-wrap -->
    <!-- 안내 문구박스 영역 -->
    <MsfBox padding="16">
      <MsfTextList :items="['가입비 및 USIM비 등 기타요금은 별도 청구 됩니다.']" level="1" />
    </MsfBox>
    <MsfBox padding="16">
      <MsfTextList
        :items="[
          'eSIM비는 다운로드에 상관없이 최초 개통 시 1회 발생되며,<br/>프로파일 삭제 등으로 재 다운로드 시 추가 발생합니다.',
          '월 납부금액은 부가서비스 등의 사용에 따라 추가금액이 합산되어 청구 될 수 있습니다.',
          '월 납부금액은 부가세 포함 금액입니다.',
          '타사향(SK, LG U+ 등) 단말은 일부 서비스(MMS,영상통화, 교통카드 기능 등) 이용이 제한될 수 있습니다.',
        ]"
        level="1"
      />
    </MsfBox>
    <!-- // 안내 문구박스 영역 -->
  </MsfPopover>
</template>

<script setup>
const props = defineProps({
  isOpen: { type: Boolean, default: false },
})

const emit = defineEmits(['update:isOpen', 'triggerClick', 'close'])

//  닫기 이벤트
const onClose = (value) => {
  if (!value || props.isOpen) {
    emit('update:isOpen', false)
    emit('close')
  }
}

// 트리거 버튼 클릭
const handlePrevPayCost = () => {
  emit('triggerClick')
  onClose(false)
}
</script>

<style lang="scss" scoped>
.cost-wrap {
  --cost-inner-space-gap: #{rem(12px)}; // 사이 간격 지정
  .cost-list {
    width: 100%;
    .cost-item {
      width: 100%;
      &:not(:first-child) {
        border-top: 1px solid var(--color-gray-75);
        padding-top: var(--cost-inner-space-gap);
        margin-top: var(--cost-inner-space-gap);
      }
      .cost-title {
        font-size: var(--font-size-14);
        font-weight: var(--font-weight-bold);
        @include flex($h: space-between) {
          gap: var(--cost-inner-space-gap);
        }
        margin-bottom: var(--cost-inner-space-gap);
        & > dd {
          flex-shrink: 0;
          flex-grow: 0;
        }
      }
      ul.cost-infos {
        width: 100%;
        @include flex($d: column) {
          gap: var(--cost-inner-space-gap);
        }
        & > li {
          & > dl {
            @include flex($h: space-between, $v: baseline) {
              gap: var(--cost-inner-space-gap);
            }
            font-size: var(--font-size-14);
            font-weight: var(--font-weight-regular);
            & > dt {
              color: var(--color-gray-600);
            }
            & > dd {
              font-weight: var(--font-weight-medium);
              flex-shrink: 0;
              flex-grow: 0;
            }
          }
        }
      }
    }
  }
  .cost-total {
    margin-top: var(--cost-inner-space-gap);
    padding-top: var(--cost-inner-space-gap);
    border-top: var(--border-width-base) solid var(--color-gray-300);
    font-size: var(--font-size-20);
    font-weight: var(--font-weight-bold);
    & > dl {
      @include flex($h: space-between) {
        gap: var(--cost-inner-space-gap);
      }
      & > dt {
        @include flex($d: column) {
          gap: rem(4px);
        }
      }
      & > dd {
        flex-shrink: 0;
        flex-grow: 0;
      }
    }
    .cost-desc {
      display: block;
      font-size: var(--font-size-14);
      font-weight: var(--font-weight-regular);
      color: var(--color-gray-600);
    }
  }
}
</style>
