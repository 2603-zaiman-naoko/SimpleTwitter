$( function () {
	$('#deleteid').on('click', function(){

		// 「OK」ならtrue返却、「キャンセル」ならfalse
		if(!confirm("本当に削除しますか？")){

			// 送信をキャンセルする
			return false;
		}
	});
});