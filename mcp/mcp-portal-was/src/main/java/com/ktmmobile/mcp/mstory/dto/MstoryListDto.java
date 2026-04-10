package com.ktmmobile.mcp.mstory.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("mstoryListDto")
public class MstoryListDto {
		
		private MstoryDto mstoryDto;
		private List<MstoryAttDto> mstoryAttDtoList;
		private List<MstoryDto> mstoryDetailList;
		
		
		public MstoryDto getMstoryDto() {
			return mstoryDto;
		}
		public void setMstoryDto(MstoryDto mstoryDto) {
			this.mstoryDto = mstoryDto;
		}
		public List<MstoryAttDto> getMstoryAttDtoList() {
			return mstoryAttDtoList;
		}
		public void setMstoryAttDtoList(List<MstoryAttDto> mstoryAttDtoList) {
			this.mstoryAttDtoList = mstoryAttDtoList;
		}
		public List<MstoryDto> getMstoryDetailList() {
			return mstoryDetailList;
		}
		public void setMstoryDetailList(List<MstoryDto> mstoryDetailList) {
			this.mstoryDetailList = mstoryDetailList;
		}
		
		

}
