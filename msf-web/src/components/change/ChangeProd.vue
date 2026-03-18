<template>
  <div class="page-step-panel space-y-6">
    <h3 class="section-title">상품</h3>

    <template v-if="selectedOptions.length === 0">
      <div class="p-4 bg-amber-50 border border-amber-200 rounded-lg">
        이전 단계에서 변경 항목을 선택해 주세요.
      </div>
    </template>

    <template v-else>
      <!-- 상품 그리드 로딩 중 (X20 현재 가입 부가서비스 조회) -->
      <div v-if="additionLoading" class="flex items-center gap-2 p-4 bg-gray-50 rounded-lg border border-gray-200 mb-4">
        <span class="animate-pulse text-gray-500">현재 가입 부가서비스 조회 중...</span>
      </div>
      <!-- 1줄 그리드: 무선데이터차단 → 그 밑에 정보료 (세로 배치) -->
      <div v-if="(hasOption('WIRELESS_BLOCK') || hasOption('INFO_LIMIT')) && !additionLoading" class="grid grid-cols-1 gap-4 mb-4">
        <!-- 무선데이터차단 서비스 (3.3.1) - 서브라벨 + 오른쪽 버튼 2개 -->
        <section v-if="hasOption('WIRELESS_BLOCK')" class="product-card">
          <h4 class="product-section-title">무선데이터차단 서비스</h4>
          <div class="product-form-row">
            <span class="product-row-label">무선데이터차단 이용 여부</span>
            <div class="product-row-input flex flex-wrap gap-2 items-center">
              <button
                type="button"
                class="product-btn-choice"
                :class="{ 'product-btn-choice--selected': productForm.wirelessBlock === 'use' }"
                :disabled="wirelessBlockDisableUse"
                @click="productForm.wirelessBlock = 'use'"
              >
                무선데이터 이용
              </button>
              <button
                type="button"
                class="product-btn-choice"
                :class="{ 'product-btn-choice--selected': productForm.wirelessBlock === 'block' }"
                :disabled="wirelessBlockDisableBlock"
                @click="productForm.wirelessBlock = 'block'"
              >
                무선데이터차단 서비스 이용
              </button>
            </div>
          </div>
          <p v-if="wirelessStatusMsg" class="mt-2 text-sm text-amber-600">{{ wirelessStatusMsg }}</p>
        </section>
        <!-- 정보료 상한금액 - 서브라벨 + 버튼 형태, 현재 이용 금액은 선택 불가 -->
        <section v-if="hasOption('INFO_LIMIT')" class="product-card">
          <h4 class="product-section-title">정보료 상한금액 설정/변경</h4>
          <div class="product-form-row">
            <span class="product-row-label">정보료 상한금액</span>
            <div class="product-row-input flex flex-wrap gap-2 items-center">
              <button
                v-for="opt in infoLimitOptions"
                :key="opt.value"
                type="button"
                class="product-btn-choice"
                :class="[
                  { 'product-btn-choice--selected': productForm.infoLimit === opt.value },
                  { 'product-btn-choice--disabled': isInfoLimitOptionDisabled(opt.value) },
                ]"
                :disabled="isInfoLimitOptionDisabled(opt.value)"
                @click="productForm.infoLimit = opt.value"
              >
                {{ opt.label }}
              </button>
            </div>
          </div>
          <p v-if="infoLimitStatusMsg" class="mt-2 text-sm text-amber-600">{{ infoLimitStatusMsg }}</p>
        </section>
      </div>

      <!-- 상품 그리드: 그 외 서비스 카드 (2열) -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <!-- 부가서비스 신청/변경 (3.3.3): 화면설계서 - 무료/유로 표시 후 추가/삭제 버튼 아래 -->
        <section v-if="hasOption('ADDITION')" class="product-card md:col-span-2">
          <h4 class="product-section-title">부가서비스 신청/변경</h4>
          <!-- 무료 부가서비스: 조회된 결과 / 유로 부가서비스: 가입가능한 부가서비스 -->
          <div v-if="!additionLoading" class="space-y-3">
            <div class="product-form-row">
              <span class="product-row-label">무료 부가서비스</span>
              <div class="product-row-input text-sm text-gray-700">
                <template v-if="freeAdditionItems.length">
                  <span v-for="(it, i) in freeAdditionItems" :key="it.soc || i" class="inline-block mr-2 mb-1">
                    {{ it.socDescription || it.soc }}{{ i < freeAdditionItems.length - 1 ? ',' : '' }}
                  </span>
                </template>
                <span v-else class="text-gray-500">조회된 결과 없음</span>
              </div>
            </div>
            <div class="product-form-row">
              <span class="product-row-label">유로 부가서비스</span>
              <div class="product-row-input text-sm text-gray-700">
                <template v-if="availableAdditionItems.length">
                  <span v-for="(it, i) in availableAdditionItems" :key="it.soc || i" class="inline-block mr-2 mb-1">
                    {{ it.socDescription || it.soc }}{{ it.socRateValue ? ` (${it.socRateValue})` : '' }}{{ i < availableAdditionItems.length - 1 ? ',' : '' }}
                  </span>
                </template>
                <span v-else class="text-gray-500">가입 가능한 부가서비스 없음</span>
              </div>
            </div>
            <div class="product-form-row">
              <span class="product-row-label"></span>
              <div class="product-row-input">
                <button
                  type="button"
                  class="px-4 py-2 rounded border border-teal-600 text-teal-600 hover:bg-teal-50 text-sm"
                  @click="additionEditOpen = true"
                >
                  추가/삭제
                </button>
              </div>
            </div>
          </div>
        </section>

        <!-- 요금상품 변경 (3.3.4) - 영역 박스 오른쪽 끝까지, 라벨+입력 동일 패턴 -->
        <section v-if="hasOption('RATE_CHANGE')" class="product-card md:col-span-2">
          <h4 class="product-section-title">요금상품 변경</h4>
          <div class="space-y-4">
            <div v-if="farPriceLoading" class="text-sm text-gray-500 mb-2">요금제 목록 조회 중...</div>
            <div class="product-form-row">
              <span class="product-row-label">요금제</span>
              <div class="product-row-input">
                <select
                  v-model="productForm.recommendedRatePlan"
                  class="product-select w-full max-w-xs"
                >
                  <option value="">추천요금제</option>
                  <option
                    v-for="it in farPriceItems"
                    :key="it.code"
                    :value="it.code"
                  >
                    {{ it.name }}{{ it.baseAmount ? ` (${it.baseAmount})` : '' }}
                  </option>
                </select>
              </div>
            </div>
            <div class="product-form-row">
              <span class="product-row-label"></span>
              <div class="product-row-input">
                <select
                  v-model="productForm.ratePlanSearchResult"
                  class="product-select w-full max-w-md"
                >
                  <option value="">요금제 조회 결과</option>
                  <option
                    v-for="it in farPriceItems"
                    :key="it.code"
                    :value="it.code"
                  >
                    {{ it.name }}{{ it.baseAmount ? ` (${it.baseAmount})` : '' }}
                  </option>
                </select>
              </div>
            </div>
            <div class="product-form-row">
              <span class="product-row-label">변경일시</span>
              <div class="product-row-input flex flex-wrap gap-2 items-center">
                <button
                  type="button"
                  class="product-btn-choice"
                  :class="{ 'product-btn-choice--selected': productForm.rateChangeSchedule === 'reserve' }"
                  @click="productForm.rateChangeSchedule = 'reserve'"
                >
                  예약(익월1일)
                </button>
                <button
                  type="button"
                  class="product-btn-choice"
                  :class="{ 'product-btn-choice--selected': productForm.rateChangeSchedule === 'immediate' }"
                  @click="productForm.rateChangeSchedule = 'immediate'"
                >
                  즉시
                </button>
              </div>
            </div>
          </div>
        </section>

        <!-- 번호변경 (3.3.5) -->
        <section v-if="hasOption('NUM_CHANGE')" class="product-card">
          <h4 class="product-card-title">번호변경</h4>
          <p class="text-sm text-gray-500 mb-2">신규 희망번호 조회 (X32 연동 예정). 예약번호 4자리 입력 후 번호조회.</p>
          <div class="flex gap-2">
            <input
              v-model="productForm.numChange"
              type="text"
              placeholder="010-****-선택번호"
              class="flex-1 rounded border border-gray-300 px-3 py-2 text-sm"
              readonly
            />
            <button type="button" class="px-4 py-2 rounded bg-teal-600 text-white whitespace-nowrap text-sm" @click="numberSearchOpen = true">
              번호조회
            </button>
            <button
              v-if="productForm.numChange"
              type="button"
              class="px-4 py-2 rounded border border-gray-400 text-sm"
              @click="productForm.numChange = ''"
            >
              선택취소
            </button>
          </div>
        </section>

        <!-- 분실복구/일시정지해제 (3.3.6) -->
        <section v-if="hasOption('LOST_RESTORE')" class="product-card">
          <h4 class="product-card-title">분실복구/일시정지해제 신청</h4>
          <p v-if="!canCheckPause" class="text-sm text-gray-500">
            고객 정보(휴대폰 인증) 완료 후 일시정지 이력 및 분실신고 상태를 확인할 수 있습니다.
          </p>
          <div v-else class="space-y-3">
            <div class="flex items-center gap-2">
              <button
                type="button"
                class="product-btn-choice"
                :disabled="pauseLoading"
                @click="doPauseCheck"
              >
                {{ pauseLoading ? '조회 중...' : '일시정지 이력/상태 조회' }}
              </button>
              <span v-if="pauseInfo && pauseInfo.subStatus" class="text-sm text-gray-700">
                현재 상태:
                <strong class="text-gray-900">
                  {{
                    pauseInfo.subStatus === 'S'
                      ? '일시정지'
                      : pauseInfo.subStatus === 'A'
                        ? '이용 중'
                        : pauseInfo.subStatus
                  }}
                </strong>
              </span>
            </div>
            <div v-if="pauseInfo" class="text-xs text-gray-600 space-y-1">
              <p v-if="pauseInfo.expectSpDate">
                일시정지 만료 예정일: {{ pauseInfo.expectSpDate }}
              </p>
              <p>
                일시정지 이용/잔여: {{ pauseInfo.susCnt || '0' }}회 /
                {{ pauseInfo.susDays || '0' }}일 (잔여 {{ pauseInfo.remainSusCnt || '0' }}회)
              </p>
              <p v-if="pauseInfo.lostRunMode === 'U'" class="text-amber-600">
                현재 분실신고 상태입니다. 분실복구 후 이용이 가능합니다.
              </p>
              <p v-if="pauseInfo.canSuspendRestore === false && !pauseInfo.lostRunMode" class="text-amber-600">
                온라인으로 일시정지 해제가 불가한 상태입니다. 고객센터(1899-5000)로 문의해 주세요.
              </p>
            </div>
            <div class="product-form-row">
              <span class="product-row-label">해제 비밀번호</span>
              <div class="product-row-input flex items-center gap-2">
                <input
                  v-model="pausePassword"
                  type="password"
                  maxlength="8"
                  placeholder="비밀번호를 입력(4~8자)"
                  class="w-48 rounded border border-gray-300 px-3 py-2 text-sm"
                />
                <button
                  type="button"
                  class="product-btn-choice"
                  :disabled="!pausePassword || pauseApplyLoading || !pauseInfo"
                  @click="onPauseApply"
                >
                  {{ pauseApplyLoading ? '처리 중...' : '확인' }}
                </button>
              </div>
            </div>
            <p v-if="pauseErrorMsg" class="text-sm text-red-600">
              {{ pauseErrorMsg }}
            </p>
          </div>
        </section>

        <!-- 단말보험 (3.3.7) -->
        <section v-if="hasOption('INSURANCE')" class="product-card">
          <h4 class="product-card-title">단말보험 가입</h4>
          <p class="text-sm text-gray-500">단말보험 가입 정보 조회 (API 미확정)</p>
        </section>

        <!-- 핸드폰 기변 (3.3.8) -->
        <section v-if="hasOption('PHONE_CHANGE')" class="product-card">
          <h4 class="product-card-title">핸드폰 기변/일반 기변</h4>
          <p class="text-sm text-gray-500">IMEI 유효성 체크 (API 미확정)</p>
        </section>

        <!-- USIM 변경 (3.3.9) -->
        <section v-if="hasOption('USIM_CHANGE')" class="product-card">
          <h4 class="product-card-title">USIM 변경</h4>
          <p class="text-sm text-gray-500 mb-2">유심번호 유효성 체크 (X85 연동 예정)</p>
          <input
            v-model="productForm.usimChange"
            type="text"
            placeholder="유심번호 입력"
            class="w-full rounded border border-gray-300 px-3 py-2 text-sm"
          />
        </section>

        <!-- 데이터쉐어링 (3.3.10, 분석 43: X69/X71/X70) -->
        <section v-if="hasOption('DATA_SHARING')" class="product-card">
          <h4 class="product-card-title">데이터쉐어링 가입/해지</h4>
          <p v-if="!dataSharingListLoaded && !dataSharingListLoading" class="text-sm text-gray-500">
            결합 중인 회선 목록을 조회한 후 가입 가능 여부를 확인할 수 있습니다.
          </p>
          <div v-if="dataSharingListLoading" class="flex items-center gap-2 py-2 text-sm text-gray-500">
            <span class="animate-pulse">결합 목록 조회 중...</span>
          </div>
          <template v-else>
            <!-- 결합 중인 회선 목록 (X71) -->
            <div v-if="dataSharingList.length > 0" class="mt-3">
              <h5 class="text-sm font-medium text-gray-700 mb-2">결합 중인 데이터쉐어링 회선</h5>
              <div class="border border-gray-200 rounded overflow-hidden">
                <table class="w-full text-sm text-left">
                  <thead class="bg-gray-50 text-gray-600">
                    <tr>
                      <th class="px-3 py-2">전화번호</th>
                      <th class="px-3 py-2">결합일</th>
                      <th class="px-3 py-2 w-20">처리</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(item, idx) in dataSharingList" :key="idx" class="border-t border-gray-100">
                      <td class="px-3 py-2">{{ item.svcNo || '-' }}</td>
                      <td class="px-3 py-2">{{ formatEfctStDt(item.efctStDt) }}</td>
                      <td class="px-3 py-2">
                        <button
                          type="button"
                          class="text-red-600 hover:underline text-sm"
                          :disabled="dataSharingCancelLoading === item.svcNo"
                          @click="onDataSharingCancel(item)"
                        >
                          {{ dataSharingCancelLoading === item.svcNo ? '처리 중' : '해지' }}
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            <!-- 가입 가능 여부 확인 (X69) -->
            <div class="mt-3 flex flex-wrap gap-2 items-center">
              <button
                type="button"
                class="product-btn-choice"
                :disabled="!canCheckDataSharing || dataSharingCheckLoading"
                @click="doDataSharingCheck"
              >
                {{ dataSharingCheckLoading ? '확인 중...' : '가입 가능 여부 확인' }}
              </button>
              <template v-if="dataSharingCheckResult !== null">
                <span v-if="dataSharingCheckResult.canJoin" class="text-sm text-green-700">가입 가능합니다.</span>
                <span v-else class="text-sm text-amber-600">{{ dataSharingCheckResult.RESULT_MSG || '가입이 불가합니다.' }}</span>
              </template>
            </div>
            <p v-if="!canCheckDataSharing && !dataSharingList.length" class="text-sm text-gray-500 mt-2">
              고객 정보(휴대폰 인증) 완료 후 확인할 수 있습니다.
            </p>
            <p class="text-xs text-gray-500 mt-2">
              데이터쉐어링 가입은 대표 회선당 1개 회선만 가능하며, 신규 가입(셀프개통)은 별도 메뉴에서 진행해 주세요.
            </p>
          </template>
        </section>

        <!-- 아무나 SOLO 결합 (분석 44·45) -->
        <section v-if="hasOption('ANY_SOLO')" class="product-card">
          <h4 class="product-card-title">아무나 SOLO 결합</h4>
          <p v-if="!anySoloCheckResult && !anySoloLoading" class="text-sm text-gray-500">
            결합 가능 여부를 확인한 후 신청할 수 있습니다.
          </p>
          <div v-if="anySoloLoading" class="flex items-center gap-2 py-2 text-sm text-gray-500">
            <span class="animate-pulse">결합 가능 여부 확인 중...</span>
          </div>
          <template v-else-if="anySoloCheckResult">
            <template v-if="anySoloCheckResult.RESULT_CODE === 'SUCCESS'">
              <p class="text-sm text-gray-700 mt-2">
                아무나 SOLO 결합을 신청하실 경우
                <strong class="text-gray-900">"{{ anySoloCheckResult.RESULT_SERVICE_NM }}"</strong>이 제공됩니다.
              </p>
              <p v-if="anySoloCheckResult.IS_COMBIN" class="text-sm text-amber-600 mt-1">
                결합은 가능하나, 결합 혜택이 추가로 제공되지 않습니다. (이미 다른 결합에 가입된 회선)
              </p>
              <div class="mt-3 flex flex-wrap gap-2">
                <button
                  type="button"
                  class="product-btn-choice product-btn-choice--selected"
                  :disabled="anySoloRegLoading"
                  @click="onAnySoloReg"
                >
                  {{ anySoloRegLoading ? '처리 중...' : '아무나 SOLO 결합 신청' }}
                </button>
              </div>
            </template>
            <template v-else>
              <p class="text-sm text-red-600 mt-2">{{ anySoloCheckResult.RESULT_MSG || '결합 가능 여부를 확인할 수 없습니다.' }}</p>
            </template>
          </template>
          <div v-else class="mt-2">
            <button
              type="button"
              class="product-btn-choice"
              :disabled="!canCheckAnySolo || anySoloLoading"
              @click="doAnySoloCheck"
            >
              결합 가능 여부 확인
            </button>
            <p v-if="!canCheckAnySolo && !anySoloCheckResult" class="text-sm text-gray-500 mt-2">
              고객 정보(휴대폰 인증) 완료 후 확인할 수 있습니다.
            </p>
          </div>
        </section>
      </div>
    </template>

    <!-- 부가서비스 추가/삭제 팝업 (S102030109) -->
    <McpAdditionEditPop
      v-model:open="additionEditOpen"
      @confirm="onAdditionEditConfirm"
      @open-option="onAdditionOpenOption"
    />
    <!-- 부가정보 설정 팝업: 불법TM수신차단(S102030102) -->
    <McpTmBlockPop v-model:open="tmBlockPopOpen" @confirm="onTmBlockConfirm" />
    <!-- 번호도용 차단 서비스(S102030103) -->
    <McpNumberTheftBlockPop v-model:open="numberTheftBlockPopOpen" @confirm="onNumberTheftConfirm" />
    <!-- (신)로밍 하루종일 ON(S102030104) -->
    <McpRoamingAllDayPop v-model:open="roamingPopOpen" @confirm="onRoamingConfirm" />
    <!-- 신규번호 검색 팝업(S102030105) -->
    <McpNumberSearchPop v-model:open="numberSearchOpen" @confirm="onNumberSearchConfirm" />
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useServiceChangeFormStore } from '@/stores/service_change_form'
import {
  getCurrentAddition,
  regAddition,
  cancelAddition,
  getFarPriceList,
  checkCombineSelf,
  regCombineSelf,
  getDataSharingList,
  checkDataSharing,
  cancelDataSharing,
  checkPause,
  applyPause,
} from '@/api/serviceChange'
import McpAdditionEditPop from '@/components/commons/McpAdditionEditPop.vue'
import McpTmBlockPop from '@/components/commons/McpTmBlockPop.vue'
import McpNumberTheftBlockPop from '@/components/commons/McpNumberTheftBlockPop.vue'
import McpRoamingAllDayPop from '@/components/commons/McpRoamingAllDayPop.vue'
import McpNumberSearchPop from '@/components/commons/McpNumberSearchPop.vue'

