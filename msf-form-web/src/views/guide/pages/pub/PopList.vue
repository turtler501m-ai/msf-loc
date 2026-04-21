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
          <MsfButton @click="S102030104Open = true">(신)로밍 하루종일 ON (S102030104)</MsfButton>
          <MsfButton @click="S102030105Open = true">사용요금 조회 (S102030105)</MsfButton>
          <MsfButton @click="S102030102Open = true">불법TM수신차단 (S102030102)</MsfButton>
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
