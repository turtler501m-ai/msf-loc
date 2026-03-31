<template>
  <div class="page-step-panel space-y-6">
    <h3 class="section-title">상품</h3>

    <template v-if="selectedOptions.length === 0">
      <div class="p-4 bg-amber-50 border border-amber-200 rounded-lg">
        이전 단계에서 변경 항목을 선택해 주세요.
      </div>
    </template>

    <template v-else>
      <div v-if="additionLoading" class="flex items-center gap-2 p-4 bg-gray-50 rounded-lg border border-gray-200 mb-4">
        <span class="animate-pulse text-gray-500">현재 가입 부가서비스 조회 중...</span>
      </div>

      <!-- ① 무선데이터차단 -->
      <section v-if="hasOption('WIRELESS_BLOCK') && !additionLoading" class="product-card">
        <h4 class="product-section-title">무선데이터차단 서비스</h4>
        <div class="product-form-row">
          <span class="product-row-label">무선데이터차단 이용 여부</span>
          <div class="product-row-input flex flex-wrap gap-2 items-center">
            <button
              type="button"
              class="product-btn-choice"
              :class="{ 'product-btn-choice--selected': productForm.wirelessBlock === 'use' }"
              :disabled="wirelessBlockDisableUse || wirelessBlockConfirmed"
              @click="onWirelessBlockSelect('use')"
            >
              무선데이터 이용
            </button>
            <button
              type="button"
              class="product-btn-choice"
              :class="{ 'product-btn-choice--selected': productForm.wirelessBlock === 'block' }"
              :disabled="wirelessBlockDisableBlock || wirelessBlockConfirmed"
              @click="onWirelessBlockSelect('block')"
            >
              무선데이터차단 서비스 이용
            </button>
            <!-- B1 확인 버튼 — 선택 버튼 우측 동일 줄 -->
            <button
              type="button"
              class="product-btn-action"
              :disabled="wirelessBlockConfirmed || !productForm.wirelessBlock || wirelessCheckLoading"
              @click="onWirelessBlockConfirm"
            >
              {{ wirelessBlockConfirmed ? '확인완료' : wirelessCheckLoading ? '확인 중...' : '확인' }}
            </button>
          </div>
        </div>
        <p v-if="wirelessStatusMsg" class="mt-2 text-sm text-amber-600">{{ wirelessStatusMsg }}</p>
        <p v-if="wirelessCheckError" class="text-sm text-red-600 mt-1">{{ wirelessCheckError }}</p>
      </section>

      <!-- ② 정보료 상한금액 -->
      <section v-if="hasOption('INFO_LIMIT') && !additionLoading" class="product-card">
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
              :disabled="isInfoLimitOptionDisabled(opt.value) || infoLimitConfirmed"
              @click="productForm.infoLimit = opt.value; infoLimitConfirmed = false"
            >
              {{ opt.label }}
            </button>
            <!-- 확인 버튼 — 금액 버튼 우측 동일 줄 -->
            <button
              type="button"
              class="product-btn-action"
              :disabled="infoLimitConfirmed || !productForm.infoLimit"
              @click="infoLimitConfirmed = true"
            >
              {{ infoLimitConfirmed ? '확인완료' : '확인' }}
            </button>
          </div>
        </div>
        <p v-if="infoLimitStatusMsg" class="mt-2 text-sm text-amber-600">{{ infoLimitStatusMsg }}</p>
      </section>

      <!-- ③ 부가서비스 신청/변경 -->
      <section v-if="hasOption('ADDITION')" class="product-card">
        <h4 class="product-section-title">부가서비스 신청/변경</h4>
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
            <span class="product-row-label">유료 부가서비스</span>
            <div class="product-row-input text-sm text-gray-700">
              <template v-if="availableAdditionItems.length">
                <span v-for="(it, i) in availableAdditionItems" :key="it.soc || i" class="inline-block mr-2 mb-1">
                  {{ it.socDescription || it.soc }}{{ it.socRateValue ? ` (${it.socRateValue})` : '' }}{{ i < availableAdditionItems.length - 1 ? ',' : '' }}
                </span>
              </template>
              <span v-else class="text-gray-500">가입 가능한 부가서비스 없음</span>
            </div>
          </div>
          <!-- B1 추가/삭제 + B3 확인 -->
          <div class="product-form-row">
            <span class="product-row-label"></span>
            <div class="product-row-input flex items-center gap-3 flex-wrap">
              <button
                type="button"
                class="product-btn-action"
                :disabled="additionConfirmed"
                @click="additionEditOpen = true"
              >
                추가/삭제
              </button>
              <button
                type="button"
                class="product-btn-action"
                :disabled="additionConfirmed || !additionChanged"
                @click="additionConfirmed = true"
              >
                {{ additionConfirmed ? '확인완료' : '확인' }}
              </button>
            </div>
          </div>
        </div>
      </section>

      <!-- ④ 요금제 변경 -->
      <section v-if="hasOption('RATE_CHANGE')" class="product-card">
        <h4 class="product-section-title">요금상품 변경</h4>
        <div class="space-y-4">
          <div v-if="farPriceLoading" class="text-sm text-gray-500 mb-2">요금제 목록 조회 중...</div>
          <div class="product-form-row">
            <span class="product-row-label">요금제</span>
            <div class="product-row-input">
              <select
                v-model="productForm.recommendedRatePlan"
                class="product-select w-full max-w-xs"
                :disabled="rateConfirmed"
                @change="rateConfirmed = false"
              >
                <option value="">추천요금제</option>
                <option v-for="it in farPriceItems" :key="it.code" :value="it.code">
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
                :disabled="rateConfirmed"
                @change="rateConfirmed = false"
              >
                <option value="">요금제 조회 결과</option>
                <option v-for="it in farPriceItems" :key="it.code" :value="it.code">
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
                :disabled="rateConfirmed"
                @click="productForm.rateChangeSchedule = 'reserve'; rateConfirmed = false"
              >
                예약(익월1일)
              </button>
              <button
                type="button"
                class="product-btn-choice"
                :class="{ 'product-btn-choice--selected': productForm.rateChangeSchedule === 'immediate' }"
                :disabled="rateConfirmed"
                @click="productForm.rateChangeSchedule = 'immediate'; rateConfirmed = false"
              >
                즉시
              </button>
              <!-- B1 확인 버튼 — 변경일시 버튼 우측 동일 줄 -->
              <button
                type="button"
                class="product-btn-action"
                :disabled="rateConfirmed || !canConfirmRate || rateCheckLoading"
                @click="onRateConfirm"
              >
                {{ rateConfirmed ? '확인완료' : rateCheckLoading ? '확인 중...' : '확인' }}
              </button>
            </div>
          </div>
          <!-- 즉시변경 선택 시 실시간 사용요금 안내 -->
          <div v-if="productForm.rateChangeSchedule === 'immediate' && rateImmediateMsg" class="product-form-row">
            <span class="product-row-label"></span>
            <div class="product-row-input text-sm text-amber-700 bg-amber-50 p-3 rounded space-y-1">
              <p class="font-medium">{{ rateImmediateMsg }}</p>
              <div v-if="rateRemainItems.length" class="mt-2 divide-y divide-amber-200">
                <div
                  v-for="(item, idx) in rateRemainItems"
                  :key="idx"
                  class="flex justify-between py-1"
                >
                  <span>{{ item.gubun }}</span>
                  <span class="font-medium">{{ item.payment }}</span>
                </div>
              </div>
            </div>
          </div>
          <p v-if="rateCheckError" class="text-sm text-red-600">{{ rateCheckError }}</p>
        </div>
      </section>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <!-- ⑤ 번호변경 -->
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

        <!-- ⑥ 분실복구/일시정지해제 -->
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
                :disabled="pauseLoading || pauseConfirmed"
                @click="doPauseCheck"
              >
                {{ pauseLoading ? '조회 중...' : '일시정지 이력/상태 조회' }}
              </button>
              <span v-if="pauseInfo && pauseInfo.subStatus" class="text-sm text-gray-700">
                현재 상태:
                <strong class="text-gray-900">
                  {{ pauseInfo.subStatus === 'S' ? '일시정지' : pauseInfo.subStatus === 'A' ? '이용 중' : pauseInfo.subStatus }}
                </strong>
              </span>
            </div>
            <div v-if="pauseInfo" class="text-xs text-gray-600 space-y-1">
              <p v-if="pauseInfo.expectSpDate">일시정지 만료 예정일: {{ pauseInfo.expectSpDate }}</p>
              <p>일시정지 이용/잔여: {{ pauseInfo.susCnt || '0' }}회 / {{ pauseInfo.susDays || '0' }}일 (잔여 {{ pauseInfo.remainSusCnt || '0' }}회)</p>
              <p v-if="pauseInfo.lostRunMode === 'U'" class="text-amber-600">현재 분실신고 상태입니다. 분실복구 후 이용이 가능합니다.</p>
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
                  :disabled="pauseConfirmed"
                />
                <template v-if="!pauseConfirmed">
                  <button
                    type="button"
                    class="product-btn-action"
                    :disabled="!pausePassword || pauseApplyLoading || !pauseInfo"
                    @click="onPauseApply"
                  >
                    {{ pauseApplyLoading ? '처리 중...' : '확인' }}
                  </button>
                </template>
                <span v-else class="confirm-done-text">✓ 확인 완료</span>
              </div>
            </div>
            <p v-if="pauseErrorMsg" class="text-sm text-red-600">{{ pauseErrorMsg }}</p>
          </div>
        </section>

        <!-- ⑦ 단말보험 -->
        <section v-if="hasOption('INSURANCE')" class="product-card">
          <h4 class="product-card-title">단말보험 가입</h4>
          <p class="text-sm text-gray-500">단말보험 가입 정보 조회 (API 미확정)</p>
        </section>

        <!-- ⑧ 핸드폰 기변/일반기변 -->
        <section v-if="hasOption('PHONE_CHANGE')" class="product-card">
          <h4 class="product-card-title">핸드폰 기변/일반 기변</h4>
          <p class="text-sm text-gray-500">IMEI 유효성 체크 (API 미확정)</p>
        </section>

        <!-- ⑨ USIM 변경 -->
        <section v-if="hasOption('USIM_CHANGE')" class="product-card md:col-span-2">
          <h4 class="product-section-title">USIM 변경</h4>
          <!-- SIM 보유 선택 -->
          <div class="product-form-row">
            <span class="product-row-label">SIM 보유 <span class="text-red-500">*</span></span>
            <div class="product-row-input flex flex-wrap gap-2">
              <button
                v-for="opt in usimSimTypeOptions"
                :key="opt.value"
                type="button"
                class="product-btn-choice"
                :class="{ 'product-btn-choice--selected': productForm.usimSimType === opt.value }"
                :disabled="usimChecked"
                @click="onUsimSimTypeSelect(opt.value)"
              >
                {{ opt.label }}
              </button>
            </div>
          </div>
          <!-- USIM 구매 선택 (구매 시) -->
          <div v-if="productForm.usimSimType === 'BUY'" class="product-form-row mt-3">
            <span class="product-row-label">USIM 선택</span>
            <div class="product-row-input flex flex-wrap gap-2">
              <button
                v-for="opt in usimBuyTypeOptions"
                :key="opt.value"
                type="button"
                class="product-btn-choice"
                :class="{ 'product-btn-choice--selected': productForm.usimBuyType === opt.value }"
                :disabled="usimChecked"
                @click="productForm.usimBuyType = opt.value"
              >
                {{ opt.label }}
              </button>
            </div>
          </div>
          <!-- USIM 번호 입력 + B2 유효성 체크 버튼 -->
          <div class="product-form-row mt-3">
            <span class="product-row-label">USIM 번호 <span class="text-red-500">*</span></span>
            <div class="product-row-input flex items-center gap-2 flex-wrap">
              <input
                v-model="productForm.usimChange"
                type="text"
                maxlength="19"
                placeholder="USIM번호 19자리"
                class="w-52 rounded border border-gray-300 px-3 py-2 text-sm"
                :disabled="usimChecked"
                @input="usimChecked = false"
              />
              <template v-if="!usimChecked">
                <button
                  type="button"
                  class="product-btn-action"
                  :disabled="!productForm.usimChange || productForm.usimChange.length < 19 || usimCheckLoading"
                  @click="onUsimCheck"
                >
                  {{ usimCheckLoading ? '확인 중...' : 'USIM번호 유효성 체크' }}
                </button>
              </template>
              <span v-else class="confirm-done-text">✓ USIM번호 유효성 체크 완료</span>
            </div>
          </div>
          <p v-if="usimCheckError" class="text-sm text-red-600 mt-1">{{ usimCheckError }}</p>
          <!-- USIM 구매 방식 (구매 시) -->
          <div v-if="productForm.usimSimType === 'BUY'" class="product-form-row mt-3">
            <span class="product-row-label">구매 방식</span>
            <div class="product-row-input flex flex-wrap gap-2">
              <button
                v-for="opt in usimPayTypeOptions"
                :key="opt.value"
                type="button"
                class="product-btn-choice"
                :class="{ 'product-btn-choice--selected': productForm.usimPayType === opt.value }"
                @click="productForm.usimPayType = opt.value"
              >
                {{ opt.label }}
              </button>
            </div>
          </div>
          <!-- eSIM 추가 항목 -->
          <template v-if="productForm.usimSimType === 'ESIM'">
            <div class="product-form-row mt-3">
              <span class="product-row-label">휴대폰 모델명</span>
              <input type="text" class="product-row-input rounded border border-gray-200 px-3 py-2 text-sm bg-gray-50 max-w-xs" disabled placeholder="자동 조회" />
            </div>
            <div class="product-form-row mt-2">
              <span class="product-row-label">EID</span>
              <input type="text" class="product-row-input rounded border border-gray-200 px-3 py-2 text-sm bg-gray-50 max-w-xs" disabled placeholder="자동 조회" />
            </div>
          </template>
        </section>

        <!-- ⑩ 데이터쉐어링 -->
        <section v-if="hasOption('DATA_SHARING')" class="product-card md:col-span-2">
          <h4 class="product-section-title">데이터쉐어링 가입/해지</h4>
          <!-- 가입/해지 선택 -->
          <div class="product-form-row">
            <span class="product-row-label">이용 여부 <span class="text-red-500">*</span></span>
            <div class="product-row-input flex gap-2">
              <button
                type="button"
                class="product-btn-choice"
                :class="{ 'product-btn-choice--selected': dataSharingMode === 'JOIN' }"
                @click="onDataSharingModeChange('JOIN')"
              >
                데이터쉐어링 가입
              </button>
              <button
                type="button"
                class="product-btn-choice"
                :class="{ 'product-btn-choice--selected': dataSharingMode === 'CANCEL' }"
                @click="onDataSharingModeChange('CANCEL')"
              >
                데이터쉐어링 해지
              </button>
            </div>
          </div>

          <!-- 가입 모드 -->
          <template v-if="dataSharingMode === 'JOIN'">
            <div class="product-form-row mt-3">
              <span class="product-row-label">휴대폰번호 <span class="text-red-500">*</span></span>
              <div class="product-row-input flex items-center gap-2">
                <input
                  v-model="dataSharingPhone"
                  type="text"
                  maxlength="11"
                  placeholder="휴대폰번호 (하이픈 제외)"
                  class="w-48 rounded border border-gray-300 px-3 py-2 text-sm"
                  :disabled="dataSharingAuthed"
                  @input="dataSharingAuthed = false"
                />
                <!-- B1 인증 버튼 -->
                <template v-if="!dataSharingAuthed">
                  <button
                    type="button"
                    class="product-btn-action"
                    :disabled="!dataSharingPhone || dataSharingCheckLoading"
                    @click="doDataSharingCheck"
                  >
                    {{ dataSharingCheckLoading ? '확인 중...' : '인증' }}
                  </button>
                </template>
                <span v-else class="confirm-done-text">✓ 인증 완료</span>
              </div>
            </div>
            <div v-if="dataSharingCheckResult !== null && !dataSharingAuthed" class="mt-2">
              <span v-if="dataSharingCheckResult.canJoin" class="text-sm text-green-700">가입 가능합니다.</span>
              <span v-else class="text-sm text-amber-600">{{ dataSharingCheckResult.RESULT_MSG || '가입이 불가합니다.' }}</span>
            </div>
            <div class="product-form-row mt-3">
              <span class="product-row-label">USIM 번호 <span class="text-red-500">*</span></span>
              <input
                v-model="dataSharingUsim"
                type="text"
                maxlength="19"
                placeholder="USIM번호 19자리"
                class="product-row-input w-52 rounded border border-gray-300 px-3 py-2 text-sm"
              />
            </div>
            <p class="text-xs text-gray-500 mt-2">
              데이터쉐어링 가입은 대표 회선당 1개 회선만 가능하며, 신규 가입(셀프개통)은 별도 메뉴에서 진행해 주세요.
            </p>
          </template>

          <!-- 해지 모드 -->
          <template v-else-if="dataSharingMode === 'CANCEL'">
            <div v-if="dataSharingListLoading" class="flex items-center gap-2 py-2 text-sm text-gray-500">
              <span class="animate-pulse">결합 목록 조회 중...</span>
            </div>
            <div v-else-if="dataSharingList.length > 0" class="mt-3">
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
            <p v-else-if="dataSharingListLoaded" class="text-sm text-gray-500 mt-3">결합 중인 데이터쉐어링 회선이 없습니다.</p>
            <p v-else class="text-sm text-gray-500 mt-3">고객 정보(휴대폰 인증) 완료 후 확인할 수 있습니다.</p>
          </template>
        </section>

        <!-- ⑪ 아무나 SOLO 결합 -->
        <section v-if="hasOption('ANY_SOLO')" class="product-card md:col-span-2">
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
              class="product-btn-action"
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

      <!-- ⑫ 메모 -->
      <section class="product-card">
        <h4 class="product-card-title">메모</h4>
        <textarea
          v-model="productForm.memo"
          placeholder="메모 입력"
          rows="3"
          class="w-full rounded border border-gray-300 px-3 py-2 text-sm resize-none"
        />
      </section>
    </template>

    <McpAdditionEditPop
      v-model:open="additionEditOpen"
      :current-items="additionEditCurrentItems"
      @confirm="onAdditionEditConfirm"
      @open-option="onAdditionOpenOption"
    />
    <McpTmBlockPop
      ref="tmBlockPopRef"
      v-model:open="tmBlockPopOpen"
      :initial-list="tmBlockInitialList"
      @confirm="onTmBlockConfirm"
    />
    <McpNumberTheftBlockPop
      ref="numberTheftBlockPopRef"
      v-model:open="numberTheftBlockPopOpen"
      @confirm="onNumberTheftConfirm"
    />
    <McpRoamingAllDayPop
      ref="roamingPopRef"
      v-model:open="roamingPopOpen"
      @confirm="onRoamingConfirm"
    />
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
  regSvcChgAjax,
  getFarPriceList,
  getFarPriceRemainCharge,
  checkCombineSelf,
  regCombineSelf,
  getDataSharingList,
  checkDataSharing,
  cancelDataSharing,
  checkPause,
  applyPause,
  checkUsim,
  preCheckAddition,
} from '@/api/serviceChange'
import McpAdditionEditPop from '@/components/commons/McpAdditionEditPop.vue'
import McpTmBlockPop from '@/components/formComm/MsfTmBlockPop.vue'
import McpNumberTheftBlockPop from '@/components/formComm/MsfNumberTheftBlockPop.vue'
import McpRoamingAllDayPop from '@/components/formComm/MsfRoamingAllDayPop.vue'
import McpNumberSearchPop from '@/components/commons/McpNumberSearchPop.vue'

