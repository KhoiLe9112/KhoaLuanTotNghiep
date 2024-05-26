$(function () {
	$('.js-basic-example').DataTable({
       	language: {
       		url: 'shared/vi.json',
       	},
        responsive: true
    });

    //Exportable table
    $('.js-exportable').DataTable({
        dom: 'Bfrtip',
        responsive: true,
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ],
        language: {
			url: 'shared/vi.json',
		}
    });
});