const emit = defineEmits(['update:complete'])
const formStore = useServiceChangeFormStore()

const selectedOptions = computed(() => formStore.selectedOptions || [])

const productForm = ref({
  wirelessBlock: null, // 'use' | 'block' (화면설계서 3.3.1)
  infoLimit: null, // '0'|'3000'|'10000'|'20000'|'30000' (3.3.2)
  rateChange: '',
  recommendedRatePlan: '', // 요금제 라벨 우측 추천요금제 콤보
  ratePlanSearchResult: '', // 요금제 조회 결과 콤보
  rateChangeSchedule: null, // 'reserve' | 'immediate' (변경일시)
  numChange: '',
  usimChange: '',
  additions: [],
})

// 정보료 상한금액 옵션 (화면설계서 3.3.2)
const infoLimitOptions = [
  { value: '0', label: '0원' },
  { value: '3000', label: '3천원' },
  { value: '10000', label: '1만원' },
  { value: '20000', label: '2만원' },
  { value: '30000', label: '3만원' },
]

// 가입 가능 부가서비스 목록 조회(X20) 후 세팅. null=미조회(디폴트 없음)
const wirelessBlockCurrent = ref(null) // 'use' | 'block' | null
const infoLimitCurrent = ref(null) // '0'|'3000'|'10000'|'20000'|'30000'|null
const additionLoading = ref(false) // X20 조회 중
const currentAdditionItems = ref([]) // X20 현재 가입 부가서비스 목록 (조회된 결과)
const availableAdditionItems = ref([]) // 가입 가능한 부가서비스 목록 (유로)
const farPriceLoading = ref(false)
const farPriceItems = ref([]) // FarPrice 변경 가능 요금제 목록

