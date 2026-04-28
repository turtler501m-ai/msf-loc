<template>
  <div class="guide-wrap">
    <MsfButtonGroup align="center">
      <router-link to="/guide" target="_blank">
        <MsfButton role="link">퍼블리싱 가이드 바로가기</MsfButton>
      </router-link>
      <router-link to="/publishing" target="_blank">
        <MsfButton role="link">퍼블리싱 목록 바로가기</MsfButton>
      </router-link>
      <router-link to="/test" target="_blank">
        <MsfButton role="link">컴퍼넌트 테스트 바로가기</MsfButton>
      </router-link>
    </MsfButtonGroup>
    <div class="guide-item">
      <!-- 
        // 체크박스 returnData 사용 예시
        const isReturnData = ref('N')
      -->
      <p>
        ※ MsfCheckbox - :returndata 추가 (부모가 전달하는 { true: 'Y', false: 'N' } 객체를 받음)
      </p>
      <MsfCheckbox
        v-model="isReturnData"
        :returndata="{ true: 'Y', false: 'N' }"
        label="체크박스 변경"
      />
      <div class="ut-mt-4">
        <p>상태: {{ isReturnData === 'Y' ? '동의함' : '동의 안 함' }}</p>
        <p>디버깅용 현재값: {{ isReturnData }}</p>
      </div>
    </div>
    <div class="guide-item">
      <MsfButtonGroup align="left">
        <MsfButton @click="handleLoadingTest">로딩 (MsfLoadingComp) 5초간 띄우기</MsfButton>
        <MsfButton @click="handleShowAlert">알림창 (Alert)</MsfButton>
        <MsfButton @click="handleConfirmAlert">확인창 (Confirm)</MsfButton>
      </MsfButtonGroup>
      <MsfLoadingComp v-if="isLoading" />
    </div>
    <div class="guide-item">
      <MsfCollapse>
        <template #header>여기에 제목</template>
        <div>여기에 내용</div>
      </MsfCollapse>
    </div>
    <div class="guide-item">
      <MsfTextList :items="['사과', '바나나']" type="dot" />
      <MsfTextList :items="['공지사항', '이벤트']" type="star">
        <template #default="{ item }">
          <span style="color: blue">[중요]</span> {{ item }}
        </template>
      </MsfTextList>
      <MsfTextList type="dash">
        <li class="text-list-item">직접 작성한 항목 1</li>
        <li class="text-list-item">직접 작성한 항목 2</li>
      </MsfTextList>
    </div>
    <div class="guide-item">
      <MsfFlag data="가능" color="accent" />
      <MsfFlag data="불가능" color="gray" />
      <MsfFlag :data="['불가능', '불가능']" color="gray" radius="all" />
      <MsfFlag :data="myFlags" size="small" />
    </div>
    <div class="guide-item">
      <MsfTitleArea title="약관 동의" />
      <MsfAgreementGroup policy="join" ref="agreementRef" required />
      <br />
      <br />
      <div>
        <MsfButton :disabled="!isReady" @click="handleNext">약관동의 확인 및 다음 테스트</MsfButton>
        <p class="ut-mt-10">{{ agreementRef }}</p>
      </div>
    </div>
    <div class="guide-item">
      <MsfTitleArea title="컨텐츠 타이틀영역" desc="컨텐츠 타이틀영역 간단한 설명문구">
        <template #content>
          <div class="ut-d-flex">
            <p class="ut-flex-1 ut-text-desc">자유롭게 꾸밈 코드</p>
            <MsfButton>버튼</MsfButton>
          </div>
        </template>
      </MsfTitleArea>
    </div>
    <div class="guide-item">
      <MsfButton @click="isDialogSampleOpen = true">FileDialog.vue 파일을 여는 경우</MsfButton>
      <!-- Dialog를 파일로 생성했을 경우 -->
      <DialogSample
        v-model="isDialogSampleOpen"
        @open="console.log('열릴때 이벤트!')"
        @close="console.log('닫힐때 이벤트!')"
        @confirm="console.log('본문 버튼 클릭됨!')"
      />
    </div>
    <div class="guide-item">
      <MsfButtonGroup>
        <MsfButton prefixIcon="delete">삭제</MsfButton>
        <MsfButton prefixIcon="download">엑셀다운로드</MsfButton>
        <MsfButton prefixIcon="add">추가</MsfButton>
        <MsfButton variant="detail">상세보기</MsfButton>
        <MsfButton iconOnly="cart">아이콘버튼</MsfButton>
      </MsfButtonGroup>
    </div>
    <div class="guide-item">
      <MsfButtonGroup>
        <MsfButton variant="primary">primary</MsfButton>
        <MsfButton variant="secondary">secondary</MsfButton>
        <MsfButton variant="tertiary">tertiary</MsfButton>
        <MsfButton variant="accent1">Accent01</MsfButton>
        <MsfButton variant="accent2">Accent02</MsfButton>
        <MsfButton variant="subtle">검색</MsfButton>
        <!-- <MsfButton variant="ghost">ghost</MsfButton> -->
      </MsfButtonGroup>
    </div>
    <div class="guide-item">
      <MsfButtonGroup>
        <MsfButton @click="handleButtonClick">클릭이벤트</MsfButton>
        <MsfButton variant="primary" disabled>비활성화</MsfButton>
        <MsfButton variant="tertiary" disabled> tertiary 비활성화</MsfButton>
      </MsfButtonGroup>
    </div>
    <div class="guide-item">
      <MsfButtonGroup vertical>
        <MsfButton variant="primary">primary</MsfButton>
        <MsfButton variant="secondary">secondary</MsfButton>
        <MsfButton variant="tertiary">tertiary</MsfButton>
        <MsfButton variant="accent1">Accent01</MsfButton>
        <MsfButton variant="accent2">Accent02</MsfButton>
        <!-- <MsfButton variant="ghost">ghost</MsfButton> -->
      </MsfButtonGroup>
    </div>
    <div class="guide-item">
      <MsfInput v-model="name" placeholder="이름을 입력하세요" />
      <p>실시간 입력값: {{ name }}</p>
    </div>
    <div class="guide-item">
      <MsfInput
        v-model="address"
        placeholder="주소 검색을 위해 클릭하세요"
        readonly
        @click="openPostPopup"
      />
    </div>
    <div class="guide-item">
      <MsfInput
        v-model="address2"
        placeholder="주소 검색을 위해 클릭하세요"
        disabled
        @click="openPostPopup"
      />
    </div>
    <div class="guide-item">
      <!-- FormGroup에서 자동생성된 id값을 input에 부여함 -->
      <MsfFormGroup label="이메일 주소" v-slot="{ id }" required>
        <MsfInput :id="id" v-model="email" type="email" placeholder="example@mail.com" />
      </MsfFormGroup>
    </div>
    <div class="guide-item">
      <!-- Custom id 설정 + label과 연결이 되어야 한다면 id 맞추기 (FormGroup에만 설정해도 input에 전파됨) -->
      <MsfFormGroup id="my-custom-id" label="비밀번호" required>
        <MsfInput id="my-custom-id" v-model="password" type="password" :maxLength="20" />
      </MsfFormGroup>
    </div>
    <div class="guide-item">
      <MsfFormGroup id="my-custom-id2" label="비밀번호" required>
        <MsfInput id="my-custom-id2" v-model="password2" type="password" disabled />
      </MsfFormGroup>
    </div>
    <div class="guide-item">
      <!-- FormGroup안에 폼 두개인 경우 id값 분리 -->
      <MsfFormGroup label="비밀번호" required>
        <MsfInput v-model="password2" type="password" disabled />
        <MsfInput id="testId2-1" v-model="password2" type="password" disabled />
      </MsfFormGroup>
    </div>
    <div class="guide-item">
      <MsfCheckbox v-model="isAgreed" label="개인정보 수집 및 이용에 동의합니다" />
      <p>상태: {{ isAgreed ? '동의함' : '동의 안 함' }}</p>
    </div>
    <div class="guide-item">
      <MsfFormGroup label="체크라벨1" tag="div">
        <MsfCheckbox v-model="marketing" label="마케팅 정보 수신 동의" />
      </MsfFormGroup>
    </div>
    <div class="guide-item">
      <MsfFormGroup label="체크라벨2" tag="div">
        <MsfCheckbox v-model="mustCheck" label="로봇이 아닙니다" :invalid="!mustCheck" />
      </MsfFormGroup>
    </div>
    <div class="guide-item">
      <MsfFormGroup label="관심사 선택" tag="div" desc="여러 개 선택 가능합니다." :error="errorMsg">
        <MsfCheckboxGroup
          v-model="selectedInterests"
          :options="[
            { value: 'coding', label: '코딩' },
            { value: 'design', label: '디자인' },
            { value: 'design2', label: '디자인2' },
            { value: 'music', label: '음악', disabled: true },
          ]"
        />
      </MsfFormGroup>
      <p>선택된 항목: {{ selectedInterests }}</p>
    </div>
    <div class="guide-item">
      <MsfFormGroup label="관심사 선택" tag="div" desc="여러 개 선택 가능합니다." :error="errorMsg">
        <MsfCheckboxGroup
          v-model="selectedInterests"
          :options="[
            { value: 'coding', label: '코딩' },
            { value: 'design', label: '디자인' },
            { value: 'design2', label: '디자인2' },
            { value: 'music', label: '음악', disabled: true },
          ]"
        />
      </MsfFormGroup>
      <p>선택된 항목: {{ selectedInterests }}</p>
    </div>
    <div class="guide-item">
      <MsfFormGroup label="배송 방법" tag="div" :error="deliveryError">
        <div class="flex gap-4">
          <MsfRadio v-model="deliveryMethod" value="standard" label="일반 배송" />
          <MsfRadio v-model="deliveryMethod" value="express" label="새벽 배송" />
        </div>
      </MsfFormGroup>
      <p>선택된 값: {{ deliveryMethod }}</p>
    </div>
    <div class="guide-item">
      <MsfFormGroup label="라디오" tag="div" desc="설명 메시지" required>
        <MsfRadioGroup
          name="radio-option"
          v-model="deliveryMethod2"
          :options="[
            { value: 'option1', label: '옵션 1' },
            { value: 'option2', label: '옵션 2' },
            { value: 'option3', label: '옵션 3', disabled: true },
            { value: 'option4', label: '옵션 4', disabled: true },
          ]"
          :error="true"
        />
      </MsfFormGroup>
    </div>
    <div class="guide-item">
      <MsfFormGroup label="도시 선택" tag="div">
        <MsfSelect
          title="도시 선택"
          v-model="selectedCity"
          :options="[
            { label: '서울', value: 'seoul' },
            { label: '부산1', value: 'busan1' },
            { label: '부산2', value: 'busan2' },
            { label: '부산3', value: 'busan3' },
            { label: '부산4', value: 'busan4' },
            { label: '부산5', value: 'busan5' },
            { label: '부산6', value: 'busan6' },
            { label: '제주', value: 'jeju', disabled: true },
          ]"
          placeholder="유형"
        />
      </MsfFormGroup>
    </div>
    <div class="guide-item">
      <MsfButtonGroup align="left">
        <MsfButton @click="isModalOpen = true">기본 모달 열기</MsfButton>
        <MsfButton variant="secondary" @click="isFullModal = true">약관 전체보기</MsfButton>
      </MsfButtonGroup>
    </div>
    <div class="guide-item">
      <MsfStack>
        <div :style="stackSampleStyle">Stack 1</div>
        <div :style="stackSampleStyle">Stack 2</div>
        <div :style="stackSampleStyle">Stack 3</div>
      </MsfStack>
    </div>
    <div class="guide-item">
      <MsfStack vertical>
        <div :style="stackSampleStyle">Stack 1</div>
        <div :style="stackSampleStyle">Stack 2</div>
        <div :style="stackSampleStyle">Stack 3</div>
      </MsfStack>
    </div>
    <div class="guide-item">
      <MsfButtonGroup>
        <MsfButton>이건버튼</MsfButton>
        <MsfButton>이것도버튼</MsfButton>
      </MsfButtonGroup>
    </div>
    <div class="guide-item">
      <MsfButtonGroup vertical>
        <MsfButton>이건버튼</MsfButton>
        <MsfButton>이것도버튼</MsfButton>
      </MsfButtonGroup>
    </div>
    <div class="guide-item">
      <p>달력123123</p>
      <MsfDateInput v-model="datePickerValue" class="ut-w-140" />
      <div>결과: {{ datePickerValue }}</div>
    </div>
    <div class="guide-item">
      <p>달력</p>
      <MsfDateInput v-model="datePickerValue" disabled />
      <div>결과: {{ datePickerValue }}</div>
    </div>
    <div class="guide-item">
      <p>centerd 모드 (앱에서 사용예정)</p>
      <MsfDateInput v-model="datePickerValue" centered />
      <div>결과: {{ datePickerValue }}</div>
    </div>
    <div class="guide-item">
      <MsfDateRange
        v-model:from="rangeDatePickerValue.start"
        v-model:to="rangeDatePickerValue.end"
      />
      <div>결과: {{ rangeDatePickerValue }}</div>
    </div>
    <div class="guide-item">
      <MsfDateRange
        :show-quick-buttons="true"
        v-model:from="rangeDatePickerValue.start"
        v-model:to="rangeDatePickerValue.end"
      />
      <div>결과: {{ rangeDatePickerValue }}</div>
    </div>
    <div class="guide-item">
      <p>단일 선택</p>
      <MsfChip
        v-model="selectedFruit"
        :data="fruitData"
        name="my-fruits"
        @change="handleSelectionChange"
        columns="auto"
      />
      <div>결과: {{ selectedFruit }}</div>
    </div>
    <div class="guide-item">
      <p>다중 + 전체 선택</p>
      <MsfChip
        v-model="selectedFruit2"
        :data="fruitData2"
        name="my-fruits2"
        @change="handleSelectionChange"
        multiple
      />
      <div>결과: {{ selectedFruit2 }}</div>
    </div>
    <div class="guide-item">
      <label>데이터 그리드(빈 데이터):</label>
      <MsfDataTable :columns="colDefs" :datas="[]" @selected="onSelected" />
      선택: {{ selectedRowEmpty }}
    </div>
    <div class="guide-item">
      <label>데이터 그리드:</label>
      <MsfDataTable
        :columns="colDefs"
        :datas="datas"
        :total="datas.length"
        @selected="onSelected"
      />
      선택: {{ selectedRow }}
    </div>
    <div class="guide-item">
      <label>데이터 그리드(싱글 선택):</label>
      <MsfDataTable
        :columns="colDefs"
        :datas="datas"
        :total="datas.length"
        show-single-check
        @selected="onSelected"
      />
      선택: {{ selectedRow }}
    </div>
    <div class="guide-item">
      <label>데이터 그리드(멀티 선택):</label>
      <MsfDataTable
        :columns="colDefs"
        :datas="datas"
        :total="datas.length"
        show-multi
        @selected="onSelectedMulti"
      />
      선택: {{ selectedRowMulti }}
    </div>
    <div class="guide-item">
      <label>데이터 그리드(페이징):</label>
      <MsfDataTable
        :columns="colDefsPaging"
        url="/products/search"
        :params="paramsPaging"
        show-paging
        :page="page"
        :total="total"
        :rows="rows"
        @selected="onSelectedPaging"
        @movePage="onMovePage"
      >
        <template #buttons>
          <MsfInput type="text" v-model="searchValuePaging">
            <template #right-slot>AAA</template>
          </MsfInput>
          <MsfButton @click="onClickSearchPaging">검색</MsfButton>
          <MsfButton @click="() => (page = 15)">15페이지 이동</MsfButton>
        </template>
      </MsfDataTable>
      페이지: {{ page }}, 선택: {{ selectedRowPaging }}
    </div>
    <div class="guide-item">
      <MsfPagination v-model:page="page" v-model:total="total" />
      선택: {{ page }}
    </div>
    <div class="guide-item">
      <!-- 컴퍼넌트로 쓰셔도 되고 -->
      <MsfIcon name="notice" />
      <MsfIcon name="cart" />
      <!-- 컴퍼넌트말고 불가피하게 태그로 써야할경우 -->
      <i class="msf-icon notice"></i>
      <i class="msf-icon cart"></i>
    </div>
    <div class="guide-item">
      <MsfInput v-model="name" placeholder="이름을 입력하세요" />
    </div>
    <div class="guide-item">
      <MsfFormGroup label="필드+버튼 조합" required>
        <MsfStack type="field">
          <MsfBirthdayInput v-model="birth6Value" />
          <MsfButton variant="tertiary">예??</MsfButton>
        </MsfStack>
      </MsfFormGroup>
    </div>
    <div class="guide-item">
      <MsfFormGroup label="생년월일 6자리 입력" tag="div" required>
        <MsfBirthdayInput v-model="birth6Value" />
      </MsfFormGroup>
      <p>결과: {{ birth6Value }}</p>
    </div>
    <div class="guide-item">
      <MsfFormGroup label="생년월일 8자리 입력" tag="div">
        <MsfBirthdayInput v-model="birth8Value" length="8" />
      </MsfFormGroup>
      <p>결과: {{ birth8Value }}</p>
    </div>
    <div class="guide-item">
      <MsfFormGroup label="숫자 입력" tag="div">
        <MsfNumberInput v-model="numberValue" maxlength="15" />
      </MsfFormGroup>
      <p>결과: {{ numberValue }}</p>
    </div>
    <div class="guide-item">
      <div>
        <p class="ut-text-heading1">Heading1</p>
        <p class="ut-text-heading2">Heading2</p>
        <p class="ut-text-heading3">Heading3</p>
        <p class="ut-text-title1">Title1</p>
        <p class="ut-text-title2">Title2</p>
        <p class="ut-text-body1">Body1</p>
        <p class="ut-text-body2">Body2</p>
        <p class="ut-text-body3">Body3</p>
        <p class="ut-text-caption">Caption</p>
        <br />
        <br />
        <p class="ut-color-gray">
          ※ 마케팅 수신동의 상태가 ‘거부’ 인 경우 발송되지 않습니다. 고객센터를 통해 설정 변경 요청
          후 진행하시기 바랍니다.
        </p>
        <p class="ut-color-point ut-weight-bold">빨간색 강조</p>
        <br />
        <br />
      </div>
    </div>
    <div class="guide-item">
      <MsfTable>
        <template #colgroup>
          <col />
          <col />
          <col />
        </template>
        <template #thead>
          <tr>
            <th scope="col">휴대폰 번호</th>
            <th scope="col">언어(전체 선택)</th>
            <th scope="col">삭제</th>
          </tr>
        </template>
        <template #tbody>
          <tr>
            <td><MsfInput placeholder="휴대폰 번호 입력 (“-” 제외)" /></td>
            <td>
              <MsfSelect
                v-model="selectedCity"
                :options="[
                  { label: '한국어', value: 'korea' },
                  { label: '영어', value: 'english' },
                  { label: '중국어', value: 'china' },
                ]"
                placeholder="언어"
              />
            </td>
            <td><MsfButton prefixIcon="delete">삭제</MsfButton></td>
          </tr>
        </template>
      </MsfTable>
    </div>
    <div class="guide-item">
      <div class="sample-inner-box">
        <div class="sample-left">
          <label>시간 입력 (시:분):</label>
          <MsfTimeInput v-model="timeValue" placeholder="00:00" />
          결과: {{ timeValue }}
        </div>
        <div class="vertical-divider"></div>
        <div class="sample-right">
          <label>시간 입력(시:분:초):</label>
          <MsfTimeInput v-model="timeWithSecondsValue" show-seconds placeholder="00:00:00" />
          결과: {{ timeWithSecondsValue }}
        </div>
      </div>
    </div>
    <div class="guide-item">
      <label>웹에디터:</label>
      <MsfWebEditor v-model="webEditorContent" />
      <br />
      <label>웹에디터 (높이지정 가능):</label>
      <MsfWebEditor v-model="webEditorContent" height="100px" />
      <br />
      입력: {{ webEditorContent }}
    </div>
    <div class="guide-item">
      <label>MsfTextarea height 지정가능 (기본값 200px 설정)</label>
      <MsfTextarea />
    </div>
    <div class="guide-item">
      <div>- 만들어주신 차트</div>
      <MsfStack gap="3">
        <MsfDoughnutChart :data="chartData" title="2026-03-12 현황" name-key="nm" value-key="cnt" />
        <MsfDoughnutChart
          :data="chartData2"
          title="2026-03-12 현황"
          name-key="nm"
          value-key="cnt"
        />
      </MsfStack>
    </div>
    <!-- 스크롤 테이블 필요시 사용 -->
    <div class="guide-item">
      <div>
        <h3 class="ut-mb-10">1. 스크롤형 (5개씩 페이징, 5줄 높이 고정)</h3>
        <MsfTableList
          :data="tableData"
          :columns="tableColumns"
          mode="paging"
          :row-view="5"
          :height="5"
          @row-click="onRowClick"
        >
          <template #colgroup>
            <col width="80px" />
            <col />
            <col width="120px" />
            <col width="100px" />
          </template>

          <template #thead>
            <tr>
              <th>번호</th>
              <th>제목</th>
              <th>등록일</th>
              <th>관리</th>
            </tr>
          </template>

          <template #tbody="{ items }">
            <tr
              v-for="item in items"
              :key="item.id"
              :class="{ 'is-selected': selectedId === item.id }"
              @click="onRowClick(item)"
            >
              <td>{{ item.id }}</td>
              <td class="ut-al">{{ item.title }}</td>
              <td>{{ item.date }}</td>
              <td>
                <Button size="small" @click.stop="handleDelete(item)"> 삭제 </Button>
              </td>
            </tr>
          </template>
        </MsfTableList>
      </div>

      <h3 class="ut-mb-10">1. 스크롤형 (10개씩 페이징, 5줄 높이 고정)</h3>
      <MsfTableList
        :data="tableData"
        :columns="tableColumns"
        mode="paging"
        :row-view="10"
        :height="5"
        :selected-id="selectedId"
        rowClick
        @row-click="onRowClick"
      />
      <br /><br />
      <h3 class="ut-mb-10">2. 확장형 (더보기 연결)</h3>
      <MsfTableList
        :data="tableData"
        :columns="tableColumns"
        mode="more"
        :row-view="4"
        :selected-id="selectedId"
        rowClick
        @row-click="onRowClick"
      />
      <br />
      <div v-if="selectedItem" class="selected-info-box ut-mt-20">
        <p class="info-label">선택된 항목 정보:</p>
        <div class="info-content">
          <span class="id">ID: {{ selectedItem.id }}</span>
          <span class="title">제목: {{ selectedItem.title }}</span>
          <span class="date">날짜: {{ selectedItem.date }}</span>
        </div>
      </div>
      <div v-else class="selected-info-box is-empty">
        <p>항목을 선택해 주세요.</p>
      </div>
    </div>
    <div class="guide-item">
      <MsfTable customCaption="이것을 나타냅니다.">
        <template #colgroup>
          <col style="width: 150px" />
          <col style="width: 300px" />
          <col />
        </template>
        <template #tbody>
          <tr v-for="(item, index) in list" :key="index">
            <th scope="row">{{ item.title }}</th>
            <td>
              <Input v-model="item.detail" placeholder="상세 내용" />
            </td>
            <td>
              <Textarea v-model="item.memo"></Textarea>
            </td>
          </tr>
        </template>
      </MsfTable>
      <br />
      <MsfTable>
        <template #colgroup>
          <col style="width: 150px" />
          <col style="width: 300px" />
          <col />
        </template>
        <template #tbody>
          <tr v-for="(item, index) in list" :key="index">
            <th scope="row">{{ item.title }}</th>
            <td>
              <Input v-model="item.detail" placeholder="상세 내용 입력" />
            </td>
            <td>
              <Textarea v-model="item.memo"></Textarea>
            </td>
          </tr>
        </template>
      </MsfTable>
      <br />
      <MsfTable>
        <template #colgroup>
          <col style="width: 150px" />
          <col style="width: 300px" />
          <col />
          <col style="width: 100px" />
        </template>
        <template #thead>
          <tr>
            <th>항목명</th>
            <th>상세 정보</th>
            <th>기타 메모</th>
            <th>작업</th>
          </tr>
        </template>
        <template #tbody>
          <tr v-for="(item, index) in list" :key="index">
            <th scope="row">{{ item.title }}</th>
            <td>
              <Input v-model="item.detail" class="form-control" placeholder="상세 내용 입력" />
            </td>
            <td>
              <Textarea v-model="item.memo" class="form-control"></Textarea>
            </td>
            <td style="text-align: center">
              <button @click="remove(index)" class="btn btn-danger">삭제</button>
            </td>
          </tr>
        </template>
      </MsfTable>
      <br />
    </div>
    <!-------------- 팝업 --------------->
    <!-- 기본모달 -->
    <MsfDialog
      :isOpen="isModalOpen"
      title="타이틀 타이틀 타이틀"
      showClose
      @open="handleModalOpen"
      @close="handleModalClose"
    >
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <p>자유로운 내용</p>
      <template #footer>
        <MsfButtonGroup align="center">
          <MsfButton variant="secondary">취소</MsfButton>
          <MsfButton variant="primary">확인</MsfButton>
        </MsfButtonGroup>
      </template>
    </MsfDialog>
    <!-- maximize 모달 -->
    <MsfDialog
      :isOpen="isFullModal"
      title="서비스 이용약관"
      maximize
      showClose
      @close="isFullModal = false"
    >
      <template #navBar>
        <div class="custom-nav">이전으로</div>
      </template>
      <div class="terms-content">
        <p>매우 긴 약관 내용...</p>
        <MsfButton size="small" @click="isInnerModal = true">팝업또연다</MsfButton>
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
          알럿
        </MsfButton>
      </div>
      <template #footer>
        <MsfButton variant="primary" block @click="isFullModal = false">동의하고 닫기</MsfButton>
      </template>
      <MsfDialog
        :isOpen="isInnerModal"
        title="이것은 내부팝업"
        showClose
        size="small"
        @close="isInnerModal = false"
      >
        <template #navBar>
          <div class="custom-nav">이전으로</div>
        </template>
        <p>이것은 또 다른 팝업</p>
        <template #footer>
          <MsfButton variant="primary" block @click="isInnerModal = false">동의하고 닫기</MsfButton>
        </template>
      </MsfDialog>
    </MsfDialog>
    <!-- Dialog 안에 Dialog -->
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { RouterLink } from 'vue-router'