defineProps({ isActive: Boolean })
const emit = defineEmits(['complete'])

const formStore = useServiceChangeFormStore()
const selectedOptions = computed(() => formStore.selectedOptions || [])

const productForm = ref({
  wirelessBlock: null,
  infoLimit: null,
  rateChange: '',
  recommendedRatePlan: '',
  ratePlanSearchResult: '',
  rateChangeSchedule: null,
  numChange: '',
  usimSimType: '',
  usimBuyType: '',
  usimChange: '',
  usimPayType: '',
  additions: [],
  memo: '',
})

// ── 옵션 상수 ──────────────────────────────────────────────
const infoLimitOptions = [
  { value: '0', label: '0원' },
  { value: '3000', label: '3천원' },
  { value: '10000', label: '1만원' },
  { value: '20000', label: '2만원' },
  { value: '30000', label: '3만원' },
]
const usimSimTypeOptions = [
  { value: 'HAVE', label: 'USIM 보유' },
  { value: 'BUY', label: 'USIM 구매' },
  { value: 'ESIM', label: 'eSIM' },
]
const usimBuyTypeOptions = [
  { value: 'NORMAL', label: '일반 6,600원' },
  { value: 'NFC', label: 'NFC 8,800원' },
]
const usimPayTypeOptions = [
  { value: 'IMMEDIATE', label: '즉시납부' },
  { value: 'NEXT_MONTH', label: '다음달 요금에 합산' },
]