// 무료 부가서비스: X20 조회 결과 중 socRateValue가 Free/0/빈값인 항목
const freeAdditionItems = computed(() => {
  const items = currentAdditionItems.value || []
  return items.filter((it) => {
    const v = (it?.socRateValue || '').toString().trim().toLowerCase()
    return v === '' || v === 'free' || v === '0' || v === '0원'
  })
})

// 무선데이터차단: 현재 이용 중인 옵션은 선택 불가, 반대만 선택 가능
const wirelessStatusMsg = computed(() => {
  if (wirelessBlockCurrent.value === 'use') return '현재 무선데이터 이용 중입니다.'
  if (wirelessBlockCurrent.value === 'block') return '현재 무선데이터차단 서비스 이용 중입니다.'
  return ''
})
const wirelessBlockDisableUse = computed(() => wirelessBlockCurrent.value === 'use') // 이미 이용 중 → "무선데이터 이용" 비활성화
const wirelessBlockDisableBlock = computed(() => wirelessBlockCurrent.value === 'block') // 이미 차단 이용 중 → "무선데이터차단 서비스 이용" 비활성화

// 정보료: 현재 설정된 금액 옵션은 선택 불가
const infoLimitStatusMsg = computed(() => (infoLimitCurrent.value != null ? '현재 설정된 정보료 상한금액 입니다.' : ''))
function isInfoLimitOptionDisabled(value) {
  return infoLimitCurrent.value != null && infoLimitCurrent.value === value
}

