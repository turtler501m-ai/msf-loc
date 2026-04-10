<template>
  <div class="guide-page">
    <GuideUnit
      name="Alert"
      title="Alert"
      description="import { useAlert } from '@/hooks/useAlert';<br/>const { showAlert } = useAlert()"
      caseTitle="useAlert import 후 사용"
    >
      <template #cases>
        <GuideSourceBox :source="selfSource" id="ex1" title="메시지">
          <!-- 
              import { useAlert } from '@/hooks/useAlert'
              const { showAlert } = useAlert()

              const handleSimpleAlert = () => { showAlert('처리가 완료되었습니다.') }
          -->
          <MsfButton @click="handleSimpleAlert">기본 알럿</MsfButton>
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex2" title="타이틀 + 메시지">
          <!-- 
              import { useAlert } from '@/hooks/useAlert'
              const { showAlert } = useAlert()
          -->
          <MsfButton
            @click="
              showAlert({
                title: '인증번호 유효시간이 종료되었습니다.',
                message: `[인증번호 재발송] 버튼을 클릭하시면,\n인증번호가 재발송 됩니다.`,
                onConfirm: () => console.log('삭제 확정!'),
                showCancel: true,
                labelProps: { confirm: '삭제', cancel: '취소' },
                // onCancel: () => console.log('취소 눌림하고 닫힐거임!'),
              })
            "
          >
            타이틀 + 메시지
          </MsfButton>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './AlertGuide.vue?raw'
import { useAlert } from '@/hooks/useAlert'

// Alert 열기
const { showAlert } = useAlert()

// 단순 메시지
const handleSimpleAlert = () => {
  showAlert('처리가 완료되었습니다.')
}
</script>