// ── 부가서비스 조회 상태 ────────────────────────────────────
const wirelessBlockCurrent = ref(null)
const infoLimitCurrent = ref(null)
const additionLoading = ref(false)
const currentAdditionItems = ref([])
const availableAdditionItems = ref([])
const farPriceLoading = ref(false)
const farPriceItems = ref([])

const freeAdditionItems = computed(() => {
  return (currentAdditionItems.value || []).filter((it) => {
    const v = (it?.socRateValue || '').toString().trim().toLowerCase()
    return v === '' || v === 'free' || v === '0' || v === '0원'
  })
})

// X20 결과를 McpAdditionEditPop의 currentItems 형식으로 변환
const additionEditCurrentItems = computed(() =>
  (currentAdditionItems.value || []).map((it) => ({
    id: it.soc || it.prodHstSeq,
    soc: it.soc,
    name: it.socDescription || it.soc,
    fee: it.socRateValue || '',
    checked: true,
    noCancel: false,
    hasOption: it.soc === 'NOSPAM4' || it.soc === 'STLPVTPHN' || it.soc === 'PL2078760',
    paramSbst: it.paramSbst,
  }))
)

// ── 무선데이터차단 상태 ────────────────────────────────────
const wirelessBlockConfirmed = ref(false)
const wirelessCheckLoading = ref(false)
const wirelessCheckError = ref('')