// 팝업 open 상태
const additionEditOpen = ref(false)
const tmBlockPopOpen = ref(false)
const numberTheftBlockPopOpen = ref(false)
const roamingPopOpen = ref(false)
const numberSearchOpen = ref(false)

// 아무나 SOLO 결합 (분석 44·45)
const anySoloCheckResult = ref(null)
const anySoloLoading = ref(false)
const anySoloRegLoading = ref(false)
const canCheckAnySolo = computed(() => {
  const cf = formStore.customerForm || {}
  return !!(cf.ncn && cf.phone && cf.custId && cf.phoneAuthCompleted)
})
async function doAnySoloCheck() {
  const cf = formStore.customerForm || {}
  const pf = formStore.productForm || {}
  if (!cf.ncn || !cf.phone || !cf.custId) return
  anySoloLoading.value = true
  anySoloCheckResult.value = null
  try {
    const res = await checkCombineSelf({
      ncn: cf.ncn,
      ctn: cf.phone,
      custId: cf.custId,
      rateCd: pf.rateChange || pf.ratePlanSearchResult || ''
    })
    anySoloCheckResult.value = res || null
  } catch (e) {
    anySoloCheckResult.value = { RESULT_CODE: 'ERROR', RESULT_MSG: e?.message || '확인에 실패했습니다.' }
  } finally {
    anySoloLoading.value = false
  }
}
async function onAnySoloReg() {
  const cf = formStore.customerForm || {}
  const pf = formStore.productForm || {}
  if (!cf.ncn || !cf.phone || !cf.custId) return
  if (!confirm('아무나 SOLO 결합을 신청하시겠습니까?')) return
  anySoloRegLoading.value = true
  try {
    const res = await regCombineSelf({
      ncn: cf.ncn,
      ctn: cf.phone,
      custId: cf.custId,
      rateCd: pf.rateChange || pf.ratePlanSearchResult || '',
      rateNm: pf.rateNm || '',
      subLinkName: cf.name || '',
      contractNum: cf.ncn || ''
    })
    if (res && res.RESULT_CODE === 'SUCCESS') {
      alert('아무나 SOLO 결합 신청이 완료되었습니다.')
      anySoloCheckResult.value = null
    } else {
      alert(res?.RESULT_MSG || '결합 신청에 실패하였습니다. 다시 시도해 주시기 바랍니다.')
    }
  } catch (e) {
    alert(e?.message || '결합 신청에 실패하였습니다. 다시 시도해 주시기 바랍니다.')
  } finally {
    anySoloRegLoading.value = false
  }
}

