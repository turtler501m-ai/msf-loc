<template>
  <div class="pub-popup-wrap">
    <p class="pub-popup-info">※ 각 버튼명 ID값을 파일명으로 찾으시면 됩니다.</p>
    <div class="pub-popup-list">
      <!-- 팝업 목록 -->
      <div class="popup-list-items">
        <p class="popup-list-title">신규/변경 신청서 팝업목록</p>
        <MsfButtonGroup align="left">
          <MsfButton @click="S101020109Open = true">신분증 목록 조회 (S101020109)</MsfButton>
          <MsfButton @click="S101020110Open = true">구비서류 (S101020110)</MsfButton>
          <MsfButton @click="S101020108Open = true">안내동의 상세 (S101020108)</MsfButton>
          <MsfButton @click="S101030104Open = true">번호이동 사전동의 실패 (S101030104)</MsfButton>
          <MsfButton @click="S101030105Open = true">신규번호 검색 (S101030105)</MsfButton>
          <MsfButton @click="S101050103Open = true">신청서 열람 (S101050103)</MsfButton>
          <MsfButton @click="S101040104Open = true">신청서 확인 (S101040104)</MsfButton>
          <MsfButton @click="S101050102Open = true">비밀번호 입력 (S101050102)</MsfButton>
          <MsfButton @click="S101030106Open = true">부가서비스 추가/삭제 (S101030106)</MsfButton>
          <MsfButton @click="S101020106Open = true">주소검색 (S101020106)</MsfButton>
          <MsfButton @click="S101020104Open = true">안면인증 (S101020104)</MsfButton>
        </MsfButtonGroup>
      </div>
      <div class="popup-list-items">
        <p class="popup-list-title">서비스변경 신청서 팝업목록</p>
        <MsfButtonGroup align="left">
          <MsfButton @click="S102030103Open = true">번호도용 차단 서비스 (S102030103)</MsfButton>
          <MsfButton @click="S102030105Open = true">사용요금 조회 (S102030105)</MsfButton>
          <MsfButton @click="S102030102Open = true">불법TM수신차단 (S102030102)</MsfButton>
        </MsfButtonGroup>
        <p class="ut-weight-semibold ut-mt-30 ut-mb-5">
          ※ 상품_부가서비스명_수정 및 신규__20260427
        </p>
        <MsfButtonGroup align="left">
          <MsfButton @click="S102030104Open = true">부가서비스명 (S102030104)</MsfButton>
          <MsfButton @click="S102030106Open = true">로밍 시작일 설정 (S102030106)</MsfButton>
          <MsfButton @click="S102030107Open = true">함께쓰는 로밍 대표 설정 (S102030107)</MsfButton>
          <MsfButton @click="S102030108Open = true">함께쓰는 로밍 서브 설정 (S102030108)</MsfButton>
          <MsfButton @click="S102030109Open = true"
            >하루종일 로밍 베이직 투게더(대표) (S102030109)</MsfButton
          >
          <MsfButton @click="S102030110Open = true"
            >하루종일 로밍 베이직 투게더(서브) (S102030110)</MsfButton
          >

          <MsfButton @click="S102030111Open = true">특정번호 수신차단서비스 (S102030111)</MsfButton>
          <MsfButton @click="S102030112Open = true"
            >정보제공사업자번호차단서비스 (S102030112)</MsfButton
          >
          <MsfButton @click="S102030113Open = true"
            >데이터로밍요금알림(태블릿PC용) (S102030113)</MsfButton
          >
          <MsfButton @click="S102030114Open = true">로밍 해외도착알리미 (S102030114)</MsfButton>
          <MsfButton @click="S102030115Open = true">My time plan_MVNO 전용 (S102030115)</MsfButton>
          <MsfButton @click="S102030116Open = true">망내 1회선 무료통화 (S102030116)</MsfButton>
        </MsfButtonGroup>
      </div>
      <div class="popup-list-items">
        <p class="popup-list-title">메인 팝업목록</p>
        <MsfButtonGroup align="left">
          <MsfButton @click="S106040102Open = true">공지사항 (S106040102)</MsfButton>
          <MsfButton @click="S106040103Open = true">Q&A (S106040103)</MsfButton>
          <MsfButton @click="S106040104Open = true">Q&A 등록 (S106040104)</MsfButton>
        </MsfButtonGroup>
      </div>
    </div>
  </div>

  <!-- 팝업 파일 -->
  <S101020109 v-model="S101020109Open" />
  <S101020110 v-model="S101020110Open" />
  <S101020108 v-model="S101020108Open" />
  <S101030104 v-model="S101030104Open" />
  <S101030105 v-model="S101030105Open" />
  <S101050103 v-model="S101050103Open" />
  <S101050102 v-model="S101050102Open" />
  <S101030106 v-model="S101030106Open" />
  <S101040104 v-model="S101040104Open" />
  <S101020106 v-model="S101020106Open" />
  <S102030103 v-model="S102030103Open" />
  <S102030104 v-model="S102030104Open" />
  <S102030105 v-model="S102030105Open" />
  <S102030102 v-model="S102030102Open" />
  <S101020104 v-model="S101020104Open" />
  <S106040102 v-model="S106040102Open" />
  <!-- <S106040102 v-model="S106040102Open" targetId="notice-01" /> -->
  <S106040103 v-model="S106040103Open" />
  <!-- <S106040103 v-model="S106040103Open" targetId="qna-02" /> -->
  <S106040104 v-model="S106040104Open" />
  <S102030111 v-model="S102030111Open" />
  <S102030112 v-model="S102030112Open" />
  <S102030113 v-model="S102030113Open" />
  <S102030114 v-model="S102030114Open" />
  <S102030115 v-model="S102030115Open" />
  <S102030116 v-model="S102030116Open" />
  <S102030106 v-model="S102030106Open" />
  <S102030107 v-model="S102030107Open" />
  <S102030108 v-model="S102030108Open" />
  <S102030109 v-model="S102030109Open" />
  <S102030110 v-model="S102030110Open" />