// Dialog 직접 열기
import DialogSample from '@/views/guide/components/DialogSample.vue'
const isDialogSampleOpen = ref(false)

const name = ref('emailAddress@naver.com')
const address = ref('이것은 ReadOnly')
const address2 = ref('이것은 Disabled')

const handleButtonClick = () => {
  alert('버튼이 클릭되었습니다!')
}

const openPostPopup = () => {
  console.log('주소 검색 팝업을 엽니다!')
}

const email = ref('')
const password = ref('')
const password2 = ref('')
const emailError = ref('')

// 유효성 검사 예시
if (!email.value.includes('@')) {
  emailError.value = '올바른 이메일 형식이 아닙니다.'
}

// 시간
const timeValue = ref()
const timeWithSecondsValue = ref()

// 체크박스 returnData 사용예시
const isReturnData = ref('N')

// 체크박스
const isAgreed = ref(false)
const marketing = ref(true)
const mustCheck = ref(false)
// 체크박스그룹
// 초기값으로 '코딩'이 체크된 상태 (배열)
const selectedInterests = ref(['coding'])
const errorMsg = ref('')

// 라디오
const deliveryMethod = ref('standard')
const deliveryError = ref('')
// 라디오그룹
const deliveryMethod2 = ref('option4')
// const deliveryError2 = ref('에러메시지')

