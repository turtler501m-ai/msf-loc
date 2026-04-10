
var validator=function(){
    varmultiValidateId = '';

    var _lenCommon = function(selector,len,that,_emptyCheck,isNumber,msgObj,conditionFn)
    {
        var v = validator.getValue(selector);
        var emptyCheck = false
           ,result     = false;
        var isValNonEmpty = _isNonEmpty(selector,that,v);

        emptyCheck = (_emptyCheck==undefined || _emptyCheck == false)?true:isValNonEmpty;
        if(emptyCheck)
        {
            var b = false;

            if(isNumber)
            {
                b = validator.types.isNumberN.validate(selector,that,v)
            }
            else
            {
                b = true;
            }

            if(!isValNonEmpty && !_emptyCheck)
            {
                return true;
            }

            if(b)
            {
                that.instructions = msgObj[selector];
                result = conditionFn(v);
                return result;
            }
            else
            {
                return false;
            }
        }

        return false;
    }

    var _lenFix = function(selector,len,that,_emptyCheck,isNumber)
    {
        return _lenCommon(selector,len,that,_emptyCheck,isNumber,VALIDATE_FIX_MSG,function(v){
            return v.length == len;
        });
    }

    var _lenBetterFix = function(selector,len,that,_emptyCheck,isNumber)
    {
        return _lenCommon(selector,len,that,_emptyCheck,isNumber,VALIDATE_FIX_MSG,function(v){
            return  (v.length >= len || v.length==0);
        });
    }

    var _lenBetterMore = function(selector,len,that,_emptyCheck,isNumber)
    {
        return _lenCommon(selector,len,that,_emptyCheck,isNumber,VALIDATE_FIX_MSG,function(v){
            if  (v.length <= len){
                return true;
            } else {
                var $obj=$('#'+selector);
                var lsStr=$obj.val();
                $obj.val(lsStr.substr(0,len));
                return false;
            }

        });
    }

    var _fngNoFix = function(fgnno, ids,that)
    {
        if (fgnno.length != 13) {
            that.instructions = VALIDATE_COMPARE_MSG[ids[0]];
            validator.setValidateMultiId(ids[0]);
            return false;
        } else if (fgnno.substr(6, 1) != 5 && fgnno.substr(6, 1) != 6 && fgnno.substr(6, 1) != 7 && fgnno.substr(6, 1) != 8) {
            that.instructions = VALIDATE_COMPARE_MSG[ids[0]];
            validator.setValidateMultiId(ids[0]);
            return false;
        }

        return true;

        //VOC 발생으로 제외 처리
        if (Number(fgnno.substr(7, 2)) % 2 != 0) {
            that.instructions = VALIDATE_COMPARE_MSG[ids[0]];
            validator.setValidateMultiId(ids[0]);
            return false;
        }
        var sum = 0;

        for (var i = 0; i < 12; i++) {
            sum += Number(fgnno.substr(i, 1)) * ((i % 8) + 2);
        }
        if ((((11 - (sum % 11)) % 10 + 2) % 10) != Number(fgnno.substr(12, 1))) {
            that.instructions = VALIDATE_COMPARE_MSG[ids[0]];
            validator.setValidateMultiId(ids[0]);
            return false;
        }
        return true;
    }

    var _jiminFix = function(regno1, regon2, ids,that)
    {
        //2024-01-07 박민건 주민번호 유효성 검증 로직 제외
        return true;

        if( regno1 == "111111" && regon2 == "1111118"){
            that.instructions = VALIDATE_COMPARE_MSG[ids[0]];
            validator.setValidateMultiId(ids[0]);
            return false;
        }

        var chk = 0;

        for (var i = 0; i <=5 ; i++){
            chk = chk + ((i%8+2) * parseInt(regno1.substring(i,i+1)))
        }

        for (var i = 6; i <=11 ; i++){
            chk = chk + ((i%8+2) * parseInt(regon2.substring(i-6,i-5)))
        }

        chk = 11 - (chk %11);
        chk = chk % 10;

        if (chk != regon2.substring(6,7))
        {
            that.instructions = VALIDATE_COMPARE_MSG[ids[0]];
            validator.setValidateMultiId(ids[0]);
            return false;
        }

        return true;
    }

    var _jiminFixAll = function(selector,that)
    {
        var regno = validator.getValue(selector);
        if( regno == "1111111111118" ){
            that.instructions = VALIDATE_COMPARE_MSG[selector];
            validator.setValidateMultiId(selector);
            return false;
        }

        if (regno.length != 13) {
            that.instructions = VALIDATE_FIX_MSG[selector];
            validator.setValidateMultiId(selector);
            return false;
        }

        var regno1 = regno.substring(0,6);
        var regno2 = regno.substring(6);

        var chk = 0;

        for (var i = 0; i <=5 ; i++){
            chk = chk + ((i%8+2) * parseInt(regno1.substring(i,i+1)))
        }

        for (var i = 6; i <=11 ; i++){
            chk = chk + ((i%8+2) * parseInt(regno2.substring(i-6,i-5)))
        }

        chk = 11 - (chk %11);
        chk = chk % 10;

        if (chk != regno2.substring(6,7))
        {
            that.instructions = VALIDATE_COMPARE_MSG[selector];
            validator.setValidateMultiId(selector);
            return false;
        }

        return true;
    }


    var _maxByte = function(id,len,that,emptyCheck)
    {
        emptyCheck = (emptyCheck==undefined || emptyCheck == false)?true:_isNonEmpty(id,that);

        if(emptyCheck)
        {
            var $obj=$('#'+id);
            if($obj.length == 0) return false;

            var lsStr=$obj.val();               //이벤트가 일어난 컨트롤의 value값
            var lsStrLen=lsStr.length;          //전체길이
            var maxLen= len;                    //제한할 글자수 크기
            var liByte=0;                       //한글일 경우는 2 그밖에는 1을 더함
            var liLen=0;                        //substring하기위해사용
            var lsOneChar="";                   //한글자씩 검사한다.
            var lsStr2="";                      //글자수를 초과하면 제한할 수 글자전까지만 보여준다.

            for(var i=0;i<lsStrLen;i++)
            {
                //한글자추출
                lsOneChar=lsStr.charAt(i);
                //한글이면 2를 더한다.
                if(escape(lsOneChar).length>4)
                {
                    liByte=liByte+3;
                }
                else if((lsOneChar =='\r' && lsStr.charAt(i+1) =='\n') || lsOneChar=='\n')
                {
                    liByte=liByte+3;
                }
                else
                {
                    liByte++;
                }

                //전체 크기가 maxLen을 넘지않으면
                if(liByte<=maxLen)
                {
                    liLen=i+1;
                }

            }

            //전체길이를 초과하면
            if(liByte>maxLen)
            {
                that.instructions = VALIDATE_MAX_BYTES_MSG[id];
                $obj.val(lsStr.substr(0,liLen));

                return false;
            }

            return true;
        }

        return false;
    }

    var _minLength = function(id,len,that,emptyCheck)
    {
        emptyCheck = (emptyCheck==undefined || emptyCheck == false)?true:_isNonEmpty(id,that);

        if(emptyCheck)
        {
            var $obj=$('#'+id);
            if($obj.length == 0) return false;

            var lsStr=$obj.val();               //이벤트가 일어난 컨트롤의 value값
            var lsStrLen=lsStr.length;          //전체길이
            var minLen= len;                    //최소한 글자수 크기

            //전체길이를 미만이면
            if(lsStrLen<minLen)
            {
                that.instructions = VALIDATE_MAX_BYTES_MSG[id];
                return false;
            }

            return true;
        }

        return false;
    }

    var _isLessThan =  function(id,maxValue,that,emptyCheck)
    {
        emptyCheck = (emptyCheck==undefined || emptyCheck == false)?true:_isNonEmpty(id,that);
        if(emptyCheck)
        {
            var $obj=$('#'+id);
            if($obj.length == 0) return false;

            var v = $.trim($obj.val());
            if(isNaN(v)) return false;

            if(maxValue<v)
            {
                that.instructions = VALIDATE_COMPARE_MSG[id];
                return false;
            }

            return true;
        }

        return false;
    }



    var _isNonEmpty = function(selector,that,v)
    {
        return validator.types.isNonEmpty.validate(selector,that,v);
    }

    var _isMail = function (selector,that,v)
    {
        that.instructions = VALIDATE_COMPARE_MSG[selector];
       // var strReg = /^([\w\.-]+)@([a-z\d\.-]+)\.([a-z\.]{2,6})$/;
        var strReg = /^[-!#$%&\'*+\\./0-9=?A-Z^_`a-zㄱ-ㅎ|ㅏ-ㅣ|가-힣{|}~]+@[-!#$%&\'*+\\/0-9=?A-Z^_`a-zㄱ-ㅎ|ㅏ-ㅣ|가-힣{|}~]+\.[-!#$%&\'*+\\./0-9=?A-Z^_`a-zㄱ-ㅎ|ㅏ-ㅣ|가-힣{|}~]+$/;


     // 정규식 - 이메일 유효성 검사
        //var regEmail = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
        // 정규식 -전화번호 유효성 검사
        //var regPhone = /^((01[1|6|7|8|9])[1-9]+[0-9]{6,7})|(010[1-9][0-9]{7})$/;


       return strReg.test(v);
    }


    return {
        getValue:function(id){
            var v        = '';
            var $el      = $('#'+id);

            var isExistJqueryObject = function($j)
            {
                return $j.length>0;
            }

            if(isExistJqueryObject($el))
            {
                var domEl = $el[0];
                var nodeName = domEl.nodeName.toLowerCase();
                var inputType = '';

                if(nodeName == 'input')
                {
                    inputType = $el.attr('type').toLowerCase();

                    switch(inputType)
                    {
                    case 'radio'   :
                    case 'checkbox':
                        var name = $el.attr('name');
                        var groupVal = '';

                        if(name)
                        {
                            var $groups = $('[name="'+name+'"]');
                            if(isExistJqueryObject($groups))
                            {
                                $groups.each(function(){
                                    if(this.checked)
                                    {
                                        if($(this).attr('validityyn'))
                                        {
                                            groupVal = $.trim($(this).val());
                                        }
                                        else
                                        {
                                            if($(this).attr('invalid') == undefined)
                                                groupVal = $.trim($(this).val());
                                            groupVal = 'true';
                                        }
                                    }
                                });
                            }
                        }
                        else
                        {
                            if($el.is(':checked'))
                            {
                                groupVal = $el.val();
                            }
                        }

                        v = groupVal;
                        break;
                    case 'text'     :
                    case 'hidden'   :
                    case 'textarea' :
                    case 'tel'      :
                    case 'number'   :
                    case 'password' :
                    case 'search'   :
                    case 'email'   :
                    case 'file'    :
                    case 'date'    :
                        v = $.trim($el.val());
                        break;

                    }
                }
                else if(nodeName == 'textarea')
                {
                    v = $.trim($el.val());
                }
                else if(nodeName == 'select')
                {
                    v = $('#'+id+' option:selected').val();
                }
                else if(nodeName == 'img')
                {
                    var isSignImg = ($el.hasClass('signSimg'));
                    var src = '';
                    if(isSignImg)
                    {
                        src = $el.attr('src');
                        if(src.indexOf('data:image')<0)
                        {
                            v = '';
                        }
                        else
                        {
                            v = 'sign';
                        }
                    }
                }
            }

            return v;
        }
        ,types:{
            isNonEmpty:{
                 validate:function(selector,that,val){
                     var v = (val != undefined)?val:validator.getValue(selector);
                     v = (v == undefined) ? '':v;
                     that = that || this;
                     that.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                     return v !='';
                 }
            },
            isNonEmptyAndCheckY:{
                validate:function(selector,that,val){
                    var v = (val != undefined)?val:validator.getValue(selector);
                    v = (v == undefined) ? '':v;
                    that = that || this;
                    that.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                    return v !='' && v === 'Y';
                }
            },
            isNonEmpty2:{
                validate:function(selector,that,val){
                    var placeholder = $("#"+selector).attr("placeholder");
                    if (placeholder != undefined) {
                        if (placeholder == validator.getValue(selector)) {
                            that = that || this;
                            that.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                            return false;
                        }
                    }

                    var v = (val != undefined)?val:validator.getValue(selector);
                    v = (v == undefined) ? '':v;
                    that = that || this;
                    that.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                    return v !='';
                }
            }
            ,isCert:{
                validate:function(selector,that,val){
                    var v = (val != undefined)?val:validator.getValue(selector);
                    that = that || this;
                    that.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                    return v =='Y';
                }
            }
            //empty 허용,입력되면 숫자만
            ,isNumberN:{
                validate:function(selector,that,val){
                    var v = (val != undefined)?val:validator.getValue(selector);
                    if(v == '') return true;
                    that = that || this;
                    that.instructions = VALIDATE_NUMBER_MSG[selector];
                    var strReg = /^[0-9]+$/;
                    return strReg.test(v);
                }
            }
            ,isNumber:{
                validate:function(selector,that){
                    var v = validator.getValue(selector);
                    that = that || this;
                    var _isNotEmpty = validator.types.isNonEmpty.validate(selector,that,v);
                    if(!_isNotEmpty) return false;

                    return validator.types.isNumberN.validate(selector,that,v);
                }
            }
            ,isNumFix2:{
                validate:function(selector){
                    return _lenFix(selector,2,this,true,true);
                }
            }
            ,isNumFix3:{
                validate:function(selector){
                    return _lenFix(selector,3,this,true,true);
                }
            }
            ,isNumFix4:{
                validate:function(selector){
                    return _lenFix(selector,4,this,true,true);
                }
            }
            ,isNumFix5:{
                validate:function(selector){
                    return _lenFix(selector,5,this,true,true);
                }
            }
            ,isNumFix6:{
                validate:function(selector){
                    return _lenFix(selector,6,this,true,true);
                }
            }
            ,isNumFix7:{
                validate:function(selector){
                    return _lenFix(selector,7,this,true,true);
                }
            }
            ,isNumFix8:{
                validate:function(selector){
                    return _lenFix(selector,8,this,true,true);
                }
            }
            ,isNumFix10:{
                validate:function(selector){
                    return _lenFix(selector,10,this,true,true);
                }
            }
            ,isNumFix15:{
                validate:function(selector){
                    return _lenFix(selector,15,this,true,true);
                }
            }
            ,isNumFix19:{
                validate:function(selector){
                    return _lenFix(selector,19,this,true,true);
                }
            }
            ,isNumFix32:{
                validate:function(selector){
                    return _lenFix(selector,32,this,true,true);
                }
            }
            ,isNumBetterFixN2:{
                validate:function(selector){
                    return _lenBetterFix(selector,2,this,true,true);
                }
            }
            ,isNumBetterFixN3:{
                validate:function(selector){
                    return _lenBetterFix(selector,3,this,true,true);
                }
            }
            ,isNumBetterFixN4:{
                validate:function(selector){
                    return _lenBetterFix(selector,4,this,true,true);
                }
            }
            ,isNumBetterFixN8:{
                validate:function(selector){
                    return _lenBetterFix(selector,8,this,true,true);
                }
            }
            ,isNumBetterFixN9:{
                validate:function(selector){
                    return _lenBetterFix(selector,9,this,true,true);
                }
            }
            ,isNumBetterFixN10:{
                validate:function(selector){
                    return _lenBetterFix(selector,10,this,true,true);
                }
            }
            ,isNumBetterFixN11:{
                validate:function(selector){
                    return _lenBetterFix(selector,11,this,true,true);
                }
            }
            ,isNumBetterFixN14:{
                validate:function(selector){
                    return _lenBetterFix(selector,14,this,true,true);
                }
            }
            //empty 허용,입력되면 숫자영문만
            ,isNumberEngN:{
                validate:function(selector,val){
                    var v = (val != undefined)?val:validator.getValue(selector);
                    if(v == '') return true;

                    this.instructions = VALIDATE_NUMBER_ENG_MSG[selector];
                    var strReg = /^[A-Za-z0-9]+$/;
                    return strReg.test(v);
                }
            }
            ,isNumberEng:{
                validate:function(selector){
                    var v = validator.getValue(selector);
                    if(_isNonEmpty(selector,this,v)) {
                        this.instructions = VALIDATE_NUMBER_ENG_MSG[selector];
                        var strReg = /^[A-Za-z0-9]+$/;
                        return strReg.test(v);
                    }
                    return false;
                }
            }
            ,isSpecialKeyExcept:{
                validate:function(selector){
                    var v = validator.getValue(selector);
                    if(_isNonEmpty(selector,this,v)) {
                        this.instructions = VALIDATE_NUMBER_ENG_MSG[selector];
                        var strReg = /^[A-Za-z0-9ㄱ-ㅎㅏ-ㅣ가-힣]+$/;
                        return strReg.test(v);
                    }
                    return false;
                }
            }
            ,isTel:{
                validate:function(selector){
                    var v = validator.getValue(selector);
                    if(_isNonEmpty(selector,this,v)) {
                        this.instructions = VALIDATE_COMPARE_MSG[selector];
                        var strReg = /(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/;
                        return strReg.test(v);
                    }
                    return false;
                }
            }
            ,isPhone:{
                validate:function(selector){
                    var v = validator.getValue(selector);
                    if(_isNonEmpty(selector,this,v)) {
                        this.instructions = VALIDATE_COMPARE_MSG[selector];
                        var strReg = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
                        return strReg.test(v);
                    }
                    return false;
                }
            }
            ,isEngN:{
                validate:function(selector,val){
                    var v = (val != undefined)?val:validator.getValue(selector);
                    if(v == '') return true;

                    this.instructions = VALIDATE_ENG_MSG[selector];
                    var strReg = /^[A-Za-z]+$/;
                    return strReg.test(v);
                }
            }
            ,isEng:{
                validate:function(selector){
                    var v = validator.getValue(selector);
                    if(_isNonEmpty(selector,v))
                        return validator.types.isEngN.validate(selector,v);
                    return false;
                }
            }
            ,isImg:{
                validate:function(selector){
                    var fileNm = validator.getValue(selector);
                    if(fileNm == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                        return false;
                    }

                    if(_isNonEmpty(selector,this,fileNm)) {

                        var ext = fileNm.slice(fileNm.lastIndexOf(".") + 1).toLowerCase();
                        if (!(ext == "gif" || ext == "jpg" || ext == "png" || ext =="jpeg")) {
                            this.instructions = VALIDATE_COMPARE_MSG[selector];
                            return false;
                        }
                    }

                    return true;
                }
            }
            ,isNonMail:{
                validate:function(selector){
                    var v = validator.getValue(selector);
                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                        return false;
                    }

                    return _isMail(selector,this,v);
                }
            }
            ,isNonMailMulti:{
                validate:function(id){
                    var ids        = id.split('&');
                    var idsLen     = ids.length;

                    if(idsLen != 2)
                    {
                        this.instructions = 'ID를 2개 입력해 주세요.';
                        return false;
                    }

                    //v1 = $('#'+ids[0]).val();
                    //v2 = $('#'+ids[1]).val();

                    var v = validator.getValue(ids[0]);
                    validator.setValidateMultiId(ids[0]);

                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    var v2 = validator.getValue(ids[1]);
                    if(v2 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[1]];
                        validator.setValidateMultiId(ids[1]);
                        return false;
                    }

                    v = v+"@"+v2 ;
                    return _isMail(ids[0],this,v);
                }
            }
            ,isJiminAndFngNo:{
                validate:function(id){
                    var ids        = id.split('&');
                    var idsLen     = ids.length;

                    if(idsLen != 2)
                    {
                        this.instructions = 'ID를 2개 입력해 주세요.';
                        return false;
                    }

                    //v1 = $('#'+ids[0]).val();
                    //v2 = $('#'+ids[1]).val();

                    var v = validator.getValue(ids[0]);
                    validator.setValidateMultiId(ids[0]);

                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    var v2 = validator.getValue(ids[1]);
                    if(v2 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[1]];
                        validator.setValidateMultiId(ids[1]);
                        return false;
                    }

                    if (!_jiminFix(v,v2,ids,this)) {
                        var fgnno = v + v2 ;
                        return _fngNoFix(fgnno,ids,this);
                    } else {
                        return true;
                    }


                }
            }
            ,isJimin:{
                validate:function(id){
                    var ids        = id.split('&');
                    var idsLen     = ids.length;

                    if(idsLen != 2)
                    {
                        this.instructions = 'ID를 2개 입력해 주세요.';
                        return false;
                    }

                    //v1 = $('#'+ids[0]).val();
                    //v2 = $('#'+ids[1]).val();

                    var v = validator.getValue(ids[0]);
                    validator.setValidateMultiId(ids[0]);

                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    var v2 = validator.getValue(ids[1]);
                    if(v2 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[1]];
                        validator.setValidateMultiId(ids[1]);
                        return false;
                    }

                    return _jiminFix(v,v2,ids,this);

                }
            }
            ,isJiminNum:{
                validate:function(selector){
                    var v = validator.getValue(selector);
                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                        return false;
                    }
                    return _jiminFixAll(selector,this);
                }
            }
            ,isFngno:{
                //재외국인 번호 체크
                validate:function(id){
                    var ids        = id.split('&');
                    var idsLen     = ids.length;

                    if(idsLen != 2)
                    {
                        this.instructions = 'ID를 2개 입력해 주세요.';
                        return false;
                    }

                    var v = validator.getValue(ids[0]);
                    validator.setValidateMultiId(ids[0]);

                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    var v2 = validator.getValue(ids[1]);
                    if(v2 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[1]];
                        validator.setValidateMultiId(ids[1]);
                        return false;
                    }

                    var fgnno = v + v2 ;

                    if (fgnno.length != 13) {
                        this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    } else if (fgnno.substr(6, 1) != 5 && fgnno.substr(6, 1) != 6 && fgnno.substr(6, 1) != 7 && fgnno.substr(6, 1) != 8) {
                        this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    return true;

                    //VOC 발생으로 제외 처리
                    if (Number(fgnno.substr(7, 2)) % 2 != 0) {
                        this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }
                    var sum = 0;

                    for (var i = 0; i < 12; i++) {
                        sum += Number(fgnno.substr(i, 1)) * ((i % 8) + 2);
                    }
                    if ((((11 - (sum % 11)) % 10 + 2) % 10) != Number(fgnno.substr(12, 1))) {
                        this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }
                    return true;
                  //VOC 발생으로 제외 처리

                }
            },isFngnoNum:{
                //재외국인 번호 체크
                validate:function(selector){
                    var v = validator.getValue(selector);
                    validator.setValidateMultiId(selector);

                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                        validator.setValidateMultiId(selector);
                        return false;
                    }

                    var fgnno = v ;

                    if (fgnno.length != 13) {
                        this.instructions = VALIDATE_COMPARE_MSG[selector];
                        validator.setValidateMultiId(selector);
                        return false;
                    } else if (fgnno.substr(6, 1) != 5 && fgnno.substr(6, 1) != 6 && fgnno.substr(6, 1) != 7 && fgnno.substr(6, 1) != 8) {
                        this.instructions = VALIDATE_COMPARE_MSG[selector];
                        validator.setValidateMultiId(selector);
                        return false;
                    }

                    return true;

                    //VOC 발생으로 제외 처리
                    if (Number(fgnno.substr(7, 2)) % 2 != 0) {
                        this.instructions = VALIDATE_COMPARE_MSG[selector];
                        validator.setValidateMultiId(selector);
                        return false;
                    }
                    var sum = 0;

                    for (var i = 0; i < 12; i++) {
                        sum += Number(fgnno.substr(i, 1)) * ((i % 8) + 2);
                    }
                    if ((((11 - (sum % 11)) % 10 + 2) % 10) != Number(fgnno.substr(12, 1))) {
                        this.instructions = VALIDATE_COMPARE_MSG[selector];
                        validator.setValidateMultiId(selector);
                        return false;
                    }
                    return true;
                  //VOC 발생으로 제외 처리

                }
            }

            ,isNonPassWord:{
                validate:function(selector){
                    var v = validator.getValue(selector);
                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                        return false;
                    }

                    //영문, 숫자 혼용해서 8~20자 이내
                    this.instructions = VALIDATE_FIX_MSG[selector];
                    var strReg =/^.*(?=.{8,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
                    return strReg.test(v);
                }
            }
            ,isNumFix4IsNotEqualsTriple:{
                validate:function(id){
                    var ids        = id.split('&');
                    var idsLen        = ids.length;
                    var v1, v2, v3;

                    if(idsLen != 3)
                    {
                        this.instructions = 'ID를 3개 입력해 주세요.';
                        return false;
                    }

                    for(var i=0;i<3;i++)
                    {
                        validator.setValidateMultiId(ids[i]);
                        if(!_lenFix(ids[i],4,this,true,true) ) {
                            return false;
                        }
                    }

                    v1 = $('#'+ids[0]).val();
                    v2 = $('#'+ids[1]).val();
                    v3 = $('#'+ids[2]).val();

                    validator.setValidateMultiId(ids[0]);

                    if($.trim(v1)== $.trim(v2) || $.trim(v1)== $.trim(v3) || $.trim(v2)== $.trim(v3)) {
                        this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                        return false;
                    } else {
                        return true;
                    }

                }
            }
            ,isIsNotEqualsTriple:{
                validate:function(id){
                    var ids        = id.split('&');
                    var idsLen        = ids.length;
                    var v1, v2, v3;

                    if(idsLen != 3)
                    {
                        this.instructions = 'ID를 3개 입력해 주세요.';
                        return false;
                    }

                    v1 = $('#'+ids[0]).val();
                    v2 = $('#'+ids[1]).val();
                    v3 = $('#'+ids[2]).val();

                    if(v1 == '') {
                        validator.setValidateMultiId(ids[0]);
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[0]];
                        return false;
                    }

                    if(v2 == '') {
                        validator.setValidateMultiId(ids[1]);
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[1]];
                        return false;
                    }

                    if(v3 == '') {
                        validator.setValidateMultiId(ids[2]);
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[2]];
                        return false;
                    }

                    validator.setValidateMultiId(ids[0]);

                    if($.trim(v1)== $.trim(v2) || $.trim(v1)== $.trim(v3) || $.trim(v2)== $.trim(v3)) {
                        this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                        return false;
                    } else {
                        return true;
                    }

                }
            }
            ,isCheckCardNumber:{
                validate:function(id){
                    var ids     = id.split('&');
                    var idsLen  = ids.length;

                    if(idsLen != 4)
                    {
                        this.instructions = 'ID를 4개 입력해 주세요.';
                        return false;
                    }

                    for(var i=0;i<3;i++)
                    {
                        validator.setValidateMultiId(ids[i]);
                        if(!_lenFix(ids[i],4,this,true,true) ) {
                            return false;
                        }
                    }

                    validator.setValidateMultiId(ids[3]);
                    if(!_lenBetterFix(ids[3],2,this,true,true) ) {
                        return false;
                    }

                    validator.setValidateMultiId(ids[0]);
                    var cardNumber = $('#'+ids[0]).val() + $('#'+ids[1]).val() + $('#'+ids[2]).val() + $('#'+ids[3]).val() ;

                    var digits = cardNumber.split('');
                    for (var i = 0; i < digits.length; i++) {
                        digits[i] = parseInt(digits[i], 10);
                    }

                    //그 배열을 대상으로 룬 알고리즘 실행
                    var sum = 0;
                    var alt = false;
                    for (var i = digits.length - 1; i >= 0 ; i-- ) {
                        if (alt) {
                            digits[i] *= 2;
                            if(digits[i] > 9) {
                                digits[i] -= 9;
                            }
                        }
                        sum += digits[i];
                        alt = !alt;
                    }
                    //결국 카드 번호는 잘못 되었음이 밝혀짐
                    if(sum % 10 == 0) {
                        return true;
                    } else {
                        this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                        return false;
                    }
                }
            }
            ,isCheckCardNumberAll:{
                validate:function(selector){
                    var cardNumber = validator.getValue(selector);

                    if(cardNumber == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                        return false;
                    }

                    if(!_lenBetterFix(selector,15,this,true,true) ) {
                        return false;
                    }

                    var digits = cardNumber.split('');
                    for (var i = 0; i < digits.length; i++) {
                        digits[i] = parseInt(digits[i], 10);
                    }

                    //그 배열을 대상으로 룬 알고리즘 실행
                    var sum = 0;
                    var alt = false;
                    for (var i = digits.length - 1; i >= 0 ; i-- ) {
                        if (alt) {
                            digits[i] *= 2;
                            if(digits[i] > 9) {
                                digits[i] -= 9;
                            }
                        }
                        sum += digits[i];
                        alt = !alt;
                    }
                    //결국 카드 번호는 잘못 되었음이 밝혀짐
                    if(sum % 10 == 0) {
                        return true;
                    } else {
                        this.instructions = VALIDATE_COMPARE_MSG[selector];
                        return false;
                    }
                }
            }
            ,isEquals : {
                validate : function(id){
                    var ids        = id.split('&');
                    var idsLen     = ids.length;
                    var v1,v2;

                    if(idsLen != 2)
                    {
                        this.instructions = 'ID를 2개 입력해 주세요.';
                        return false;
                    }

                    v1 = $('#'+ids[0]).val();
                    v2 = $('#'+ids[1]).val();

                    if($.trim(v1) != $.trim(v2))
                    {
                        this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                        validator.setValidateMultiId(($.trim(v1) == '')?ids[0]:ids[1]);
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            ,isNotEquals : {
                validate : function(id){
                    var ids        = id.split('&');
                    var idsLen     = ids.length;
                    var v1,v2;

                    if(idsLen != 2)
                    {
                        this.instructions = 'ID를 2개 입력해 주세요.';
                        return false;
                    }

                    v1 = $('#'+ids[0]).val();
                    v2 = $('#'+ids[1]).val();

                    if($.trim(v1) == $.trim(v2))
                    {
                        this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                        validator.setValidateMultiId(($.trim(v1) == '')?ids[0]:ids[1]);
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            ,isTeen:{
                validate : function(id){
                    //청소년 나이 체크
                    var ids        = id.split('&');
                    var idsLen     = ids.length;

                    if(idsLen != 2)
                    {
                        this.instructions = 'ID를 2개 입력해 주세요.';
                        return false;
                    }

                    var v = validator.getValue(ids[0]);
                    validator.setValidateMultiId(ids[0]);

                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    var v2 = validator.getValue(ids[1]);
                    if(v2 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[1]];
                        validator.setValidateMultiId(ids[1]);
                        return false;
                    }

                    //주민번호 체크
                    if(!_jiminFix($('#'+ids[0]).val(),$('#'+ids[1]).val(),ids,this)) {
                        return false;
                    }


                    var regNo = $('#'+ids[0]).val() + $('#'+ids[1]).val() ;
                    var temp = regNo.substring(6, 7);
                    var birthday  = "" ;

                    if (temp == "1" || temp == "2") {
                        birthday = "19"+regNo ;
                    } else {
                        birthday = "20"+regNo ;
                    }

                    var bday=parseInt(birthday.substring(6,8));
                    var bmo=(parseInt(birthday.substring(4,6))-1);
                    var byr=parseInt(birthday.substring(0,4));
                    var byr;
                    var age;
                    var now = new Date();
                    tday=now.getDate();
                    tmo=(now.getMonth());
                    tyr=(now.getFullYear());


                    if((tmo > bmo)||(tmo==bmo & tday>=bday)) {
                        age=byr
                    } else{
                        age=byr+1;
                    }

                    if( 19 > (tyr-age) ) {
                        return true;
                    } else {
                        validator.setValidateMultiId(ids[0]);
                        this.instructions = VALIDATE_COMPARE_MSG[ids[1]];
                        return false;
                    }

                }
            }
            ,isTeenNum:{
                validate : function(selector){
                    //청소년 나이 체크
                    var v = validator.getValue(selector);
                    validator.setValidateMultiId(selector);

                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                        return false;
                    }

                    //주민번호 체크
                    if(!_jiminFixAll(selector,this)) {
                        return false;
                    }

                    var regNo = v;
                    var temp = regNo.substring(6, 7);
                    var birthday  = "" ;

                    if (temp == "1" || temp == "2") {
                        birthday = "19"+regNo ;
                    } else {
                        birthday = "20"+regNo ;
                    }

                    var bday=parseInt(birthday.substring(6,8));
                    var bmo=(parseInt(birthday.substring(4,6))-1);
                    var byr=parseInt(birthday.substring(0,4));
                    var byr;
                    var age;
                    var now = new Date();
                    tday=now.getDate();
                    tmo=(now.getMonth());
                    tyr=(now.getFullYear());

                    if((tmo > bmo)||(tmo==bmo & tday>=bday)) {
                        age=byr
                    } else{
                        age=byr+1;
                    }

                    if( 19 > (tyr-age) ) {
                        return true;
                    } else {
                        validator.setValidateMultiId(selector);
                        this.instructions = VALIDATE_AUT_MSG[selector];
                        return false;
                    }

                }
            }
            ,isInsrAgeChk1:{
                validate : function(id){
                    //DB선택보험 나이체크 만15세 이상 100세 이하 (국민 키즈안심 외)
                    var ids        = id.split('&');
                    var idsLen     = ids.length;

                    if(idsLen != 2)
                    {
                        this.instructions = 'ID를 2개 입력해 주세요.';
                        return false;
                    }

                    var v = validator.getValue(ids[0]);
                    validator.setValidateMultiId(ids[0]);

                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    var v2 = validator.getValue(ids[1]);
                    if(v2 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[1]];
                        validator.setValidateMultiId(ids[1]);
                        return false;
                    }

                    //주민번호 체크
                    if(!_jiminFix($('#'+ids[0]).val(),$('#'+ids[1]).val(),ids,this)) {
                        return false;
                    }

                    var regNo = $('#'+ids[0]).val() + $('#'+ids[1]).val() ;
                    var temp = regNo.substring(6, 7);
                    var birthday  = "" ;

                    if (temp == "1" || temp == "2") {
                        birthday = "19"+regNo ;
                    } else {
                        birthday = "20"+regNo ;
                    }

                    var bday=parseInt(birthday.substring(6,8));
                    var bmo=(parseInt(birthday.substring(4,6))-1);
                    var byr=parseInt(birthday.substring(0,4));
                    var byr;
                    var age;
                    var now = new Date();
                    tday=now.getDate();
                    tmo=(now.getMonth());
                    tyr=(now.getFullYear());

                    if((tmo > bmo)||(tmo==bmo & tday>=bday)) {
                        age=byr
                    } else{
                        age=byr+1;
                    }

                    if( 15 > (tyr-age) || 100 < (tyr-age) ) {
                        validator.setValidateMultiId(ids[0]);
                        this.instructions = VALIDATE_NOT_KOR_MSG[ids[0]];
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            ,isInsrAgeChk2:{
                validate : function(id){
                    //DB선택보험 나이체크 100세 이하 가입가능 (국민 키즈안심)
                    var ids        = id.split('&');
                    var idsLen     = ids.length;

                    if(idsLen != 2)
                    {
                        this.instructions = 'ID를 2개 입력해 주세요.';
                        return false;
                    }

                    var v = validator.getValue(ids[0]);
                    validator.setValidateMultiId(ids[0]);

                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    var v2 = validator.getValue(ids[1]);
                    if(v2 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[1]];
                        validator.setValidateMultiId(ids[1]);
                        return false;
                    }

                    //주민번호 체크
                    if(!_jiminFix($('#'+ids[0]).val(),$('#'+ids[1]).val(),ids,this)) {
                        return false;
                    }

                    var regNo = $('#'+ids[0]).val() + $('#'+ids[1]).val() ;
                    var temp = regNo.substring(6, 7);
                    var birthday  = "" ;

                    if (temp == "1" || temp == "2") {
                        birthday = "19"+regNo ;
                    } else {
                        birthday = "20"+regNo ;
                    }

                    var bday=parseInt(birthday.substring(6,8));
                    var bmo=(parseInt(birthday.substring(4,6))-1);
                    var byr=parseInt(birthday.substring(0,4));
                    var byr;
                    var age;
                    var now = new Date();
                    tday=now.getDate();
                    tmo=(now.getMonth());
                    tyr=(now.getFullYear());

                    if((tmo > bmo)||(tmo==bmo & tday>=bday)) {
                        age=byr
                    } else{
                        age=byr+1;
                    }

                    if( 100 < (tyr-age) ) {
                        validator.setValidateMultiId(ids[0]);
                        this.instructions = VALIDATE_NOT_KOR_MSG[ids[1]];
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            ,isGreater : {
                validate : function(id){
                    var ids        = id.split('&');
                    var idsLen     = ids.length;
                    var v1,v2;

                    if(idsLen != 2)
                    {
                        this.instructions = 'ID를 2개 입력해 주세요.';
                        return false;
                    }

                    v1 = $('#'+ids[0]).val();
                    v2 = $('#'+ids[1]).val();

                    if(v1 == '')
                    {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    if(v2 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[1]];
                        validator.setValidateMultiId(ids[1]);
                        return false;
                    }


                    //2022-01-28 => 20220128
                    v1 = v1.replace(/-/gi,"");
                    v2 = v2.replace(/-/gi,"");


                    if($.trim(v1) >= $.trim(v2))
                    {
                        this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                        validator.setValidateMultiId(($.trim(v1) == '')?ids[0]:ids[1]);
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            ,isDate :{
                validate : function(id){
                    if(!_lenFix(id,8,this,true,true) ) {
                        return false;
                    }

                    var thisVal = $('#'+id).val();
                    var yearfield = thisVal.substring(0,4);
                    var monthfield = thisVal.substring(4,6);
                    var dayfield = thisVal.substring(6,8);
                    var dayobj = new Date(yearfield, monthfield - 1, dayfield);

                    if ((dayobj.getMonth() + 1 != monthfield)
                         || (dayobj.getDate() != dayfield)
                         || (dayobj.getFullYear() != yearfield)) {
                        this.instructions = VALIDATE_COMPARE_MSG[id];
                        validator.setValidateMultiId(id);
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            ,isIssuDate :{
                validate : function(id){
                    if(!_lenFix(id,10,this,true,false) ) {
                        this.instructions = VALIDATE_COMPARE_MSG[id];
                        validator.setValidateMultiId(id);
                        return false;
                    }

                    var thisVal = $('#'+id).val();
                    thisVal = thisVal.replaceAll('-','');
                    var yearfield = thisVal.substring(0,4);
                    var monthfield = thisVal.substring(4,6);
                    var dayfield = thisVal.substring(6,8);
                    var dayobj = new Date(yearfield, monthfield - 1, dayfield);

                    if (new Date() < dayobj) {
                        this.instructions = VALIDATE_COMPARE_MSG[id];
                        validator.setValidateMultiId(id);
                        return false;
                    }

                    if ((dayobj.getMonth() + 1 != monthfield)
                        || (dayobj.getDate() != dayfield)
                        || (dayobj.getFullYear() != yearfield)) {
                        this.instructions = VALIDATE_COMPARE_MSG[id];
                        validator.setValidateMultiId(id);
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            ,isMaxByte4000:{
                validate:function(selector){
                    return _maxByte(selector,4000,this,true);
                }
            }
            ,isMaxByte200:{
                validate:function(selector){
                    return _maxByte(selector,200,this,true);
                }
            }
            ,isMaxByteNull200:{
                validate:function(selector){
                    return _maxByte(selector,200,this,false);
                }
            }
            ,isMaxLength1299:{
                validate:function(selector){
                    return _lenBetterMore(selector,1299,this,true,false);
                }
            }
            ,isMaxLength1000:{
                validate:function(selector){
                    return _lenBetterMore(selector,1000,this,true,false);
                }
            }
            ,isMaxLengthNull1299:{
                validate:function(selector){
                    return _lenBetterMore(selector,1299,this,false,false);
                }
            }
            ,isMaxLengthNull1000:{
                validate:function(selector){
                    return _lenBetterMore(selector,1000,this,false,false);
                }
            }
            //한글 입력 불가
            ,isNotKor:{
                validate:function(selector){
                    var v = validator.getValue(selector);
                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                        return false;
                    }

                    var strReg = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
                    if (strReg.test(v)) {
                        this.instructions = VALIDATE_NOT_KOR_MSG[selector];
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            ,isInputId:{
                validate:function(selector){
                    //아이디 입력 체크 영문 숫자만 허용
                    var v = validator.getValue(selector);
                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                        return false;
                    }

                    var strReg = /^[A-Za-z0-9]*$/;
                    if (!strReg.test(v)) {
                        this.instructions = VALIDATE_NOT_KOR_MSG[selector];
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            ,isInputName:{
                validate:function(selector){
                    var v = validator.getValue(selector);
                    if(v == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                        return false;
                    }

                    var strReg = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|\*\s]+$/;
                    if (!strReg.test(v)) {
                        this.instructions = VALIDATE_NOT_KOR_MSG[selector];
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            ,isPhoneNumberCheck:{
                validate:function(id){
                    //전화번호 형식 체크
                    var ids        = id.split('&');
                    var idsLen     = ids.length;
                    if(idsLen != 3)
                    {
                        this.instructions = 'ID를 3개 입력해 주세요.';
                        return false;
                    }
                    var v1 = validator.getValue(ids[0]);
                    validator.setValidateMultiId(ids[0]);
                    var v2 = validator.getValue(ids[1]);
                    var v3 = validator.getValue(ids[2]);

                    if(v1 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    if(v2 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[1]];
                        validator.setValidateMultiId(ids[1]);
                        return false;
                    }

                    if(v3 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[2]];
                        validator.setValidateMultiId(ids[2]);
                        return false;
                    }
                    var testVar= v1+"-"+v2+"-"+v3;
                    this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                    var strReg = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
                    return strReg.test(testVar);
                }
            }
            ,isTelNumberCheck:{
                validate:function(id){
                    //전화번호 형식 체크
                    var ids        = id.split('&');
                    var idsLen     = ids.length;
                    if(idsLen != 3)
                    {
                        this.instructions = 'ID를 3개 입력해 주세요.';
                        return false;
                    }
                    var v1 = validator.getValue(ids[0]);
                    validator.setValidateMultiId(ids[0]);
                    var v2 = validator.getValue(ids[1]);
                    var v3 = validator.getValue(ids[2]);

                    if(v1 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    if(v2 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[1]];
                        validator.setValidateMultiId(ids[1]);
                        return false;
                    }

                    if(v3 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[2]];
                        validator.setValidateMultiId(ids[2]);
                        return false;
                    }
                    var testVar= v1+v2+v3;
                    this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                    var strReg = /(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/;
                    return strReg.test(testVar);
                }
            }
            , idDatesCheckMore6:{
                validate:function(id){
                    //날짜 비교
                    var ids        = id.split('&');
                    var idsLen     = ids.length;
                    if(idsLen != 2)
                    {
                        this.instructions = 'ID를 2개 입력해 주세요.';
                        return false;
                    }

                    var v1 = validator.getValue(ids[0]);
                    var vDate1 = new Date(v1.substring(0,4), v1.substring(4,6) - 1, v1.substring(6,8));

                    var v2 = validator.getValue(ids[1]);
                    var vDate2 = new Date(v2.substring(0,4), v2.substring(4,6) - 1, v2.substring(6,8));

                    if (vDate1 > vDate2) {
                        this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    var diff = Math.abs(vDate2.getTime() - vDate1.getTime());
                    diff = Math.ceil(diff / (1000 * 3600 * 24));

                    //180일
                    if(diff > 180) {
                        this.instructions = VALIDATE_FIX_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    return true;
                }
            }, isNumsMNP :{
                validate:function(id){
                    //번호이동 가입자 가능 영부 확인
                    var ids        = id.split('&');
                    var idsLen     = ids.length;
                    var v1,v2,v3;

                    if(idsLen != 3) {
                        this.instructions = 'ID를 3 입력하세요';
                        return false;
                    }

                    v1 = $('#'+ids[0]).val();
                    v2 = $('#'+ids[1]).val();
                    v3 = $('#'+ids[2]).val();

                    if(v1 == '')
                    {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }

                    if(v2 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[1]];
                        validator.setValidateMultiId(ids[1]);
                        return false;
                    }

                    if(v3 == '') {
                        this.instructions = VALIDATE_NOT_EMPTY_MSG[ids[2]];
                        validator.setValidateMultiId(ids[2]);
                        return false;
                    }

                    var thisVal = v1+v2+v3;
                    var isPhone = false;
                    var strReg = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
                    isPhone = strReg.test(thisVal);

                    if (!isPhone) {
                        this.instructions = VALIDATE_COMPARE_MSG[ids[0]];
                        return false;
                    }


                    var rtnInt = 0;
                    var varData = ajaxCommon.getSerializedData({
                        subscriberNo:thisVal
                    });

                    ajaxCommon.getItemNoLoading({
                        id:'juoSubIngo'
                        ,cache:false
                        ,url:'/juoSubIngoCountAjax.do'
                        ,data: varData
                        ,dataType:"json"
                        ,async:false
                    }
                    ,function(jsonObj){
                        rtnInt = jsonObj;
                    });

                    if(rtnInt > 0) {
                        this.instructions = VALIDATE_AUT_MSG[ids[0]];
                        validator.setValidateMultiId(ids[0]);
                        return false;
                    }
                    return true;
                }

            }
            , isNumMNP :{
                validate:function(selector){

                    //isPhone
                    var v = validator.getValue(selector);
                    var isPhone = false;
                    if(_isNonEmpty(selector,this,v)) {
                        this.instructions = VALIDATE_COMPARE_MSG[selector];
                        var strReg = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
                        isPhone = strReg.test(v);
                    }

                    if (!isPhone) {
                        this.instructions = VALIDATE_COMPARE_MSG[selector];
                        return false;
                    }


                    var rtnInt = 0;
                    var varData = ajaxCommon.getSerializedData({
                        subscriberNo:v
                    });

                    ajaxCommon.getItemNoLoading({
                        id:'juoSubIngo'
                        ,cache:false
                        ,url:'/juoSubIngoCountAjax.do'
                        ,data: varData
                        ,dataType:"json"
                        ,async:false
                    }
                    ,function(jsonObj){
                        rtnInt = jsonObj;
                    });

                    if(rtnInt > 0) {
                        this.instructions = VALIDATE_AUT_MSG[selector];
                        validator.setValidateMultiId(selector);
                        return false;
                    }
                    return true;
                }

            }
            , isCommendId :{
                validate:function(id){
                   //추천 아이디 체크
                    var thisVal = $('#'+id).val();
                    var rtnInt = 0;
                    var varData = ajaxCommon.getSerializedData({
                        commendId:thisVal
                    });

                    ajaxCommon.getItemNoLoading({
                        id:'juoSubIngo'
                        ,cache:false
                        ,url:'/commendIdCountAjax.do'
                        ,data: varData
                        ,dataType:"json"
                        ,async:false
                    }
                    ,function(jsonObj){
                        rtnInt = jsonObj;
                    });

                    if(rtnInt == 0) {
                        this.instructions = VALIDATE_COMPARE_MSG[id];
                        validator.setValidateMultiId(id);
                        return false;
                    }
                    return true;
                }

            }
            , accountChk:{ // 핸드폰으로 등록된 계좌사용방식
                validate:function(selector,that,val){
                    var v = (val != undefined)?val:validator.getValue(selector);
                    v = (v == undefined) ? '':v;
                    that = that || this;
                    var len = v.length;
                    if(v==''){
                        that.instructions = VALIDATE_NOT_EMPTY_MSG[selector];
                        return false;
                    } else {
                        if(len > 2){ // 조건안걸면 substring 오류남
                            var str = v.substring(0,3);
                            if(str=='010' && len==11){
                                that.instructions = VALIDATE_COMPARE_MSG[selector];
                                return false;
                            }
                        }

                    return true;
                }
            }
            }
        }
        ,config:{}
        ,exceptionFn:{}
        ,setValidateMultiId : function(id)
        {
            multiValidateId = id;
        }
        ,getValidateMultiId : function()
        {
            return multiValidateId;
        }
        ,validate:function(isNonExFnRun){
            var i,msg,type,checker,resultOK,vGroup;
            var that = this;

            var fnValidateCheck = function(id,prdcType)
            {
                checker = that.types[type];

                if(!type)
                {
                    return;
                }

                if(!checker)
                {
                    return false;
                }
                resultOK = checker.validate(id);
                if(!resultOK)
                {
                    /**********************************************
                     * id구성 2개 이상 일때...
                     *********************************************/
                    if(id.indexOf('&')>-1)
                    {
                        id = validator.getValidateMultiId();
                    }

                    that.id = id;
                    msg = checker.instructions;
                    that.message=msg;

                    var exFn = validator.exceptionFn[id];
                    if(exFn != undefined && typeof exFn == 'function' && !isNonExFnRun)
                        exFn();

                    if (!isNonExFnRun) {
                        var $validTarget = $('#'+id);
                        if ($validTarget.attr("type") != "hidden") {
                            $validTarget.focus();
                        }
                    }

                    /**IE에서 focus 상단으로 붙는 현상 방지*/
                    /*var agent = navigator.userAgent.toLowerCase();
                    if ((navigator.appName=='Netscape' && navigator.userAgent.search('Trident') != -1) || agent.indexOf("msie") != -1) {
                        try {
                            $('html').animate({scrollTop:$('#'+id).offset().top - ($(window).height() / 2)}, 0);
                        } catch (e) {

                        }
                    }*/
                }

                return resultOK;
            }

            resultOK = true;
            that.message = '';

            for(var i in this.config)
            {
                if(this.config.hasOwnProperty(i))
                {
                    vGroup = this.config[i];
                    if(typeof vGroup ==='object')
                    {
                        for(var p in vGroup)
                        {
                            if(vGroup.hasOwnProperty(p))
                            {
                                type = vGroup[p];
                                fnValidateCheck(p,i);
                                if(!resultOK)
                                {
                                    break;
                                }
                            }
                        }
                    }
                    else
                    {
                        type = vGroup;
                        fnValidateCheck(i);
                    }

                    if(!resultOK) {
                        break;
                    } else {

                        /*if (!isNonExFnRun) {
                            var ids        = i.split('&');
                            if (ids.length > 1) {
                               console.log ("isNonExFnRun==>"+ isNonExFnRun + "#ids==>"+ ids);
                               $('#'+ids[0]).parent().parent().eq(0).addClass('has-safe');
                            } else  {
                               $('#'+i).parent().eq(0).addClass('has-safe');
                            }
                        }*/


                    }

                }
            }

            return resultOK;
        }
        ,getErrorMsg:function()
        {
            return this.message;
        }
        ,getErrorId:function()
        {
            return this.id;
        }
    }
}();