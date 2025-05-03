const activeLink = document.getElementById('language_sidebar_link');
activeLink.classList.add('active');

const editModal = document.getElementById('edit_modal');
if (editModal) {
    editModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const fieldId = button.getAttribute('data-id');
        const modalFieldIdInput = editModal.querySelector('#edit_id');
        modalFieldIdInput.value = fieldId;

        const field1 = button.getAttribute('data-field1');
        const modalField1Input = editModal.querySelector('#edit_field1');
        modalField1Input.value = field1;

        const field2 = button.getAttribute('data-field2');
        const modalField2Input = editModal.querySelector('#edit_field2');
        modalField2Input.value = field2;

        const field3 = button.getAttribute('data-field3');
        const modalField3Input = editModal.querySelector('#edit_field3');
        modalField3Input.value = field3;

        const fieldActive = button.getAttribute('data-active');
        const modalActiveInput = editModal.querySelector('#edit_active');
        modalActiveInput.checked = (fieldActive === 'true');
    });
}
const deleteModal = document.getElementById('delete_modal');
if (deleteModal) {
    deleteModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const fieldId = button.getAttribute('data-id');
        const modalFieldIdInput = deleteModal.querySelector('#delete_id');
        modalFieldIdInput.value = fieldId;

        const field1 = button.getAttribute('data-field1');
        const modalField1Input = deleteModal.querySelector('#delete_field1');
        modalField1Input.value = field1;

        const field2 = button.getAttribute('data-field2');
        const modalField2Input = deleteModal.querySelector('#delete_field2');
        modalField2Input.value = field2;

        const field3 = button.getAttribute('data-field3');
        const modalField3Input = deleteModal.querySelector('#delete_field3');
        modalField3Input.value = field3;

        const fieldActive = button.getAttribute('data-active');
        const modalActiveInput = deleteModal.querySelector('#delete_active');
        modalActiveInput.checked = (fieldActive === 'true');
    });
}