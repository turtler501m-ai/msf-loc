<template>
  <MsfDialog
    v-bind="$attrs"
    :is-open="modelValue"
    title="부가서비스 추가/삭제"
    @open="emit('open')"
    @close="onClose"
  >
    <!-- 팝업 내용 -->
    <MsfSelect
      title="추천 부가서비스"
      v-model="service"
      :options="[
        { label: '추천 부가서비스1', value: 'service1' },
        { label: '추천 부가서비스2', value: 'service2' },
      ]"
      placeholder="추천 부가서비스"
    />
    <MsfTitleArea title="무료 부가 서비스" level="2" />
    <MsfTable>
      <template #colgroup>
        <col style="width: 68px" />
        <col />
        <col style="width: 120px" />
        <col style="width: 130px" />
      </template>
      <template #thead>
        <tr>
          <th>선택</th>
          <th>부가서비스명</th>
          <th>요금</th>
          <th>설정</th>
        </tr>
      </template>
      <template #tbody>
        <tr>
          <td class="ut-text-center">
            <MsfCheckbox id="inp-check1" v-model="check1" label="(신)로밍 하루종일 ON" hideLabel />
          </td>
          <td><label for="inp-check1">(신)로밍 하루종일 ON</label></td>
          <td class="ut-text-center">무료</td>
          <td class="ut-text-center"><MsfButton variant="subtle">설정</MsfButton></td>
        </tr>
        <tr>
          <td class="ut-text-center">
            <MsfCheckbox id="inp-check2" v-model="check2" label="(신)로밍 하루종일 ON" hideLabel />
          </td>
          <td><label for="inp-check2">(신)로밍 하루종일 ON</label></td>
          <td class="ut-text-center">무료</td>
          <td class="ut-text-center"><MsfButton variant="subtle">설정완료</MsfButton></td>
        </tr>
        <tr>
          <td colspan="4">
            <div class="nodata-wrap">추가 가능한 무료 부가서비스가 없습니다.</div>
          </td>
        </tr>
      </template>
    </MsfTable>
    <MsfTitleArea title="유료 부가 서비스" level="2" />
    <MsfTable>
      <template #colgroup>
        <col style="width: 68px" />
        <col />
        <col style="width: 120px" />
        <col style="width: 130px" />
      </template>
      <template #thead>
        <tr>
          <th>선택</th>
          <th>부가서비스명</th>
          <th>요금</th>
          <th>설정</th>
        </tr>
      </template>
      <template #tbody>
        <tr>
          <td class="ut-text-center">
            <MsfCheckbox id="inp-check3" v-model="check3" label="(신)로밍 하루종일 ON" hideLabel />
          </td>
          <td><label for="inp-check3">(신)로밍 하루종일 ON</label></td>
          <td class="ut-text-center">2,200 원/1일</td>
          <td class="ut-text-center"><MsfButton variant="subtle">설정</MsfButton></td>
        </tr>
        <tr>
          <td class="ut-text-center">
            <MsfCheckbox id="inp-check4" v-model="check4" label="(신)로밍 하루종일 ON" hideLabel />
          </td>
          <td><label for="inp-check4">(신)로밍 하루종일 ON</label></td>
          <td class="ut-text-center">2,200 원/1일</td>
          <td class="ut-text-center"><MsfButton variant="subtle">설정완료</MsfButton></td>
        </tr>
        <tr>
          <td colspan="4">
            <div class="nodata-wrap">추가 가능한 유료 부가서비스가 없습니다.</div>
          </td>
        </tr>
      </template>
    </MsfTable>
    <!-- 하단 고정 -->
    <template #footer>
      <MsfButtonGroup>
        <MsfButton variant="secondary" @click="onClose">취소</MsfButton>
        <MsfButton variant="primary">확인</MsfButton>
      </MsfButtonGroup>
    </template>
  </MsfDialog>
</template>

<script setup>
import MsfButton from '@/libs/ui/base/MsfButton.vue'
import { ref } from 'vue'

const props = defineProps({
  modelValue: Boolean,
})

const emit = defineEmits(['update:modelValue', 'open', 'close'])

const service = ref('') // 부가서비스 선택항목
// 체크항목
const check1 = ref(false)
const check2 = ref(false)
const check3 = ref(false)
const check4 = ref(false)

// 닫힘 이벤트
const onClose = () => {
  if (props.modelValue) {
    emit('update:modelValue', false)
    emit('close')
  }
}
</script>

<style lang="scss" scoped>
.ut-text-desc {
  padding-bottom: rem(12px);
  margin-bottom: rem(12px);
  border-bottom: var(--border-width-base) solid var(--color-gray-150);
}
</style>
