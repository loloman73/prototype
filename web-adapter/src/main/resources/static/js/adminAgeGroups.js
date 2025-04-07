const activeLink = document.getElementById('age_group_sidebar_link');
activeLink.classList.add('active');

const editModal = document.getElementById('edit_age_group_modal');
if (editModal) {
    editModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const ageGroupId = button.getAttribute('data-age-group-id');
        const modalIdInput = editModal.querySelector('#edit_age_group_id');
        modalIdInput.value = ageGroupId;

        const ageGroupName = button.getAttribute('data-age-group-name');
        const modalNameInput = editModal.querySelector('#edit_age_group_name');
        modalNameInput.value = ageGroupName;

        const ageGroupMin = button.getAttribute('data-age-group-min');
        const modalMinInput = editModal.querySelector('#edit_age_group_min');
        modalMinInput.value = ageGroupMin;

        const ageGroupMax = button.getAttribute('data-age-group-max');
        const modalMaxInput = editModal.querySelector('#edit_age_group_max');
        modalMaxInput.value = ageGroupMax;

        const groupActive = button.getAttribute('data-age-group-active');
        const modalActiveInput = editModal.querySelector('#edit_age_group_active');
        modalActiveInput.checked = (groupActive === 'true');
    });
}
const deleteModal = document.getElementById('delete_age_group_modal');
if (deleteModal) {
    deleteModal.addEventListener('show.bs.modal', event => {
        const button = event.relatedTarget;

        const ageGroupId = button.getAttribute('data-age-group-id');
        const modalIdInput = deleteModal.querySelector('#delete_age_group_id');
        modalIdInput.value = ageGroupId;

        const ageGroupName = button.getAttribute('data-age-group-name');
        const modalNameInput = deleteModal.querySelector('#delete_age_group_name');
        modalNameInput.value = ageGroupName;

        const ageGroupMin = button.getAttribute('data-age-group-min');
        const modalMinInput = deleteModal.querySelector('#delete_age_group_min');
        modalMinInput.value = ageGroupMin;

        const ageGroupMax = button.getAttribute('data-age-group-max');
        const modalMaxInput = deleteModal.querySelector('#delete_age_group_max');
        modalMaxInput.value = ageGroupMax;

        const groupActive = button.getAttribute('data-age-group-active');
        const modalActiveInput = deleteModal.querySelector('#delete_age_group_active');
        modalActiveInput.checked = (groupActive === 'true');
    });
}