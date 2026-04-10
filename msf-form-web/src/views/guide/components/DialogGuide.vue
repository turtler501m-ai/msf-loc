<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfDialog"
      title="MsfDialog"
      description="MsfDialog 코드를 페이지 내에서 선언하거나 파일로 정의하여 사용합니다."
      caseTitle="사용예시"
    >
      <template #cases>
        <GuideSourceBox :source="selfSource" id="ex1">
          <MsfButton @click="isModalOpen = true">기본 모달 열기</MsfButton>
          <!-- 기본팝업의 형태 -->
          <MsfDialog
            :isOpen="isModalOpen"
            title="팝업 타이틀"
            showClose
            @open="handleModalOpen"
            @close="handleModalClose"
          >
            <template #navBar> 상단고정 영역이 있다면 </template>
            <p v-for="n in 100" :key="n">자유로운 내용</p>
            <template #footer>
              <MsfButtonGroup align="center">
                <MsfButton variant="secondary">취소</MsfButton>
                <MsfButton variant="primary">확인</MsfButton>
              </MsfButtonGroup>
            </template>
          </MsfDialog>
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex2">
          <MsfButton @click="isDialogSampleOpen = true">DialogSample.vue 파일로 열기</MsfButton>
          <!-- Dialog를 파일로 생성했을 경우 (DialogSample.vue) -->
          <DialogSample
            v-model="isDialogSampleOpen"
            @open="console.log('열릴때 이벤트')"
            @close="console.log('닫힐때 이벤트')"
            @confirm="console.log('확인 이벤트')"
          />
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex3">
          <MsfButton @click="isPopinPopOpen = true">팝업 중첩</MsfButton>
          <!-- Dialog안에 Dialog -->
          <MsfDialog
            :isOpen="isPopinPopOpen"
            title="팝업 중첩 타이틀"
            showClose
            @close="isPopinPopOpen = false"
          >
            <template #navBar> 상단고정 영역이 있다면 </template>
            <MsfButtonGroup>
              <MsfButton @click="isInnerModal = true">팝업또연다</MsfButton>
              <MsfButton
                @click="
                  showAlert({
                    title: '인증번호 유효시간이 종료되었습니다.',
                    message: `[인증번호 재발송] 버튼을 클릭하시면,\n인증번호가 재발송 됩니다.`,
                    onConfirm: () => console.log('Confirm!'),
                    showCancel: true,
                    labelProps: { confirm: '삭제', cancel: '취소' },
                    // onCancel: () => console.log('취소 눌림하고 닫힘'),
                  })
                "
              >
                알럿
              </MsfButton>
            </MsfButtonGroup>
            <template #footer>
              <MsfButtonGroup align="center">
                <MsfButton variant="secondary">취소</MsfButton>
                <MsfButton variant="primary">확인</MsfButton>
              </MsfButtonGroup>
            </template>
            <!-- 내부 Dialog -->
            <MsfDialog
              size="small"
              :isOpen="isInnerModal"
              title="이것은 내부팝업"
              @close="isInnerModal = false"
            >
              <template #navBar> 상단고정 영역이 있다면 </template>
              <p v-for="n in 100" :key="n">자유로운 내용</p>
              <template #footer>
                <MsfButtonGroup align="center">
                  <MsfButton variant="secondary">취소</MsfButton>
                  <MsfButton variant="primary">확인</MsfButton>
                </MsfButtonGroup>
              </template>
            </MsfDialog>
          </MsfDialog>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './DialogGuide.vue?raw'
import { useAlert } from '@/hooks/useAlert'

// Alert 열기
const { showAlert } = useAlert()

// Dialog 파일 열기
import DialogSample from '@/views/guide/components/DialogSample.vue'
const isDialogSampleOpen = ref(false)
const isPopinPopOpen = ref(false)
const isInnerModal = ref(false) // 팝업내부 팝업

// Dialog 열기
const isModalOpen = ref(false)
// 모달이 열릴 때 실행될 함수 선언
const handleModalOpen = () => {
  console.log('modal 열림! 이벤트 선언하세요!')
}
// 모달이 닫힐 때 실행될 함수 선언
const handleModalClose = () => {
  isModalOpen.value = false
  console.log('modal 닫힘! 이벤트 선언하세요!')
}
</script>