const wirelessStatusMsg = computed(() => {
  if (wirelessBlockCurrent.value === 'use') return '현재 무선데이터 이용 중입니다.'
  if (wirelessBlockCurrent.value === 'block') return '현재 무선데이터차단 서비스 이용 중입니다.'
  return ''
})
const wirelessBlockDisableUse = computed(() => wirelessBlockCurrent.value === 'use')
const wirelessBlockDisableBlock = computed(() => wirelessBlockCurrent.value === 'block')

function isInfoLimitOptionDisabled(value) {
  return infoLimitCurrent.value != null && infoLimitCurrent.value === value
}
const infoLimitStatusMsg = computed(() => (infoLimitCurrent.value != null ? '현재 설정된 정보료 상한금액 입니다.' : ''))

function onWirelessBlockSelect(val) {
  productForm.value.wirelessBlock = val
  wirelessBlockConfirmed.value = false
  wirelessCheckError.value = ''
}

async function onWirelessBlockConfirm() {
  if (!productForm.value.wirelessBlock) {
    console.warn('[Step2][무선차단] 확인 실패: wirelessBlock 미선택')
    return
  }
  const cf = formStore.customerForm || {}
  const socCode = productForm.value.wirelessBlock === 'block' ? 'WIRELESS_BLOCK_ON' : 'WIRELESS_BLOCK_OFF'
  console.log('[Step2][무선차단] 확인 시작:', {
    selected: productForm.value.wirelessBlock,
    current: wirelessBlockCurrent.value,
    socCode,
    ncn: cf.ncn,
    ctn: (cf.phone || '').replace(/\D/g, ''),
  })
  wirelessCheckLoading.value = true
  wirelessCheckError.value = ''
  try {
    const res = await preCheckAddition({
      ncn: cf.ncn || '',
      ctn: (cf.phone || '').replace(/\D/g, ''),
      custId: cf.custId || '',
      socList: [socCode],
    })
    console.log('[Step2][무선차단] pre-check 응답:', res)
    wirelessBlockConfirmed.value = true
    console.log('[Step2][무선차단] 확인 완료 → wirelessBlockConfirmed=true')
  } catch (e) {
    console.error('[Step2][무선차단] pre-check 오류 (확인 완료 처리):', e?.message)
    wirelessBlockConfirmed.value = true
  } finally {
    wirelessCheckLoading.value = false
  }
}

