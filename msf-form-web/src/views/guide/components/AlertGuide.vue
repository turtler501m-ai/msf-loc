<template>
  <div class="guide-page">
    <GuideUnit
      name="Alert"
      title="공통 Alert"
      description="개발 공통으로 만드신 유틸 사용 (줄바꿈 \n 또는 br태그 사용가능)<br/>
      ※ showAlertWithId, showConfirmWithId 는 호출시 앞에 customId를 넘겨서 여러번 띄워지는 경우 반복 생성 폭탄을 방지합니다. (예: API 호출 에러 Alert)"
      caseTitle="import { showAlert, showConfirm, showAlertWithId, showConfirmWithId } from '@/libs/utils/comp.utils'"
    >
      <template #cases>
        <GuideSourceBox :source="selfSource" id="ex1" title="1. showAlert">
          <!-- 1. 알림창 (Alert) 표시 -->
          <MsfButtonGroup align="left">
            <!-- 알림메시지 -->
            <MsfButton @click="showAlert('알림 메세지입니다.')">알림메시지</MsfButton>
            <!-- 알림메시지 + 확인콜백함수 -->
            <MsfButton
              @click="
                showAlert('알림 메세지입니다.\n줄바꿈 입니다.', () => {
                  console.log('확인 버튼 클릭')
                })
              "
            >
              알림메시지 + 확인콜백함수
            </MsfButton>
            <!-- 알림메시지 + 확인콜백함수 + 하위메시지 -->
            <MsfButton
              @click="
                showAlert(
                  '알림 메세지입니다.',
                  () => {
                    console.log('확인 버튼 클릭')
                  },
                  '알림 하위 메세지입니다.<br/>줄바꿈 입니다.',
                )
              "
            >
              알림메시지 + 확인콜백함수 + 하위메시지
            </MsfButton>
          </MsfButtonGroup>
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex2" title="2. showConfirm">
          <!-- 2. 확인창 (Confirm) 표시 -->
          <MsfButtonGroup align="left">
            <!-- 확인메시지 -->
            <MsfButton @click="showConfirm('확인 메세지입니다.')">확인메시지</MsfButton>
            <!-- 확인메시지 + 확인콜백함수 -->
            <MsfButton
              @click="
                showConfirm('확인 메세지입니다.', () => {
                  console.log('확인 버튼 클릭')
                })
              "
              >확인메시지 + 확인콜백함수
            </MsfButton>
            <!-- 확인메시지 + 확인콜백함수 + 하위메시지 -->
            <MsfButton
              @click="
                showConfirm(
                  '확인 메세지입니다.',
                  () => {
                    console.log('확인 버튼 클릭')
                  },
                  '확인 하위 메세지입니다.',
                )
              "
            >
              확인메시지 + 확인콜백함수 + 하위메시지
            </MsfButton>
            <!-- 확인메시지 + 확인콜백함수 + 하위메시지 + 취소콜백함수 -->
            <MsfButton
              @click="
                showConfirm(
                  '확인 메세지입니다.',
                  () => {
                    console.log('확인 버튼 클릭')
                  },
                  '확인 하위 메세지입니다.',
                  () => {
                    console.log('취소 버튼 클릭')
                  },
                )
              "
            >
              확인메시지 + 확인콜백함수 + 하위메시지 + 취소콜백함수
            </MsfButton>
          </MsfButtonGroup>
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex3" title="3. showAlertWithId">
          <!-- 3. 중복 방지 알림창 (Alert With ID) 표시 -->
          <MsfButtonGroup align="left">
            <!-- 알림메시지 -->
            <MsfButton @click="handleShowAlertId">중복방지 알림메시지</MsfButton>
            <!--
              showAlertWithId('alert-custom-id', '제목', handleSampleAlert, '메시지입니다.')
            -->
          </MsfButtonGroup>
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex4" title="4. showConfirmWithId">
          <!-- 4. 중복 방지 확인창 (Confirm With ID) 표시 -->
          <MsfButtonGroup align="left">
            <!-- 확인메시지 -->
            <MsfButton @click="handleShowConfirmId">중복방지 확인메시지</MsfButton>
            <!--
              showConfirmWithId('confirm-custom-id', '제목', handleSampleAlert, '메시지입니다.')
            -->
          </MsfButtonGroup>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './AlertGuide.vue?raw'
import { showAlert, showConfirm, showAlertWithId, showConfirmWithId } from '@/libs/utils/comp.utils'
// import { useAlert } from '@/hooks/useAlert'

// Alert 열기
// const { showAlert } = useAlert()

// 샘플 콜백함수
const handleSampleAlert = () => {
  console.log('처리가 완료되었습니다.')
}
// showAlertWithId 예시
const handleShowAlertId = () => {
  // showAlert('제목1', handleSampleAlert, '메시지입니다.1')
  // showAlert('제목2', handleSampleAlert, '메시지입니다.2')
  // showAlert('제목3', handleSampleAlert, '메시지입니다.3')
  // showAlert('제목4', handleSampleAlert, '메시지입니다.4')
  // showAlert('제목5', handleSampleAlert, '메시지입니다.5')
  showAlertWithId('alert-custom-id', '제목1', handleSampleAlert, '메시지입니다.1')
  showAlertWithId('alert-custom-id', '제목2', handleSampleAlert, '메시지입니다.2')
  showAlertWithId('alert-custom-id', '제목3', handleSampleAlert, '메시지입니다.3')
  showAlertWithId('alert-custom-id', '제목4', handleSampleAlert, '메시지입니다.4')
  showAlertWithId('alert-custom-id', '제목5', handleSampleAlert, '메시지입니다.5')
}
// showConfirmWithId 예시
const handleShowConfirmId = () => {
  // showConfirm('제목1', handleSampleAlert, '메시지입니다.1')
  // showConfirm('제목2', handleSampleAlert, '메시지입니다.2')
  // showConfirm('제목3', handleSampleAlert, '메시지입니다.3')
  // showConfirm('제목4', handleSampleAlert, '메시지입니다.4')
  // showConfirm('제목5', handleSampleAlert, '메시지입니다.5')
  showConfirmWithId('confirm-custom-id', '제목1', handleSampleAlert, '메시지입니다.1')
  showConfirmWithId('confirm-custom-id', '제목2', handleSampleAlert, '메시지입니다.2')
  showConfirmWithId('confirm-custom-id', '제목3', handleSampleAlert, '메시지입니다.3')
  showConfirmWithId('confirm-custom-id', '제목4', handleSampleAlert, '메시지입니다.4')
  showConfirmWithId('confirm-custom-id', '제목5', handleSampleAlert, '메시지입니다.5')
}
</script>
