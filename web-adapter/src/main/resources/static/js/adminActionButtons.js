const editModal = document.getElementById('edit_modal');
if (editModal) {
    editModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const fieldId = button.getAttribute('data-id');
        const modalInputFieldId = editModal.querySelector('#edit_id');
        if(modalInputFieldId != null) { modalInputFieldId.value = fieldId}

        const field1 = button.getAttribute('data-field1');
        const modalInputField1 = editModal.querySelector('#edit_field1');
        if(modalInputField1 != null) { modalInputField1.value = field1}

        const field2 = button.getAttribute('data-field2');
        const modalInputField2 = editModal.querySelector('#edit_field2');
        if(modalInputField2 != null) { modalInputField2.value = field2}

        const field3 = button.getAttribute('data-field3');
        const modalInputField3 = editModal.querySelector('#edit_field3');
        if(modalInputField3 != null) { modalInputField3.value = field3}

        const fieldActive = button.getAttribute('data-active');
        const modalInputActive = editModal.querySelector('#edit_active');
        if(modalInputActive != null) {modalInputActive.checked = (fieldActive === 'true')}
    });
}
const deleteModal = document.getElementById('delete_modal');
if (deleteModal) {
    deleteModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const fieldId = button.getAttribute('data-id');
        const modalInputFieldId = deleteModal.querySelector('#delete_id');
        if(modalInputFieldId != null) { modalInputFieldId.value = fieldId}

        const field1 = button.getAttribute('data-field1');
        const modalInputField1 = deleteModal.querySelector('#delete_field1');
        if(modalInputField1 != null) { modalInputField1.value = field1}

        const field2 = button.getAttribute('data-field2');
        const modalInputField2 = deleteModal.querySelector('#delete_field2');
        if(modalInputField2 != null) { modalInputField2.value = field2}

        const field3 = button.getAttribute('data-field3');
        const modalInputField3 = deleteModal.querySelector('#delete_field3');
        if(modalInputField3 != null) { modalInputField3.value = field3}

        const fieldActive = button.getAttribute('data-active');
        const modalInputActive = deleteModal.querySelector('#delete_active');
        if(modalInputActive != null) {modalInputActive.checked = (fieldActive === 'true')}
    });
}