// select
const selectedCity = ref('') // 선택값 (value)만 담깁니다.

// alert(개발에서 정의하신 유틸로 사용)
import { showAlert, showConfirm } from '@/libs/utils/comp.utils'
// Alert 샘플 함수
const handleSampleAlert = () => {
  console.log('처리가 완료되었습니다.')
}
/** 1. 단순 메시지만 띄울 때 */
const handleShowAlert = () => {
  showAlert('[표시할 메세지 (필수)]', handleSampleAlert, '[표시할 하위 메세지]')
}
/** 2. 제목과 확인/취소가 필요한 경우 */
const handleConfirmAlert = () => {
  showConfirm(
    '[표시할 메세지 (필수)]\n줄바꿈입니다.',
    handleSampleAlert,
    '[표시할 하위 메세지]<br/>줄바꿈입니다.',
    handleSampleAlert,
  )
}

// 로딩관련
const isLoading = ref(false)
const handleLoadingTest = () => {
  isLoading.value = true

  // 실제 API 호출 대신 5초 뒤에 로딩을 종료하는 예시
  setTimeout(() => {
    isLoading.value = false
  }, 5000)
}

//  다이알로그
const isModalOpen = ref(false)
const isFullModal = ref(false)
const isInnerModal = ref(false) // 팝업내부 팝업