// 데이터쉐어링 (분석 43: X69/X71/X70)
const dataSharingList = ref([])
const dataSharingListLoading = ref(false)
const dataSharingListLoaded = ref(false)
const dataSharingCheckResult = ref(null)
const dataSharingCheckLoading = ref(false)
const dataSharingCancelLoading = ref(null) // svcNo when loading
const canCheckDataSharing = computed(() => {
  const cf = formStore.customerForm || {}
  return !!(cf.ncn && cf.phone && cf.custId && cf.phoneAuthCompleted)
})

// 분실복구/일시정지해제 (분석 47: X26/X28/X33/X30/X35)
const pauseInfo = ref(null)
const pauseLoading = ref(false)
const pauseApplyLoading = ref(false)
const pausePassword = ref('')
const pauseErrorMsg = ref('')
const canCheckPause = computed(() => {
  const cf = formStore.customerForm || {}
  return !!(cf.ncn && cf.phone && cf.custId && cf.phoneAuthCompleted)
})

async function doPauseCheck() {
  const cf = formStore.customerForm || {}
  if (!cf.ncn || !cf.phone || !cf.custId) return
  pauseLoading.value = true
  pauseErrorMsg.value = ''
  try {
    const res = await checkPause({ ncn: cf.ncn, ctn: cf.phone, custId: cf.custId })
    if (res && res.RESULT_CODE === 'SUCCESS') {
      pauseInfo.value = res
    } else {
      pauseInfo.value = null
      pauseErrorMsg.value = res?.RESULT_MSG || '일시정지 이력/상태 조회에 실패했습니다.'
    }
  } catch (e) {
    pauseInfo.value = null
    pauseErrorMsg.value = e?.message || '일시정지 이력/상태 조회에 실패했습니다.'
  } finally {
    pauseLoading.value = false
  }
}

