const TEMPLATE_ID_ENV_KEYS = {
  newchange: 'VITE_EFORM_TEMPLATE_ID_NEWCHANGE',
  ownerchange: 'VITE_EFORM_TEMPLATE_ID_OWNERCHANGE',
  termination: 'VITE_EFORM_TEMPLATE_ID_TERMINATION',
  servicechange: 'VITE_EFORM_TEMPLATE_ID_SERVICECHANGE',
  insurance_ios: 'VITE_EFORM_TEMPLATE_ID_INSURANCE_IOS',
  insurance_android: 'VITE_EFORM_TEMPLATE_ID_INSURANCE_ANDROID',
}

export const getEformsignTemplateIds = () => {
  return Object.entries(TEMPLATE_ID_ENV_KEYS).reduce((acc, [templateKey, envKey]) => {
    const templateId = import.meta.env[envKey]

    if (templateId) {
      acc[templateKey] = templateId
    }

    return acc
  }, {})
}
