const activeLink = document.getElementById('topic_tag_sidebar_link');
activeLink.classList.add('active');

const editTopicTagModal = document.getElementById('edit_topic_tag_modal');
if (editTopicTagModal) {
    editTopicTagModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const tagId = button.getAttribute('data-topic-tag-id');
        const modalIdInput = editTopicTagModal.querySelector('#edit_topic_tag_id');
        modalIdInput.value = tagId;

        const tagName = button.getAttribute('data-topic-tag-name');
        const modalTagNameInput = editTopicTagModal.querySelector('#edit_topic_tag_name');
        modalTagNameInput.value = tagName;

        const tagDescription = button.getAttribute('data-topic-tag-description');
        const modalTagDescriptionInput = editTopicTagModal.querySelector('#edit_topic_tag_description');
        modalTagDescriptionInput.value = tagDescription;

        const tagActive = button.getAttribute('data-topic-tag-active');
        const modalTagActiveInput = editTopicTagModal.querySelector('#edit_topic_tag_active');
        modalTagActiveInput.checked = (tagActive === 'true');
    });
}
const deleteTopicTagModal = document.getElementById('delete_topic_tag_modal');
if (deleteTopicTagModal) {
    deleteTopicTagModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const tagId = button.getAttribute('data-topic-tag-id');
        const modalIdInput = deleteTopicTagModal.querySelector('#delete_topic_tag_id');
        modalIdInput.value = tagId;

        const tagName = button.getAttribute('data-topic-tag-name');
        const modalTagNameInput = deleteTopicTagModal.querySelector('#delete_topic_tag_name');
        modalTagNameInput.value = tagName;

        const tagDescription = button.getAttribute('data-topic-tag-description');
        const modalTagDescriptionInput = deleteTopicTagModal.querySelector('#delete_topic_tag_description');
        modalTagDescriptionInput.value = tagDescription;

        const tagActive = button.getAttribute('data-topic-tag-active');
        const modalTagActiveInput = deleteTopicTagModal.querySelector('#delete_topic_tag_active');
        modalTagActiveInput.checked = (tagActive === 'true');
    });
}