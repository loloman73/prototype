const activeLink = document.getElementById('accessibility_tag_sidebar_link');
activeLink.classList.add('active');

const editAccessibilityTagModal = document.getElementById('edit_accessibility_tag_modal');
if (editAccessibilityTagModal) {
    editAccessibilityTagModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const tagId = button.getAttribute('data-accessibility-tag-id');
        const modalIdInput = editAccessibilityTagModal.querySelector('#edit_accessibility_tag_id');
        modalIdInput.value = tagId;

        const tagName = button.getAttribute('data-accessibility-tag-name');
        const modalTagNameInput = editAccessibilityTagModal.querySelector('#edit_accessibility_tag_name');
        modalTagNameInput.value = tagName;

        const tagDescription = button.getAttribute('data-accessibility-tag-description');
        const modalTagDescriptionInput = editAccessibilityTagModal.querySelector('#edit_accessibility_tag_description');
        modalTagDescriptionInput.value = tagDescription;

        const tagActive = button.getAttribute('data-accessibility-tag-active');
        const modalTagActiveInput = editAccessibilityTagModal.querySelector('#edit_accessibility_tag_active');
        modalTagActiveInput.checked = (tagActive === 'true');
    });
}
const deleteAccessibilityTagModal = document.getElementById('delete_accessibility_tag_modal');
if (deleteAccessibilityTagModal) {
    deleteAccessibilityTagModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const tagId = button.getAttribute('data-accessibility-tag-id');
        const modalIdInput = deleteAccessibilityTagModal.querySelector('#delete_accessibility_tag_id');
        modalIdInput.value = tagId;

        const tagName = button.getAttribute('data-accessibility-tag-name');
        const modalTagNameInput = deleteAccessibilityTagModal.querySelector('#delete_accessibility_tag_name');
        modalTagNameInput.value = tagName;

        const tagDescription = button.getAttribute('data-accessibility-tag-description');
        const modalTagDescriptionInput = deleteAccessibilityTagModal.querySelector('#delete_accessibility_tag_description');
        modalTagDescriptionInput.value = tagDescription;

        const tagActive = button.getAttribute('data-accessibility-tag-active');
        const modalTagActiveInput = deleteAccessibilityTagModal.querySelector('#delete_accessibility_tag_active');
        modalTagActiveInput.checked = (tagActive === 'true');
    });
}