// 모달이 열릴 때 실행될 함수 선언
const handleModalOpen = () => {
  console.log('modal 열림! 이벤트 선언하세요!')
}
// 모달이 닫힐 때 실행될 함수 선언
const handleModalClose = () => {
  isModalOpen.value = false
  console.log('modal 닫힘! 이벤트 선언하세요!')
}

// 스택 샘플스타일 설정 (예시용)
const stackSampleStyle =
  'background:#efefef; height:100px; display:flex; align-items:center; justify-content:center;'

// vue-datepicker
const datePickerValue = ref()
const rangeDatePickerValue = ref({ start: '', end: '' })

// Chip
const selectedFruit = ref('today')
const selectedFruit2 = ref('apple')

// 단일 선택
const fruitData = [
  // { value: 'all', label: '전체' },
  { value: 'selected1', label: '선택1' },
  { value: 'selected2', label: '선택2' },
  { value: 'selected3', label: '선택3' },
  // { value: 'selected3', label: '선택3', disabled: true }, // 비활성화 예시
  { value: 'selected4', label: '선택4', disabled: true },
]
// 다중 선택 + 전체
const fruitData2 = [
  { value: 'all', label: '전체' },
  { value: 'apple', label: '사과' },
  { value: 'banana', label: '바나나' },
  { value: 'grape', label: '포도' },
  { value: 'orange', label: '오렌지' },
]
// 선택값 변경 시 콘솔 찍기
const handleSelectionChange = (val) => {
  console.log('부모에서 받은 선택값:', val)
}