async function onPauseApply() {
  const cf = formStore.customerForm || {}
  if (!cf.ncn || !cf.phone || !cf.custId) return
  if (!pausePassword.value) {
    pauseErrorMsg.value = '해제 비밀번호를 입력해 주세요.'
    return
  }
  if (!confirm('분실복구/일시정지해제를 진행하시겠습니까?')) return

  pauseApplyLoading.value = true
  pauseErrorMsg.value = ''
  try {
    const res = await applyPause({
      ncn: cf.ncn,
      ctn: cf.phone,
      custId: cf.custId,
      password: pausePassword.value,
    })
    if (res && res.RESULT_CODE === 'SUCCESS') {
      alert('분실복구/일시정지해제 처리가 완료되었습니다.')
      await doPauseCheck()
    } else {
      pauseErrorMsg.value = res?.RESULT_MSG || '분실복구/일시정지해제 처리에 실패했습니다.'
    }
  } catch (e) {
    pauseErrorMsg.value = e?.message || '분실복구/일시정지해제 처리에 실패했습니다.'
  } finally {
    pauseApplyLoading.value = false
  }
}
function formatEfctStDt(efctStDt) {
  if (!efctStDt || efctStDt.length < 8) return efctStDt || '-'
  return `${efctStDt.slice(0, 4)}-${efctStDt.slice(4, 6)}-${efctStDt.slice(6, 8)}`
}
async function loadDataSharingList() {
  const cf = formStore.customerForm || {}
  if (!cf.ncn || !cf.phone || !cf.custId) return
  dataSharingListLoading.value = true
  dataSharingListLoaded.value = true
  try {
    const res = await getDataSharingList({ ncn: cf.ncn, ctn: cf.phone, custId: cf.custId })
    if (res && res.RESULT_CODE === 'SUCCESS' && Array.isArray(res.sharingList)) {
      dataSharingList.value = res.sharingList
    } else {
      dataSharingList.value = []
    }
  } catch (_) {
    dataSharingList.value = []
  } finally {
    dataSharingListLoading.value = false
  }
}
async function doDataSharingCheck() {
  const cf = formStore.customerForm || {}
  if (!cf.ncn || !cf.phone || !cf.custId) return
  dataSharingCheckLoading.value = true
  dataSharingCheckResult.value = null
  try {
    const res = await checkDataSharing({ ncn: cf.ncn, ctn: cf.phone, custId: cf.custId })
    dataSharingCheckResult.value = res || null
  } catch (e) {
    dataSharingCheckResult.value = { RESULT_CODE: 'E', RESULT_MSG: e?.message || '확인에 실패했습니다.', canJoin: false }
  } finally {
    dataSharingCheckLoading.value = false
  }
}
async function onDataSharingCancel(item) {
  const cf = formStore.customerForm || {}
  if (!cf.ncn || !cf.phone || !cf.custId || !item?.svcNo) return
  if (!confirm(`데이터쉐어링 회선 ${item.svcNo}을(를) 해지하시겠습니까?`)) return
  dataSharingCancelLoading.value = item.svcNo
  try {
    const res = await cancelDataSharing({
      ncn: cf.ncn,
      ctn: cf.phone,
      custId: cf.custId,
      opmdSvcNo: item.svcNo
    })
    if (res && res.RESULT_CODE === 'SUCCESS') {
      await loadDataSharingList()
    } else {
      alert(res?.RESULT_MSG || '해지에 실패했습니다.')
    }
  } catch (e) {
    alert(e?.message || '해지에 실패했습니다.')
  } finally {
    dataSharingCancelLoading.value = null
  }
}

