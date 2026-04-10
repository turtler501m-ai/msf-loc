<template>
  <div class="guide-page">
    <GuideUnit
      name="MsfTable"
      title="MsfTable"
      description="데이터 테이블이 아닌 일반 테이블에 사용합니다."
      caseTitle="테이블 예시"
    >
      <template #cases>
        <GuideSourceBox :source="selfSource" id="ex1">
          <MsfTable>
            <template #colgroup>
              <col style="width: 80px" />
              <col />
            </template>
            <template #thead>
              <tr>
                <th scope="col">제목</th>
                <th scope="col">제목</th>
              </tr>
            </template>
            <template #tbody>
              <tr>
                <td>내용</td>
                <td>내용</td>
              </tr>
            </template>
          </MsfTable>
        </GuideSourceBox>
        <GuideSourceBox :source="selfSource" id="ex2">
          <MsfTable>
            <template #colgroup>
              <col style="width: 160px" />
              <col />
            </template>
            <template #tbody>
              <tr>
                <th>레이블</th>
                <td>내용</td>
              </tr>
              <tr>
                <th>
                  <label for="labelId" class="label-txt required">레이블 연결 필수표시</label>
                </th>
                <td><MsfInput type="text" id="labelId" placeholder="입력해주세요." /></td>
              </tr>
              <tr>
                <th><span class="label-txt required">레이블 일반 텍스트</span></th>
                <td>내용</td>
              </tr>
            </template>
          </MsfTable>
        </GuideSourceBox>
      </template>
    </GuideUnit>
  </div>
</template>

<script setup>
import { GuideUnit, GuideSourceBox } from '@/views/guide/components'
import selfSource from './TableGuide.vue?raw'
</script>