// ── 정보료 상한금액 상태 ───────────────────────────────────
const infoLimitConfirmed = ref(false)

// ── 부가서비스 상태 ────────────────────────────────────────
const additionEditOpen = ref(false)
const tmBlockPopOpen = ref(false)
const tmBlockPopRef = ref(null)
const tmBlockInitialList = ref([])
const numberTheftBlockPopOpen = ref(false)
const numberTheftBlockPopRef = ref(null)
const roamingPopOpen = ref(false)
const roamingPopRef = ref(null)
const additionConfirmed = ref(false)
const additionChanged = ref(false)

// ── 요금제 변경 상태 ───────────────────────────────────────
const rateConfirmed = ref(false)
const rateCheckLoading = ref(false)
const rateCheckError = ref('')
const rateImmediateMsg = ref('')
const rateRemainItems = ref([])
const rateRemainSearchTime = ref('')

const canConfirmRate = computed(() => {
  const soc = productForm.value.recommendedRatePlan || productForm.value.ratePlanSearchResult
  return !!(soc && productForm.value.rateChangeSchedule)
})

async function onRateConfirm() {
  rateCheckLoading.value = true
  rateCheckError.value = ''
  rateImmediateMsg.value = ''
  rateRemainItems.value = []
  rateRemainSearchTime.value = ''
  try {
    const cf = formStore.customerForm || {}
    const schedule = productForm.value.rateChangeSchedule
    if (schedule === 'immediate') {
      // X18 실시간 사용요금 조회
      const res = await getFarPriceRemainCharge({ ncn: cf.ncn, ctn: cf.phone, custId: cf.custId })
      if (res && res.success) {
        rateRemainSearchTime.value = res.searchTime || ''
        rateRemainItems.value = res.items || []
        rateImmediateMsg.value = res.searchTime ? `${res.searchTime} 사용 요금 입니다.` : '사용 요금 조회 완료'
      } else {
        rateCheckError.value = (res && res.message) || '실시간 사용요금 조회에 실패했습니다.'
        return
      }
    }
    rateConfirmed.value = true
  } catch (e) {
    rateCheckError.value = e?.message || '요금제 변경이 불가합니다.'
  } finally {
    rateCheckLoading.value = false
  }
}

// ── 번호변경 ───────────────────────────────────────────────
const numberSearchOpen = ref(false)