function openSettingPop(type) {
  if (type === 'tm') tmBlockPopOpen.value = true
  else if (type === 'numberTheft') numberTheftBlockPopOpen.value = true
  else if (type === 'roaming') roamingPopOpen.value = true
}

function onAdditionOpenOption({ item }) {
  additionEditOpen.value = false
  const id = item?.id || ''
  if (id === 'tm_block') tmBlockPopOpen.value = true
  else if (id === 'num_theft') numberTheftBlockPopOpen.value = true
  else if (id === 'roaming') roamingPopOpen.value = true
}

function onTmBlockConfirm(payload) {
  if (payload?.blockList?.length) {
    productForm.value.tmBlockList = payload.blockList
  }
}

function onNumberTheftConfirm() {
  productForm.value.numberTheftAgreed = true
}

function onRoamingConfirm(payload) {
  if (payload) {
    productForm.value.roamingPeriod = payload
  }
}

function onNumberSearchConfirm(payload) {
  if (payload?.number) {
    productForm.value.numChange = payload.number
  }
}

function hasOption(id) {
  return selectedOptions.value.includes(id)
}

/** 요금상품 변경 목록 조회 (RATE_CHANGE) */
async function fetchFarPriceData() {
  const cust = formStore.customerForm || {}
  const ctn = (cust.phone || '').replace(/\D/g, '') || ''
  if (!ctn) return
  farPriceLoading.value = true
  try {
    const data = await getFarPriceList({
      ncn: cust.ncn || '',
      ctn,
      custId: cust.custId || '',
    })
    if (data?.items?.length) {
      farPriceItems.value = [...data.items]
    } else {
      farPriceItems.value = []
    }
  } catch (e) {
    console.warn('FarPrice 목록 조회 실패:', e)
    farPriceItems.value = []
  } finally {
    farPriceLoading.value = false
  }
}

/** 1. 조회_현재 가입된 부가서비스 조회 (X20). 무선데이터차단/정보료 이용 여부 세팅. 설계서 하드코딩 표시용 ctn 없어도 호출 */
async function fetchAdditionCurrent() {
  const cust = formStore.customerForm || {}
  const ctn = (cust.phone || '').replace(/\D/g, '') || '01012345678'
  additionLoading.value = true
  try {
    const data = await getCurrentAddition({
      ncn: cust.ncn || '',
      ctn,
      custId: cust.custId || '',
    })
    if (data && data.items) currentAdditionItems.value = data.items
    if (data && Array.isArray(data.availableItems)) availableAdditionItems.value = data.availableItems
    // 무선데이터차단 서비스 사용 여부 → 가입 가능 옵션 제한 (미사용중인 경우만 해당 옵션 가입 가능)
    if (data && typeof data.wirelessBlockInUse === 'boolean') {
      wirelessBlockCurrent.value = data.wirelessBlockInUse ? 'block' : 'use'
    }
    if (data && data.infoLimitAmount != null) {
      infoLimitCurrent.value = String(data.infoLimitAmount)
    }
  } catch (e) {
    console.warn('X20 부가서비스 조회 실패:', e)
  } finally {
    additionLoading.value = false
  }
}

