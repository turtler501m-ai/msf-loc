<script setup>
import { defineModel } from 'vue'

const model = defineModel({ type: Object, required: true })

function update(value) {
  const { serviceSelect, serviceList } = model.value

  // 현재 선택된 것 중 동시변경 불가 서비스 존재 여부
  const hasLocked = serviceSelect.some((selected) =>
    serviceList.some((item) => item.value === selected && item.notConcurrentChange),
  )

  model.value.serviceList = serviceList.map((item) => ({
    ...item,
    disabled: hasLocked && item.notConcurrentChange && !serviceSelect.includes(item.value),
  }))
}
</script>
<template>
  <!-- 서비스 변경 선택_type01 -->
  <MsfTitleArea title="서비스 변경 선택_type01__디자인미확정" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="서비스 선택" tag="div" required>
      <MsfStack vertical>
        <MsfCheckbox v-model="model.allCheck" label="전체 선택" />
        <MsfChip
          v-model="model.serviceSelect"
          name="inp-serviceSelect"
          :data="model.serviceList"
          multiple
          @changeValue="update"
        />
        <MsfTextList margin="0">
          <li>
            동시 변경 불가능 업무
            <MsfTextList type="dash">
              <li>요금제 변경, 번호변경, 분실복구/일시정지해제 신청</li>
            </MsfTextList>
          </li>
          <li>번호변경 : 평일 오전10시~오후8시까지 가능 (주말 공휴일은 변경불가)</li>
        </MsfTextList>
      </MsfStack>
      <MsfButtonGroup borderTop align="left">
        <MsfButton variant="toggle" disabled>서비스 체크</MsfButton>
      </MsfButtonGroup>
    </MsfFormGroup>
    <MsfFormGroup label="대리점" tag="div" required>
      <MsfSelect
        title="대리점 선택"
        v-model="model.agency"
        :options="[
          { label: '대리점1', value: 'agency1' },
          { label: '대리점2', value: 'agency2' },
        ]"
        class="ut-w-300"
        placeholder="대리점 선택"
      />
    </MsfFormGroup>
  </MsfStack>
  <!-- // 서비스 변경 선택_type01 -->
  <!-- 서비스 변경 선택_type02 -->
  <MsfTitleArea title="서비스 변경 선택_type02__디자인미확정" />
  <MsfStack vertical type="formgroups">
    <MsfFormGroup label="요금제/부가<br/>서비스" tag="div">
      <MsfChip
        v-model="model.addonService"
        name="inp-addonService"
        :data="[
          { value: 'addonService1', label: '무선데이터차단 서비스' },
          { value: 'addonService2', label: '정보료 상한금액 설정/변경' },
          { value: 'addonService3', label: '부가서비스 신청/변경' },
          { value: 'addonService4', label: '요금제 변경' },
          { value: 'addonService5', label: '단말보험 가입' },
        ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="결합서비스" tag="div">
      <MsfChip
        v-model="model.combinedService"
        name="inp-combinedService"
        :data="[
          { value: 'combinedService1', label: '데이터쉐어링 가입/해지' },
          { value: 'combinedService2', label: '아무나 SOLO 결합' },
        ]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="일시/분실정지" tag="div">
      <MsfChip
        v-model="model.loseLock"
        name="inp-loseLock"
        :data="[{ value: 'loseLock', label: '분실복구/일시정지해제 신청' }]"
      />
    </MsfFormGroup>
    <MsfFormGroup label="가입정보 변경" tag="div">
      <MsfStack vertical>
        <MsfChip
          v-model="model.joinInfoChange"
          name="inp-joinInfoChange"
          :data="[
            { value: 'joinInfoChange1', label: '번호변경' },
            { value: 'joinInfoChange2', label: 'USIM 변경' },
          ]"
        />
        <MsfTextList margin="0">
          <li>
            동시 변경 불가능 업무
            <MsfTextList type="dash">
              <li>요금제 변경, 번호변경, 분실복구/일시정지해제 신청</li>
            </MsfTextList>
          </li>
          <li>번호변경 : 평일 오전10시~오후8시까지 가능 (주말 공휴일은 변경불가)</li>
        </MsfTextList>
      </MsfStack>
      <MsfButtonGroup borderTop align="left">
        <MsfButton variant="toggle">서비스 체크</MsfButton>
      </MsfButtonGroup>
    </MsfFormGroup>
  </MsfStack>
  <!-- // 서비스 변경 선택_type02 -->
</template>
