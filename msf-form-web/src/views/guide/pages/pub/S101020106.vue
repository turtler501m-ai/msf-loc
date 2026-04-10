<template>
  <MsfDialog
    :is-open="modelValue"
    title="주소 검색"
    v-bind="$attrs"
    @open="onOpen"
    @close="onClose"
  >
    <!-- 주소검색 -->
    <div class="address-search-root">
      <!-- 상단 검색영역 -->
      <div class="search-header">
        <MsfInput
          v-model="searchQuery"
          placeholder="도로명 주소, 건물명 또는 지번 입력"
          clearable
          @keypress.enter="onSearch"
        >
          <template #right-slot>
            <MsfButton class="btn-search" iconOnly="searchIcon" @click="onSearch" />
          </template>
        </MsfInput>
      </div>
      <!-- 하단 내용영역 -->
      <div class="viewport">
        <!-- 목록 노출 -->
        <template v-if="step === 'list'">
          <!-- 검색결과 목록 노출 -->
          <div v-if="results.length > 0" class="result-content">
            <MsfTitleArea level="3">
              <template #title>
                도로명 주소 검색 결과<span class="ut-color-point">({{ totalCount }}건)</span>
              </template>
            </MsfTitleArea>
            <div v-if="results.length > 0" class="result-content">
              <div class="addr-list">
                <div
                  v-for="(item, idx) in results"
                  :key="idx"
                  class="addr-card is-link"
                  @click="goToDetail(item)"
                  role="button"
                  @keydown.enter.stop.prevent="goToDetail(item)"
                  @keydown.space.stop.prevent="goToDetail(item)"
                  :tabindex="0"
                >
                  <div class="zip">{{ item.zipNo }}</div>
                  <div class="row">
                    <span class="tag road">도로명</span>
                    <p>{{ item.roadAddr }}</p>
                  </div>
                  <div class="row">
                    <span class="tag jibun">지번</span>
                    <p>{{ item.jibunAddr }}</p>
                  </div>
                </div>
              </div>
              <div class="pagination-wrapper">
                <MsfPagination
                  v-model:page="currentPage"
                  v-model:total="totalCount"
                  :items-per-page="5"
                  @change="onSearch"
                />
              </div>
            </div>
          </div>
          <!-- // 검색결과 목록 노출 -->
          <!-- 비어있는 케이스 -->
          <div v-else class="empty-case">
            <!-- 검색결과 없음 -->
            <MsfTitleArea level="3" v-if="isSearchPerformed">
              <template #title>
                도로명 주소 검색 결과<span class="ut-color-point">({{ totalCount }}건)</span>
              </template>
            </MsfTitleArea>
            <div class="empty-view" v-if="isSearchPerformed">
              <span class="empty-icon"> <MsfIcon name="noDataIcon" /></span>
              <p>검색 결과가 없습니다.</p>
            </div>
            <!-- // 검색결과 없음 -->
            <!-- 검색어 예시 -->
            <div class="guide-view">
              <section class="guide-section">
                <MsfTitleArea title="검색어 예시" level="3" />
                <ul class="example-list">
                  <li>
                    <p>도로명 + 건물번호</p>
                    <span>예) 반포대로 58</span>
                  </li>
                  <li>
                    <p>지역명(동/리) + 번지</p>
                    <span>예) 서초동 1337-22</span>
                  </li>
                  <li>
                    <p>건물명(아파트) 검색</p>
                    <span>예) 반포자이</span>
                  </li>
                </ul>
              </section>
              <MsfBox class="notice-box">
                <em class="notice-box-tit">
                  <MsfIcon name="titleInfo" />
                  알려드립니다.
                </em>
                <MsfTextList
                  :items="[
                    '건물명은 각 자치단체에서 등록한 경우 조회가 가능합니다.',
                    '상세검색을 이용하시면 보다 정확한 결과를 얻으실 수 있습니다.',
                  ]"
                  type="dash"
                  level="2"
                  class="ut-mt-8"
                />
              </MsfBox>
            </div>
            <!-- // 검색어 예시 -->
          </div>
          <!-- // 비어있는 케이스 -->
        </template>
        <!-- // 목록 노출 -->
        <!-- 주소 선택 후 -->
        <template v-else-if="step === 'detail'">
          <MsfTitleArea level="3">
            <template #title> 도로명 주소 </template>
          </MsfTitleArea>
          <div class="detail-content">
            <div class="addr-card selected">
              <div class="zip">{{ selectedAddress?.zipNo }}</div>
              <div class="row">
                <span class="tag road">도로명</span>
                <p>{{ selectedAddress?.roadAddr }}</p>
              </div>
            </div>
            <div class="detail-input">
              <MsfInput
                v-model="detailAddress"
                placeholder="상세주소(예 1202동 101호 / 종로빌딩 1층)"
              />
            </div>
          </div>
        </template>
        <!-- // 주소 선택 후 -->
      </div>
      <!-- // 하단 내용영역 -->
    </div>
    <!-- // 주소검색 -->
    <template #footer v-if="step === 'detail'">
      <MsfButtonGroup>
        <MsfButton variant="primary" class="btn-confirm" @click="onConfirm">주소 입력</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({ modelValue: Boolean })