// 데이터 테이블 + 페이징처리
const datas = ref([
  { make: 'Tesla', model: 'Model Y', price: 64950, electric: true },
  { make: 'Ford', model: 'F-Series', price: 33850, electric: false },
  { make: 'Toyota', model: 'Corolla', price: 29600, electric: false },
  { make: 'Tesla', model: 'Model Y', price: 64950, electric: true },
  { make: 'Ford', model: 'F-Series', price: 33850, electric: false },
  { make: 'Toyota', model: 'Corolla', price: 29600, electric: false },
  { make: 'Tesla', model: 'Model Y', price: 64950, electric: true },
  { make: 'Ford', model: 'F-Series', price: 33850, electric: false },
  { make: 'Toyota', model: 'Corolla', price: 29600, electric: false },
  { make: 'Tesla', model: 'Model Y', price: 64950, electric: true },
  { make: 'Ford', model: 'F-Series', price: 33850, electric: false },
  { make: 'Toyota', model: 'Corolla', price: 29600, electric: false },
])
const colDefs = ref([
  { field: 'make', headerClass: 'ag-left-header' }, //왼쪽정렬 원하신다면
  { field: 'model', headerClass: 'ag-right-header' }, //오른쪽정렬 원하신다면
  { field: 'price' },
  { field: 'electric' },
])
const selectedRow = ref()
const onSelected = (data) => {
  selectedRow.value = data
}
const selectedRowMulti = ref([])
const onSelectedMulti = (data) => {
  selectedRowMulti.value = data
}
const colDefsPaging = ref([
  { field: 'id' },
  { field: 'title' },
  { field: 'category' },
  { field: 'brand' },
  { field: 'price' },
])
const searchValuePaging = ref('')
const paramsPaging = ref({})
const selectedRowPaging = ref([])
const onClickSearchPaging = () => {
  paramsPaging.value = { q: searchValuePaging.value }
}
const onSelectedPaging = (data) => {
  selectedRowPaging.value = data
}
const onMovePage = (data) => {
  page.value = data
}
const selectedRowEmpty = ref()

