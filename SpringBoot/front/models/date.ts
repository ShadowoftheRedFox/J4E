export const toDate = (date: string) => {
    return new Date(date).toLocaleString('fr-FR', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' });
}

export const toTime = (date: string) => {
    return new Date(date).toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
}

export const toDateTime = (date: string) => {
    return new Date(date).toLocaleString('fr-FR', { weekday: 'short', day: 'numeric', month: 'short', year: 'numeric', hour: '2-digit', minute: '2-digit' });
}