</template>

<script setup>
import { ref } from 'vue'
// 팝업
import S101020109 from '@/views/guide/pages/pub/S101020109.vue'
import S101020110 from '@/views/guide/pages/pub/S101020110.vue'
import S101020108 from '@/views/guide/pages/pub/S101020108.vue'
import S101030104 from '@/views/guide/pages/pub/S101030104.vue'
import S101030105 from '@/views/guide/pages/pub/S101030105.vue'
import S101050103 from '@/views/guide/pages/pub/S101050103.vue'
import S101050102 from '@/views/guide/pages/pub/S101050102.vue'
import S101030106 from '@/views/guide/pages/pub/S101030106.vue'
import S101040104 from '@/views/guide/pages/pub/S101040104.vue'
import S101020106 from '@/views/guide/pages/pub/S101020106.vue'
import S102030103 from '@/views/guide/pages/pub/S102030103.vue'
import S102030104 from '@/views/guide/pages/pub/S102030104.vue'
import S102030105 from '@/views/guide/pages/pub/S102030105.vue'
import S102030102 from '@/views/guide/pages/pub/S102030102.vue'
import S101020104 from '@/views/guide/pages/pub/S101020104.vue'
import S106040102 from '@/views/guide/pages/pub/S106040102.vue'
import S106040103 from '@/views/guide/pages/pub/S106040103.vue'
import S106040104 from '@/views/guide/pages/pub/S106040104.vue'
import S102030111 from '@/views/guide/pages/pub/S102030111.vue'
import S102030112 from '@/views/guide/pages/pub/S102030112.vue'
import S102030113 from '@/views/guide/pages/pub/S102030113.vue'
import S102030114 from '@/views/guide/pages/pub/S102030114.vue'
import S102030115 from '@/views/guide/pages/pub/S102030115.vue'
import S102030116 from '@/views/guide/pages/pub/S102030116.vue'
import S102030106 from '@/views/guide/pages/pub/S102030106.vue'
import S102030107 from '@/views/guide/pages/pub/S102030107.vue'
import S102030108 from '@/views/guide/pages/pub/S102030108.vue'
import S102030109 from '@/views/guide/pages/pub/S102030109.vue'
import S102030110 from '@/views/guide/pages/pub/S102030110.vue'
const S101020109Open = ref(false)
const S101020110Open = ref(false)
const S101020108Open = ref(false)
const S101030104Open = ref(false)
const S101030105Open = ref(false)
const S101050103Open = ref(false)
const S101050102Open = ref(false)
const S101030106Open = ref(false)
const S101040104Open = ref(false)
const S101020106Open = ref(false)
const S102030103Open = ref(false)
const S102030104Open = ref(false)
const S102030105Open = ref(false)
const S102030102Open = ref(false)
const S101020104Open = ref(false)
const S106040102Open = ref(false)
const S106040103Open = ref(false)
const S106040104Open = ref(false)
const S102030111Open = ref(false)
const S102030112Open = ref(false)
const S102030113Open = ref(false)
const S102030114Open = ref(false)
const S102030115Open = ref(false)
const S102030116Open = ref(false)
const S102030106Open = ref(false)
const S102030107Open = ref(false)
const S102030108Open = ref(false)
const S102030109Open = ref(false)
const S102030110Open = ref(false)
</script>

<style lang="scss" scoped>
.pub-popup-wrap {
  position: relative;
}
.pub-popup-info {
  font-size: 20px;
  font-weight: bold;
  margin-block: 10px 24px;
}
.pub-popup-list {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 56px;
  .popup-list-items {
    .popup-list-title {
      margin-bottom: 10px;
      font-size: 18px;
      font-weight: bold;
    }
  }
}
</style>
