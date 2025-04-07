const activeLink = document.getElementById('topic_tag_sidebar_link');
activeLink.classList.add('active');

const editModal = document.getElementById('edit_topic_tag_modal');
if (editModal) {
    editModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const tagId = button.getAttribute('data-topic-tag-id');
        const modalIdInput = editModal.querySelector('#edit_topic_tag_id');
        modalIdInput.value = tagId;

        const tagName = button.getAttribute('data-topic-tag-name');
        const modalNameInput = editModal.querySelector('#edit_topic_tag_name');
        modalNameInput.value = tagName;

        const tagDescription = button.getAttribute('data-topic-tag-description');
        const modalDescriptionInput = editModal.querySelector('#edit_topic_tag_description');
        modalDescriptionInput.value = tagDescription;

        const tagActive = button.getAttribute('data-topic-tag-active');
        const modalActiveInput = editModal.querySelector('#edit_topic_tag_active');
        modalActiveInput.checked = (tagActive === 'true');
    });
}
const deleteModal = document.getElementById('delete_topic_tag_modal');
if (deleteModal) {
    deleteModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const tagId = button.getAttribute('data-topic-tag-id');
        const modalIdInput = deleteModal.querySelector('#delete_topic_tag_id');
        modalIdInput.value = tagId;

        const tagName = button.getAttribute('data-topic-tag-name');
        const modalNameInput = deleteModal.querySelector('#delete_topic_tag_name');
        modalNameInput.value = tagName;

        const tagDescription = button.getAttribute('data-topic-tag-description');
        const modalDescriptionInput = deleteModal.querySelector('#delete_topic_tag_description');
        modalDescriptionInput.value = tagDescription;

        const tagActive = button.getAttribute('data-topic-tag-active');
        const modalActiveInput = deleteModal.querySelector('#delete_topic_tag_active');
        modalActiveInput.checked = (tagActive === 'true');
    });
}