const page = ref(3)
const total = ref(1000)
const rows = ref(10)

// input 항목
const numberValue = ref()
const birth6Value = ref()
const birth8Value = ref()

// 에디터
const webEditorContent = ref('')

// 차트
const chartData = ref([
  { nm: '접수처리완료', cnt: 60 },
  { nm: '신청중', cnt: 30 },
  { nm: '이미지전송완료', cnt: 10 },
])
const chartData2 = ref([
  { nm: '신규/변경', cnt: 60 },
  { nm: '서비스변경', cnt: 30 },
  { nm: '명의변경', cnt: 10 },
  { nm: '서비스해지', cnt: 10 },
])

// 약관 동의
const handleNext = () => alert('다음 단계로 이동합니다.')

// 약관 동의관련
const agreementRef = ref(null) // AgreementGroup을 참조할 변수
// 자식 컴포넌트가 expose한 isReady 값을 실시간으로 읽어옵니다.
const isReady = computed(() => agreementRef.value?.isReady || false)

// 플래그
const myFlags = [
  { label: '플래그1', color: 'gray' },
  { label: '플래그2', color: 'gray' },
  { label: '플래그3', color: 'gray', className: 'custom-bold' },
]

// MsfTable 테이블의 경우
const list = ref([
  { title: '111', detail: '111', memo: '' },
  { title: '222', detail: '222', memo: '222' },
])

