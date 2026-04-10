<template>
  <!-- 신청 완료 페이지 -->
  <div class="completed-page">
    <div class="completed-msg">
      <img src="@/assets/images/completedIcon.svg" alt="완료" />
      <p class="user-name"><em class="ut-color-accent">홍길*</em>님</p>
      <div class="info-txt">
        <strong> 작성하신 신청서가 작성 완료되었습니다.</strong>
        <p class="desc">고객님께 신청서를 발송해 주세요.</p>
      </div>
    </div>
    <div class="box-layout">
      <MsfAppViewer class="box-item" />
      <MsfAppSender class="box-item" />
    </div>
    <MsfTextList
      :items="[
        '개통한 전화번호와 가입자 연락처 휴대폰번호로 동시에 발송됩니다.',
        '고객님께서 신청서 출력을 원하실 경우 관리자 사이트에서 출력하여 제공해 주시기 바랍니다.',
      ]"
    />
    <MsfBox class="kt-box-wrap">
      <strong class="box-tit">KT M모바일</strong>
      <div class="section-wrap">
        <div class="sec left">
          <div class="ktmmobile-info">
            <img src="@/assets/images/ktmmobile.svg" alt="kt m mobile" />
            <p class="desc">
              KT M 모바일에서 제공하는 서비스를 이용할수 있도록 고객에게 안내 발송해 주세요.<br />
              신규가입에서 부가서비스의 신청/변경, 실시간 사용량 조회까지 다양한 서비스를 이용하실
              수 있습니다.
            </p>
          </div>
        </div>
        <div class="sec right">
          <MsfStack type="field" nowrap>
            <MsfInput
              v-model="phoneNumber"
              placeholder="휴대폰번호 ‘-’ 없이 입력"
              class="ut-flex-1"
            />
            <MsfButton variant="subtle">발송</MsfButton>
          </MsfStack>
        </div>
      </div>
    </MsfBox>
  </div>
</template>
<script setup>
import { ref } from 'vue'

const phoneNumber = ref('') // 최하단 폰번호
</script>
<style lang="scss" scoped></style>
