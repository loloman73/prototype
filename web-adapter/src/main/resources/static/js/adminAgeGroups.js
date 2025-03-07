const activeLink = document.getElementById('age_group_sidebar_link');
activeLink.classList.add('active');

const editAgeGroupModal = document.getElementById('edit_age_group_modal');
if (editAgeGroupModal) {
    editAgeGroupModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const ageGroupId = button.getAttribute('data-age-group-id');
        const modalIdInput = editAgeGroupModal.querySelector('#edit_age_group_id');
        modalIdInput.value = ageGroupId;

        const ageGroupName = button.getAttribute('data-age-group-name');
        const modalNameInput = editAgeGroupModal.querySelector('#edit_age_group_name');
        modalNameInput.value = ageGroupName;

        const ageGroupMin = button.getAttribute('data-age-group-min');
        const modalMinInput = editAgeGroupModal.querySelector('#edit_age_group_min');
        modalMinInput.value = ageGroupMin;

        const ageGroupMax = button.getAttribute('data-age-group-max');
        const modalMaxInput = editAgeGroupModal.querySelector('#edit_age_group_max');
        modalMaxInput.value = ageGroupMax;

    });
}
const deleteAgeGroupModal = document.getElementById('delete_age_group_modal');
if (deleteAgeGroupModal) {
    deleteAgeGroupModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const ageGroupId = button.getAttribute('data-age-group-id');
        const modalIdInput = deleteAgeGroupModal.querySelector('#delete_age_group_id');
        modalIdInput.value = ageGroupId;

        const ageGroupName = button.getAttribute('data-age-group-name');
        const modalTagNameInput = deleteAgeGroupModal.querySelector('#delete_age_group_name');
        modalTagNameInput.value = ageGroupName;

        const ageGroupMin = button.getAttribute('data-age-group-min');
        const modalMinInput = deleteAgeGroupModal.querySelector('#delete_age_group_min');
        modalMinInput.value = ageGroupMin;

        const ageGroupMax = button.getAttribute('data-age-group-max');
        const modalMaxInput = deleteAgeGroupModal.querySelector('#delete_age_group_max');
        modalMaxInput.value = ageGroupMax;
    });
}