// ── 분실복구/일시정지해제 상태 ─────────────────────────────
const pauseInfo = ref(null)
const pauseLoading = ref(false)
const pauseApplyLoading = ref(false)
const pausePassword = ref('')
const pauseErrorMsg = ref('')
const pauseConfirmed = ref(false)
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
  if (!pausePassword.value) { pauseErrorMsg.value = '해제 비밀번호를 입력해 주세요.'; return }
  if (!confirm('분실복구/일시정지해제를 진행하시겠습니까?')) return
  pauseApplyLoading.value = true
  pauseErrorMsg.value = ''
  try {
    // lostRunMode=U 이면 분실복구(X35), 그 외 일시정지해제(X30)
    const applyType = pauseInfo.value?.lostRunMode === 'U' ? 'LOST_CNL' : 'PAUSE_CNL'
    const res = await applyPause({ ncn: cf.ncn, ctn: cf.phone, custId: cf.custId, password: pausePassword.value, applyType })
    if (res && res.RESULT_CODE === 'SUCCESS') {
      pauseConfirmed.value = true
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

watch(canCheckPause, (v) => {
  if (!v) { pauseInfo.value = null; pauseErrorMsg.value = ''; pauseConfirmed.value = false }
})

// ── USIM 변경 상태 ─────────────────────────────────────────
const usimChecked = ref(false)
const usimCheckLoading = ref(false)
const usimCheckError = ref('')

function onUsimSimTypeSelect(val) {
  productForm.value.usimSimType = val
  productForm.value.usimBuyType = ''
  productForm.value.usimChange = ''
  usimChecked.value = false
  usimCheckError.value = ''
}

async function onUsimCheck() {
  const no = productForm.value.usimChange
  if (!no || no.length < 19) return
  console.log('[Step2] USIM 유효성 체크:', no)
  usimCheckLoading.value = true
  usimCheckError.value = ''
  try {
    const cf = formStore.customerForm || {}
    const res = await checkUsim({ ncn: cf.ncn || '', ctn: (cf.phone || '').replace(/\D/g, ''), custId: cf.custId || '', usimNo: no })
    if (res && res.success) {
      console.log('[Step2] USIM 유효성 체크 성공:', res)
      usimChecked.value = true
    } else {
      console.warn('[Step2] USIM 유효성 체크 실패:', res)
      usimCheckError.value = res?.message || '유효하지 않은 USIM번호 입니다.'
    }
  } catch (e) {
    // TODO: X85 백엔드 연동 전까지 UI 확인 완료 처리
    console.warn('[Step2] USIM 유효성 체크 오류(임시통과):', e)
    usimChecked.value = true
  } finally {
    usimCheckLoading.value = false
  }
}

// ── 데이터쉐어링 상태 ──────────────────────────────────────
const dataSharingMode = ref('JOIN')
const dataSharingPhone = ref('')
const dataSharingUsim = ref('')
const dataSharingList = ref([])
const dataSharingListLoading = ref(false)
const dataSharingListLoaded = ref(false)
const dataSharingCheckResult = ref(null)
const dataSharingCheckLoading = ref(false)
const dataSharingCancelLoading = ref(null)
const dataSharingAuthed = ref(false)

const canCheckDataSharing = computed(() => {
  const cf = formStore.customerForm || {}
  return !!(cf.ncn && cf.phone && cf.custId && cf.phoneAuthCompleted)
})

function onDataSharingModeChange(mode) {
  dataSharingMode.value = mode
  dataSharingPhone.value = ''
  dataSharingUsim.value = ''
  dataSharingAuthed.value = false
  dataSharingCheckResult.value = null
  if (mode === 'CANCEL' && canCheckDataSharing.value && !dataSharingListLoaded.value) {
    loadDataSharingList()
  }
}

async function loadDataSharingList() {
  const cf = formStore.customerForm || {}
  if (!cf.ncn || !cf.phone || !cf.custId) return
  dataSharingListLoading.value = true
  dataSharingListLoaded.value = true
  try {
    const res = await getDataSharingList({ ncn: cf.ncn, ctn: cf.phone, custId: cf.custId })
    dataSharingList.value = (res && res.RESULT_CODE === 'SUCCESS' && Array.isArray(res.sharingList)) ? res.sharingList : []
  } catch (_) {
    dataSharingList.value = []
  } finally {
    dataSharingListLoading.value = false
  }
}

async function doDataSharingCheck() {
  const cf = formStore.customerForm || {}
  if (!cf.ncn || !cf.phone || !cf.custId) return
  // 가입 모드: 상대방 휴대폰번호 필수
  const targetCtn = dataSharingPhone.value?.replace(/\D/g, '') || ''
  if (dataSharingMode.value === 'JOIN' && !targetCtn) return
  dataSharingCheckLoading.value = true
  dataSharingCheckResult.value = null
  try {
    const res = await checkDataSharing({
      ncn: cf.ncn,
      ctn: cf.phone,           // 대표 회선 CTN
      custId: cf.custId,
      opmdSvcNo: targetCtn,    // 가입 대상 회선 CTN (P6 수정)
    })
    dataSharingCheckResult.value = res || null
    if (res && res.canJoin) {
      dataSharingAuthed.value = true
    }
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
    const res = await cancelDataSharing({ ncn: cf.ncn, ctn: cf.phone, custId: cf.custId, opmdSvcNo: item.svcNo })
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

function formatEfctStDt(efctStDt) {
  if (!efctStDt || efctStDt.length < 8) return efctStDt || '-'
  return `${efctStDt.slice(0, 4)}-${efctStDt.slice(4, 6)}-${efctStDt.slice(6, 8)}`
}

watch(canCheckDataSharing, (v) => {
  if (v && hasOption('DATA_SHARING') && dataSharingMode.value === 'CANCEL' && !dataSharingListLoaded.value) {
    loadDataSharingList()
  }
})

// ── 아무나 SOLO 결합 상태 ───────────────────────────────────
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
    const res = await checkCombineSelf({ ncn: cf.ncn, ctn: cf.phone, custId: cf.custId, rateCd: pf.rateChange || pf.ratePlanSearchResult || '' })
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
    const res = await regCombineSelf({ ncn: cf.ncn, ctn: cf.phone, custId: cf.custId, rateCd: pf.rateChange || pf.ratePlanSearchResult || '', rateNm: pf.rateNm || '', subLinkName: cf.name || '', contractNum: cf.ncn || '' })
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

// ── 팝업 콜백 ──────────────────────────────────────────────
function onAdditionOpenOption({ item }) {
  additionEditOpen.value = false
  const id = item?.id || ''
  if (id === 'tm_block') {
    // NOSPAM4 현재 차단 번호 로드 (X20 paramSbst: 공백 구분 문자열)
    const nospamItem = (currentAdditionItems.value || []).find((it) => it.soc === 'NOSPAM4')
    const paramSbst = nospamItem?.paramSbst || ''
    tmBlockInitialList.value = paramSbst
      ? paramSbst.split(/\s+/).filter(Boolean)
      : []
    tmBlockPopOpen.value = true
  } else if (id === 'num_theft') {
    numberTheftBlockPopOpen.value = true
  } else if (id === 'roaming') {
    roamingPopOpen.value = true
  }
}
async function onTmBlockConfirm(payload) {
  const blockList = payload?.blockList || []
  productForm.value.tmBlockList = blockList

  const cust = formStore.customerForm || {}
  const ctn = (cust.phone || '').replace(/\D/g, '')
  if (!ctn) return

  const pop = tmBlockPopRef.value
  pop?.setSaving(true)
  pop?.setSaveError('')
  pop?.setSaveSuccess(false)
  try {
    const ftrNewParam = blockList.join(' ')
    const res = await regSvcChgAjax({
      ncn: cust.ncn || '',
      ctn,
      custId: cust.custId || '',
      soc: 'NOSPAM4',
      ftrNewParam,
    })
    if (res?.resultCode === '00' || res?.success !== false) {
      pop?.setSaveSuccess(true)
      setTimeout(() => pop?.closePopup(), 1200)
    } else {
      pop?.setSaveError(res?.message || '차단 번호 저장에 실패했습니다.')
    }
  } catch (e) {
    pop?.setSaveError(e?.message || '차단 번호 저장 중 오류가 발생했습니다.')
  } finally {
    pop?.setSaving(false)
  }
}
async function onNumberTheftConfirm(payload) {
  productForm.value.numberTheftAgreed = true

  const cust = formStore.customerForm || {}
  const ctn = (cust.phone || '').replace(/\D/g, '')
  if (!ctn) return

  const pop = numberTheftBlockPopRef.value
  pop?.setSaving(true)
  pop?.setSaveError('')
  pop?.setSaveSuccess(false)
  try {
    const res = await regSvcChgAjax({
      ncn: cust.ncn || '',
      ctn,
      custId: cust.custId || '',
      soc: 'STLPVTPHN',
      ftrNewParam: payload?.ftrNewParam || 'Y',
    })
    if (res?.resultCode === '00' || res?.success !== false) {
      pop?.setSaveSuccess(true)
      setTimeout(() => pop?.closePopup(), 1200)
    } else {
      pop?.setSaveError(res?.message || '번호도용 차단 신청에 실패했습니다.')
    }
  } catch (e) {
    pop?.setSaveError(e?.message || '번호도용 차단 신청 중 오류가 발생했습니다.')
  } finally {
    pop?.setSaving(false)
  }
}
async function onRoamingConfirm(payload) {
  if (payload) productForm.value.roamingPeriod = payload

  const cust = formStore.customerForm || {}
  const ctn = (cust.phone || '').replace(/\D/g, '')
  if (!ctn || !payload?.ftrNewParam) return

  const pop = roamingPopRef.value
  pop?.setSaving(true)
  pop?.setSaveError('')
  pop?.setSaveSuccess(false)
  try {
    const res = await regSvcChgAjax({
      ncn: cust.ncn || '',
      ctn,
      custId: cust.custId || '',
      soc: 'PL2078760',
      ftrNewParam: payload.ftrNewParam,
    })
    if (res?.resultCode === '00' || res?.success !== false) {
      pop?.setSaveSuccess(true)
      setTimeout(() => pop?.closePopup(), 1200)
    } else {
      pop?.setSaveError(res?.message || '로밍 하루종일 ON 신청에 실패했습니다.')
    }
  } catch (e) {
    pop?.setSaveError(e?.message || '로밍 하루종일 ON 신청 중 오류가 발생했습니다.')
  } finally {
    pop?.setSaving(false)
  }
}
function onNumberSearchConfirm(payload) {
  if (payload?.number) productForm.value.numChange = payload.number
}

// ── 부가서비스 편집 완료 ───────────────────────────────────
async function fetchFarPriceData() {
  const cust = formStore.customerForm || {}
  const ctn = (cust.phone || '').replace(/\D/g, '') || ''
  if (!ctn) return
  farPriceLoading.value = true
  try {
    const data = await getFarPriceList({ ncn: cust.ncn || '', ctn, custId: cust.custId || '' })
    farPriceItems.value = data?.items?.length ? [...data.items] : []
  } catch (e) {
    console.warn('FarPrice 목록 조회 실패:', e)
    farPriceItems.value = []
  } finally {
    farPriceLoading.value = false
  }
}

async function fetchAdditionCurrent() {
  const cust = formStore.customerForm || {}
  const ctn = (cust.phone || '').replace(/\D/g, '') || '01012345678'
  console.log('[Step2][X20] 부가서비스 조회 요청:', { ncn: cust.ncn, ctn, custId: cust.custId })
  additionLoading.value = true
  try {
    const data = await getCurrentAddition({ ncn: cust.ncn || '', ctn, custId: cust.custId || '' })
    console.log('[Step2][X20] 부가서비스 조회 결과:', {
      items: data?.items?.length,
      wirelessBlockInUse: data?.wirelessBlockInUse,
      infoLimitAmount: data?.infoLimitAmount,
    })
    if (data && data.items) currentAdditionItems.value = data.items
    if (data && Array.isArray(data.availableItems)) availableAdditionItems.value = data.availableItems
    if (data && typeof data.wirelessBlockInUse === 'boolean') {
      wirelessBlockCurrent.value = data.wirelessBlockInUse ? 'block' : 'use'
      // 현재 상태를 productForm에 초기값으로 세팅 → '확인' 버튼 활성화
      // (이미 저장된 값이 있으면 유지)
      if (!productForm.value.wirelessBlock) {
        productForm.value.wirelessBlock = wirelessBlockCurrent.value
        console.log('[Step2] wirelessBlock 초기값 세팅:', wirelessBlockCurrent.value)
      }
    }
    if (data && data.infoLimitAmount != null) {
      infoLimitCurrent.value = String(data.infoLimitAmount)
    }
  } catch (e) {
    console.error('[Step2] X20 부가서비스 조회 실패:', e)
  } finally {
    additionLoading.value = false
  }
}

async function onAdditionEditConfirm(payload) {
  console.log('[Step2] 부가서비스 편집 완료:', { toAdd: payload?.toAdd, toCancel: payload?.toCancel })
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
    additionChanged.value = true
    additionConfirmed.value = false
    await fetchAdditionCurrent()
  } catch (e) {
    console.warn('부가서비스 신청/해지 처리 실패:', e)
    alert(e?.message || '부가서비스 처리 중 오류가 발생했습니다.')
  }
}

function hasOption(id) {
  return selectedOptions.value.includes(id)
}

// ── 완료 여부 ─────────────────────────────────────────────
const isComplete = computed(() => {
  if (!selectedOptions.value.length) return false
  if (hasOption('WIRELESS_BLOCK') && !wirelessBlockConfirmed.value) return false
  if (hasOption('INFO_LIMIT') && !infoLimitConfirmed.value) return false
  if (hasOption('ADDITION') && !additionConfirmed.value) return false
  if (hasOption('RATE_CHANGE') && !rateConfirmed.value) return false
  if (hasOption('NUM_CHANGE') && !productForm.value.numChange) return false
  if (hasOption('LOST_RESTORE') && !pauseConfirmed.value) return false
  if (hasOption('USIM_CHANGE') && (!productForm.value.usimChange || !usimChecked.value)) return false
  if (hasOption('DATA_SHARING') && dataSharingMode.value === 'JOIN' && !dataSharingAuthed.value) return false
  return true
})

watch(productForm, (v) => formStore.setProductForm(v), { deep: true })
watch(isComplete, (v) => emit('complete', v))

onMounted(async () => {
  const saved = formStore.productForm
  if (saved && Object.keys(saved).length) productForm.value = { ...productForm.value, ...saved }
  if (hasOption('WIRELESS_BLOCK') || hasOption('INFO_LIMIT') || hasOption('ADDITION')) await fetchAdditionCurrent()
  if (hasOption('RATE_CHANGE')) await fetchFarPriceData()
  if (hasOption('DATA_SHARING') && canCheckDataSharing.value) loadDataSharingList()
  emit('complete', isComplete.value)
})

// ── 저장 ──────────────────────────────────────────────────
const save = async () => {
  console.log('[Step2] 저장 시도:', { selectedOptions: selectedOptions.value, productForm: productForm.value })
  if (!selectedOptions.value.length) {
    alert('가입유형에서 변경 항목을 선택해 주세요.')
    return false
  }
  if (hasOption('WIRELESS_BLOCK') && !wirelessBlockConfirmed.value) {
    alert('무선데이터차단: [확인] 버튼을 클릭해 주세요.')
    return false
  }
  if (hasOption('INFO_LIMIT') && !infoLimitConfirmed.value) {
    alert('정보료 상한금액: [확인] 버튼을 클릭해 주세요.')
    return false
  }
  if (hasOption('ADDITION') && !additionConfirmed.value) {
    alert('부가서비스: [확인] 버튼을 클릭해 주세요.')
    return false
  }
  if (hasOption('RATE_CHANGE')) {
    const soc = productForm.value.recommendedRatePlan || productForm.value.ratePlanSearchResult
    if (!soc) { alert('요금상품 변경: 변경할 요금제를 선택해 주세요.'); return false }
    if (!productForm.value.rateChangeSchedule) { alert('요금상품 변경: 변경일시(즉시/예약)를 선택해 주세요.'); return false }
    if (!rateConfirmed.value) { alert('요금상품 변경: [확인] 버튼을 클릭해 주세요.'); return false }
  }
  if (hasOption('NUM_CHANGE') && !productForm.value.numChange) {
    alert('번호변경: 희망번호를 선택해 주세요.')
    return false
  }
  if (hasOption('LOST_RESTORE') && !pauseConfirmed.value) {
    alert('분실복구/일시정지해제: [확인] 버튼을 클릭해 주세요.')
    return false
  }
  if (hasOption('USIM_CHANGE')) {
    if (!productForm.value.usimSimType) { alert('USIM 변경: SIM 보유를 선택해 주세요.'); return false }
    if (!productForm.value.usimChange) { alert('USIM 변경: USIM번호를 입력해 주세요.'); return false }
    if (!usimChecked.value) { alert('USIM 변경: USIM번호 유효성 체크를 완료해 주세요.'); return false }
  }
  if (hasOption('DATA_SHARING') && dataSharingMode.value === 'JOIN' && !dataSharingAuthed.value) {
    alert('데이터쉐어링: 휴대폰번호 인증을 완료해 주세요.')
    return false
  }
  formStore.setProductForm(productForm.value)
  return true
}

defineExpose({ save })
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
.product-btn-action {
  @apply px-4 py-2 rounded border border-teal-600 text-teal-700 text-sm font-medium hover:bg-teal-50 disabled:opacity-40 disabled:cursor-not-allowed;
}
.product-select {
  @apply rounded-lg border border-gray-300 px-3 py-2 text-sm bg-white focus:border-teal-500 focus:ring-1 focus:ring-teal-500;
}
.confirm-done-text {
  @apply text-sm font-medium text-teal-700 flex items-center gap-2;
}
.reselect-link {
  @apply text-xs text-gray-400 underline hover:text-gray-600 font-normal;
}
</style>
