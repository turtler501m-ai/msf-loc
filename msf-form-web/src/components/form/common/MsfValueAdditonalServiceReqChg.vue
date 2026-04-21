<script setup>
import { defineModel } from 'vue'

const model = defineModel({ type: Object, required: true })
</script>
<template>
  <!-- 부가서비스 신청/변경 -->
  <MsfTitleArea title="부가서비스 신청/변경" />
  <MsfTable>
    <template #colgroup>
      <col />
      <col style="width: 120px" />
      <col style="width: 112px" />
    </template>
    <template #tbody>
      <tr>
        <th colspan="3" class="ut-text-left">무료 부가서비스</th>
      </tr>
      <tr>
        <td>무선데이터 차단</td>
        <td class="ut-text-center">무료</td>
        <td class="ut-text-center"></td>
      </tr>
      <tr>
        <td>불법TM수신차단</td>
        <td class="ut-text-center">무료</td>
        <td class="ut-text-center">
          <MsfButton variant="subtle">설정</MsfButton>
        </td>
      </tr>
      <tr>
        <td colspan="3">
          <div class="nodata-wrap">선택한 무료 서비스가 없습니다.</div>
        </td>
      </tr>
      <tr>
        <th colspan="3" class="ut-text-left">유료 부가서비스</th>
      </tr>
      <tr>
        <td>링투유</td>
        <td class="ut-text-center">990 원</td>
        <td class="ut-text-center"></td>
      </tr>
      <tr>
        <td>캐치콜</td>
        <td class="ut-text-center">550 원</td>
        <td class="ut-text-center"></td>
      </tr>
      <tr>
        <td>(신)로밍 하루종일 ON</td>
        <td class="ut-text-center">2,200 원/1일</td>
        <td class="ut-text-center">
          <MsfButton variant="subtle">설정</MsfButton>
        </td>
      </tr>
      <tr>
        <td colspan="3">
          <div class="nodata-wrap">선택한 유료 서비스가 없습니다.</div>
        </td>
      </tr>
    </template>
  </MsfTable>
  <!-- 합계박스 -->
  <MsfBox>
    <div class="total-box">
      <dl>
        <dt>합계(VAT 포함)</dt>
        <dd><em>3,740</em><span class="unit">원</span></dd>
      </dl>
    </div>
    <MsfButtonGroup class="total-btns">
      <MsfButton variant="subtle">추가/삭제</MsfButton>
      <MsfButton variant="toggle" disabled>확인</MsfButton>
      <MsfButton variant="toggle" active>확인 완료</MsfButton>
    </MsfButtonGroup>
  </MsfBox>
  <!-- // 합계박스 -->
  <!-- // 부가서비스 신청/변경 -->
</template>