const emit = defineEmits(['update:modelValue', 'confirm', 'open', 'close'])

// 샘플
const MOCK_DATA = [
  {
    zipNo: '06627',
    roadAddr: '서울특별시 서초구 강남대로 321(서초동, 서초동 대우디오빌프라임)',
    jibunAddr:
      '서울특별시 서초구 서초동 1337-22 서초동 대우디오빌프라임 서초동 대우디오빌프라임 서초동 대우디오빌프라임서초동 대우디오빌프라임서초동 대우디오빌프라임 서초동 대우디오빌프라임',
  },
  {
    zipNo: '06628',
    roadAddr: '서울특별시 서초구 서초대로 456',
    jibunAddr: '서울특별시 서초구 서초동 123-45',
  },
  {
    zipNo: '06629',
    roadAddr: '서울특별시 서초구 효령로 789',
    jibunAddr: '서울특별시 서초구 서초동 987-65',
  },
  {
    zipNo: '04524',
    roadAddr: '서울특별시 중구 세종대로 110',
    jibunAddr: '서울특별시 중구 태평로1가 31',
  },
  {
    zipNo: '03063',
    roadAddr: '서울특별시 종로구 사직로 161',
    jibunAddr: '서울특별시 종로구 세종로 1-1',
  },
  {
    zipNo: '06629',
    roadAddr: '서울특별시 서초구 효령로 789',
    jibunAddr: '서울특별시 서초구 서초동 987-65',
  },
  {
    zipNo: '04524',
    roadAddr: '서울특별시 중구 세종대로 110',
    jibunAddr: '서울특별시 중구 태평로1가 31',
  },
  {
    zipNo: '03063',
    roadAddr: '서울특별시 종로구 사직로 161',
    jibunAddr: '서울특별시 종로구 세종로 1-1',
  },
]

const step = ref('list')
const searchQuery = ref('')
const isSearchPerformed = ref(false)
const currentPage = ref(1)
const totalCount = ref(0)
const results = ref([])
const selectedAddress = ref(null)
const detailAddress = ref('')

const onOpen = () => resetAll()

const resetAll = () => {
  step.value = 'list'
  searchQuery.value = ''
  isSearchPerformed.value = false
  results.value = []
  totalCount.value = 0
  detailAddress.value = ''
}

const onSearch = () => {
  const query = searchQuery.value.trim()
  if (!query) return

  isSearchPerformed.value = true
  step.value = 'list'

  // 검색 필터링 로직
  const filtered = MOCK_DATA.filter(
    (item) => item.roadAddr.includes(query) || item.jibunAddr.includes(query),
  )

  totalCount.value = filtered.length
  results.value = filtered.slice((currentPage.value - 1) * 5, currentPage.value * 5)
}

const goToDetail = (item) => {
  selectedAddress.value = item
  step.value = 'detail'
}

const onConfirm = () => {
  // 1. 전달할 데이터 객체 생성
  const finalAddressData = {
    ...selectedAddress.value, // zipNo, roadAddr, jibunAddr 등 기존 정보
    detailAddr: detailAddress.value, // 사용자가 직접 입력한 상세 주소
  }
  // 2. 콘솔에 출력 (디버깅용)
  console.log('입력된 주소 내역:', finalAddressData)

  // 3. 부모 컴포넌트로 데이터 전송 및 팝업 닫기
  emit('confirm', finalAddressData)
  onClose()
}