// MsfTableList 테이블의 경우

// 테이블 테스트
const selectedId = ref(null)

// 컬럼 정의: 한 번만 정의해서 양쪽 테이블에 재사용 가능
const tableColumns = [
  { label: '번호', key: 'id', width: '100px' },
  { label: '제목', key: 'title' },
  { label: '등록일', key: 'date', width: '120px' },
]

// 원본 데이터 (슬라이싱 하지 않은 전체 데이터)
const tableData = ref(
  Array.from({ length: 20 }, (_, i) => ({
    id: i + 1,
    title: `리스트형 데이터의 제목 ${i + 1}입니다.`,
    date: '2026-03-20',
  })),
)

const onRowClick = (item) => {
  selectedId.value = item.id
}
const selectedItem = computed(() => {
  return tableData.value.find((item) => item.id === selectedId.value) || null
})

// 삭제 핸들러 예시
const handleDelete = (item) => {
  if (confirm(`${item.id}번 항목을 삭제하시겠습니까?`)) {
    tableData.value = tableData.value.filter((d) => d.id !== item.id)
  }
}
</script>

<style lang="scss" scoped>
.guide-wrap {
  padding: rem(24px);
  .guide-list {
    @include flex() {
      gap: rem(24px);
    }
    font-size: 16px;
    font-weight: bold;
  }
  .guide-item {
    margin-block: rem(42px);
  }
}
</style>