/** 3. 처리_부가서비스 신청(X21) / 해지(X38) */
async function onAdditionEditConfirm(payload) {
  if (!payload?.toAdd?.length && !payload?.toCancel?.length) return
  const cust = formStore.customerForm || {}
  const ctn = (cust.phone || '').replace(/\D/g, '')
  const base = { ncn: cust.ncn || '', ctn, custId: cust.custId || '' }
  try {
    for (const item of payload.toAdd || []) {
      const soc = typeof item === 'string' ? item : (item?.soc || item?.code || item?.id)
      if (soc) await regAddition({ ...base, soc, ftrNewParam: typeof item === 'object' ? item?.ftrNewParam : undefined })
    }
    for (const item of payload.toCancel || []) {
      const soc = typeof item === 'string' ? item : (item?.soc || item?.code || item?.id)
      if (soc) await cancelAddition({ ...base, soc })
    }
    productForm.value.additions = productForm.value.additions || []
    await fetchAdditionCurrent() // 반영 후 현재 목록 재조회
  } catch (e) {
    console.warn('부가서비스 신청/해지 처리 실패:', e)
    alert(e?.message || '부가서비스 처리 중 오류가 발생했습니다.')
  }
}

// 선택 항목이 있으면 상품 단계 진행 가능
const isComplete = computed(() => selectedOptions.value.length > 0)

watch(productForm, (v) => formStore.setProductForm(v), { deep: true })
watch(isComplete, (v) => emit('update:complete', v))
// 데이터쉐어링: 고객 정보 확보 후 결합 목록 자동 조회
watch(canCheckDataSharing, (v) => {
  if (v && selectedOptions.value.includes('DATA_SHARING') && !dataSharingListLoaded.value) {
    loadDataSharingList()
  }
})
// 분실복구/일시정지해제: 고객 정보 확보 후 상태 자동 조회는 하지 않고, 버튼으로 조회
watch(canCheckPause, (v) => {
  if (!v) {
    pauseInfo.value = null
    pauseErrorMsg.value = ''
  }
})
onMounted(async () => {
  const saved = formStore.productForm
  if (saved && Object.keys(saved).length) {
    productForm.value = { ...productForm.value, ...saved }
  }
  // 다음 클릭 후 상품 그리드 로딩 시: X20 현재 가입 부가서비스 조회
  if (hasOption('WIRELESS_BLOCK') || hasOption('INFO_LIMIT') || hasOption('ADDITION')) {
    await fetchAdditionCurrent()
  }
  // 요금상품 변경 선택 시: FarPrice 목록 조회
  if (hasOption('RATE_CHANGE')) {
    await fetchFarPriceData()
  }
  // 데이터쉐어링 선택 시: X71 결합 목록 조회
  if (hasOption('DATA_SHARING') && canCheckDataSharing.value) {
    loadDataSharingList()
  }
  // 분실복구/일시정지해제는 버튼으로 조회
  emit('update:complete', isComplete.value)
})

const validate = async () => {
  if (selectedOptions.value.length === 0) {
    return { valid: false, message: '가입유형에서 변경 항목을 선택해 주세요.' }
  }
  if (hasOption('RATE_CHANGE')) {
    const soc = productForm.value.recommendedRatePlan || productForm.value.ratePlanSearchResult
    if (!soc) {
      return { valid: false, message: '요금상품 변경: 변경할 요금제를 선택해 주세요.' }
    }
    if (!productForm.value.rateChangeSchedule) {
      return { valid: false, message: '요금상품 변경: 변경일시(즉시/예약)를 선택해 주세요.' }
    }
  }
  formStore.setProductForm(productForm.value)
  return { valid: true, message: '' }
}

defineExpose({ validate })
</script>

<style scoped>
@reference "tailwindcss";
.section-title {
  @apply text-lg font-semibold text-gray-800 pb-2 mb-4 border-b border-gray-300;
}
.product-card {
  @apply p-4 border border-gray-200 rounded-lg bg-white;
}
.product-card-title {
  @apply font-medium text-gray-800 mb-2;
}
.product-section-title {
  @apply text-base font-bold text-gray-800 pb-2 mb-3 border-b border-gray-300;
}
.product-form-row {
  @apply flex items-start gap-4;
}
.product-row-label {
  width: 140px;
  @apply shrink-0 text-sm font-medium text-gray-700 pt-2;
}
.product-row-input {
  @apply flex-1 min-w-0;
}
.product-btn-choice {
  @apply px-4 py-2 rounded-lg border border-gray-200 text-sm font-medium text-gray-700 bg-white transition-colors hover:bg-gray-50;
}
.product-btn-choice--selected {
  @apply border-teal-500 bg-teal-50 text-teal-700;
}
.product-btn-choice:disabled,
.product-btn-choice--disabled {
  @apply opacity-50 cursor-not-allowed pointer-events-none;
}
.product-select {
  @apply rounded-lg border border-gray-300 px-3 py-2 text-sm bg-white focus:border-teal-500 focus:ring-1 focus:ring-teal-500;
}
</style>