const onClose = () => emit('update:modelValue', false)
</script>

<style lang="scss" scoped>
.address-search-root {
  --address-result-text-color: var(--color-gray-600);

  width: 100%;
  @include flex($d: column) {
    gap: rem(24px);
  }
  .search-header {
    .btn-search {
      @include flex($display: inline-flex, $v: center, $h: center);
      height: 100%;
      padding: 0;
      border: none;
    }
  }
  .viewport {
    @include flex($d: column);
  }
  // 주소 카드 공통 스타일 (목록/상세)
  .addr-card {
    padding: rem(16px) 0;
    border-bottom: var(--border-width-base) solid var(--color-gray-75);
    &:first-child {
      padding-top: 0;
    }
    &:last-child {
      border-bottom: none;
    }
    &.is-link {
      cursor: pointer;
    }
    &.selected {
      border-bottom: none;
      padding-bottom: rem(16px);
    }
    .zip {
      font-size: var(--font-size-16);
      font-weight: var(--font-weight-bold);
      color: var(--color-primary-base);
      margin-bottom: rem(16px);
    }
    .row {
      @include flex() {
        gap: rem(4px);
      }
      padding-block: rem(8px);
      & + .row {
        margin-top: rem(8px);
      }
      .tag {
        flex-shrink: 0;
        padding: rem(2px);
        width: rem(52px);
        height: rem(22px);
        border-radius: var(--border-radius-base);
        font-size: var(--font-size-14);
        font-weight: var(--font-weight-regular);
        line-height: var(--line-height-fit);
        @include flex($display: inline-flex, $v: center, $h: center);
        &.road {
          color: var(--color-white);
          background: var(--color-accent2-base);
        }
        &.jibun {
          background: var(--color-gray-500);
          color: var(--color-white);
        }
      }
      p {
        font-size: 14px;
        margin: 0;
        line-height: 1.4;
        color: var(--color-gray-600);
      }
    }
  }
  // 초기 가이드 뷰
  .guide-view {
    .guide-section {
      margin-bottom: rem(16px);
      .title {
        font-size: 16px;
        font-weight: 700;
        margin-bottom: 12px;
        border-bottom: 1px solid #eee;
        padding-bottom: 10px;
      }
      .example-list {
        li {
          margin-bottom: rem(16px);
          padding-bottom: rem(16px);
          font-size: var(--font-size-16);
          border-bottom: var(--border-width-base) solid var(--color-gray-75);
          &:last-child {
            border-bottom: none;
            margin-bottom: 0;
          }
          p {
            color: var(--color-gray-600);
          }
          span {
            color: var(--color-accent2-base);
          }
        }
      }
    }
    .notice-box {
      .notice-box-tit {
        @include flex($v: center) {
          gap: rem(2px);
        }
        font-size: var(--font-size-18);
        font-weight: var(--font-weight-bold);
        color: var(--color-primary-base);
        margin-bottom: rem(8px);
      }
      ul {
        list-style: none;
        padding: 0;
        font-size: 13px;
        color: #666;
        line-height: 1.6;
      }
    }
  }

  // 결과 없음
  .empty-view {
    height: rem(180px);
    @include flex($v: center, $h: center, $d: column) {
      gap: rem(8px);
    }
    .empty-icon {
      width: rem(64px);
      height: rem(64px);
      background: var(--color-gray-75);
      border-radius: var(--border-radius-max);
      @include flex($display: inline-flex, $v: center, $h: center);
    }
    font-size: var(--font-size-16);
    font-weight: var(--font-weight-medium);
    color: var(--color-gray-600);
  }

  // 상세 입력
  .detail-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    .detail-input {
      margin-top: rem(16px);
    }
    .action-area {
      margin-top: auto;
      padding: 40px 0 10px;
      text-align: center;
      .btn-confirm {
        position: relative;
      }
    }
  }

  .pagination-wrapper {
    @include flex($v: center, $h: center);
  }
}
</style>
